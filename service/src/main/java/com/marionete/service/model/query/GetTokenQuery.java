package com.marionete.service.model.query;

public class GetTokenQuery {
    private final String userName;
    private final String password;

    public GetTokenQuery(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
