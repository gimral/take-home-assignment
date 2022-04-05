package com.marionete.service.controller;

import com.marionete.service.model.PostUserAccount;
import com.marionete.service.model.UserAccount;
import com.marionete.service.model.query.GetUserAccountQuery;
import com.marionete.service.query.GetUserAccountQueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountController {

    private final GetUserAccountQueryHandler getUserAccountQueryHandler;

    @Autowired
    public UserAccountController(GetUserAccountQueryHandler getUserAccountQueryHandler) {
        this.getUserAccountQueryHandler = getUserAccountQueryHandler;
    }

    @PostMapping("/useraccount")
    @ResponseStatus(HttpStatus.OK)
    public UserAccount userAccount(@RequestBody PostUserAccount postUserAccount) {
        GetUserAccountQuery query = new GetUserAccountQuery(postUserAccount.getUsername(),
                postUserAccount.getPassword());
        return getUserAccountQueryHandler.handle(query).block();
    }
}
