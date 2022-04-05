package com.marionete.service.model;

public abstract class Authorized {
    private String token;

    protected Authorized(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
