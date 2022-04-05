package com.marionete.service.query;

import com.marionete.service.model.AccountInfo;
import com.marionete.service.model.query.GetAccountInfoQuery;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GetAccountInfoQueryHandler {
    private final WebClient accountApiClient;

    private static final String RESOURCE_PATH = "/marionete/account/";

    @Autowired
    public GetAccountInfoQueryHandler(WebClient accountApiClient) {
        this.accountApiClient = accountApiClient;
    }

    public Mono<AccountInfo> handle(GetAccountInfoQuery query){
        return accountApiClient.get()
                .uri(RESOURCE_PATH)
                .headers(h -> h.setBearerAuth(query.getToken()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AccountInfo.class);
    }
}
