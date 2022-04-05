package com.marionete.service.query;

import com.marionete.service.model.UserInfo;
import com.marionete.service.model.query.GetUserInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GetUserInfoQueryHandler {
    private final WebClient userApiClient;

    private static final String RESOURCE_PATH = "/marionete/user/";

    @Autowired
    public GetUserInfoQueryHandler(WebClient userApiClient) {
        this.userApiClient = userApiClient;
    }

    public Mono<UserInfo> handle(GetUserInfoQuery query){
        return userApiClient.get()
                .uri(RESOURCE_PATH)
                .headers(h -> h.setBearerAuth(query.getToken()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserInfo.class);
//                .flatMap(s -> Mono.just(new UserInfo()));
    }
}
