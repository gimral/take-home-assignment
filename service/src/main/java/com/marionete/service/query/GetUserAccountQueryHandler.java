package com.marionete.service.query;

import com.marionete.service.model.AccountInfo;
import com.marionete.service.model.UserAccount;
import com.marionete.service.model.UserInfo;
import com.marionete.service.model.query.GetAccountInfoQuery;
import com.marionete.service.model.query.GetTokenQuery;
import com.marionete.service.model.query.GetUserAccountQuery;
import com.marionete.service.model.query.GetUserInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetUserAccountQueryHandler {
    private final GetAccountInfoQueryHandler getAccountQueryHandler;
    private final GetUserInfoQueryHandler getUserInfoQueryHandler;
    private final GetTokenQueryHandler getTokenQueryHandler;

    @Autowired
    public GetUserAccountQueryHandler(GetAccountInfoQueryHandler getAccountQueryHandler,
                                      GetUserInfoQueryHandler getUserInfoQueryHandler, GetTokenQueryHandler getTokenQueryHandler) {
        this.getAccountQueryHandler = getAccountQueryHandler;
        this.getUserInfoQueryHandler = getUserInfoQueryHandler;
        this.getTokenQueryHandler = getTokenQueryHandler;
    }

    public Mono<UserAccount> handle(GetUserAccountQuery query){
        String token = getTokenQueryHandler.handle(new GetTokenQuery(query.getUserName(),query.getPassword()));
        Mono<AccountInfo> accountInfo = getAccountQueryHandler.handle(new GetAccountInfoQuery(token));
        Mono<UserInfo> userInfo = getUserInfoQueryHandler.handle(new GetUserInfoQuery(token));
        return Mono.zip(accountInfo, userInfo).map(tuple -> new UserAccount(tuple.getT1(), tuple.getT2()));
    }
}
