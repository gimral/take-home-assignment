package com.marionete.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.json.Jackson2CodecSupport;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Consumer;

@Configuration
public class WebClientConfig {
    private static final Logger logger = LoggerFactory.getLogger(WebClientConfig.class);

    @Value("${ACCOUNT_API_URI}")
    private String accountApiUri;

    @Value("${USER_API_URI}")
    private String userApiUri;

    @Bean
    public WebClient accountApiClient(WebClient.Builder webClientBuilder) {

//        HttpClient httpClient = HttpClient.create()
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
//                .responseTimeout(Duration.ofMillis(5000))
//                .doOnConnected(conn ->
//                        conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
//                                .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        return webClientBuilder.baseUrl(accountApiUri)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                //Response does not have the correct content type header
                .codecs(getCodecConfigurer())
                .filter(retryFilter())
                .build();
    }

    @Bean
    public WebClient userApiClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(userApiUri)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                //Response does not have the correct content type header
                .codecs(getCodecConfigurer())
                .filter(retryFilter())
                .build();
    }

    private Consumer<ClientCodecConfigurer> getCodecConfigurer() {
        return configurer -> {
            // This API returns JSON with content type text/plain, so need to register a custom
            // decoder to deserialize this response via Jackson

            // Get existing decoder's ObjectMapper if available, or create new one
            ObjectMapper objectMapper = configurer.getReaders().stream()
                    .filter(reader -> reader instanceof Jackson2JsonDecoder)
                    .map(reader -> (Jackson2JsonDecoder) reader)
                    .map(Jackson2CodecSupport::getObjectMapper)
                    .findFirst()
                    .orElseGet(() -> Jackson2ObjectMapperBuilder.json().build());

            Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
            configurer.customCodecs().registerWithDefaultConfig(decoder);
        };
    }

    private ExchangeFilterFunction retryFilter() {
        return (request, next) ->
                next.exchange(request)
                        .doOnNext(clientResponse -> {
                            if (clientResponse.statusCode().is5xxServerError()) {
                                throw new RuntimeException();
                            }
                        })
                        .retryWhen(
                                Retry.backoff(10, Duration.ofSeconds(1))
                                        .jitter(0.7)
                                        .filter(throwable -> throwable instanceof RuntimeException)
                                        .doAfterRetry(retrySignal -> logger.warn("Retrying the request"))
                                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                                new RuntimeException(
                                                        "Service failed to respond, after max attempts of: "
                                                                + retrySignal.totalRetries())));
    }

}
