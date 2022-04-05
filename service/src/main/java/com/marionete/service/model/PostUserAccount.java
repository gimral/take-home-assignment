package com.marionete.service.model;

public class PostUserAccount {
    private String username;
    private String password;

    public PostUserAccount() {
    }

    public String getUsername() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
