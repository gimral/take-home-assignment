package com.marionete.service.model.query;

public class GetTokenQuery {
    private String userName;
    private String password;

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
