package com.marionete.service.model;

import javax.validation.constraints.NotBlank;

public class PostUserAccount {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public PostUserAccount() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
