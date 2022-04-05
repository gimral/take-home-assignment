package com.marionete.service.model.query;

import com.marionete.service.model.Authorized;

public class GetUserInfoQuery extends Authorized {
    public GetUserInfoQuery(String token) {
        super(token);
    }
}
