package com.marionete.service.model.query;

import com.marionete.service.model.Authorized;

public class GetAccountInfoQuery extends Authorized {
    public GetAccountInfoQuery(String token) {
        super(token);
    }
}
