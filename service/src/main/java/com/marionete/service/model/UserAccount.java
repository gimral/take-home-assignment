package com.marionete.service.model;

public class UserAccount {
    private AccountInfo accountInfo;
    private UserInfo userInfo;

    public UserAccount(){

    }

    public UserAccount(AccountInfo accountInfo, UserInfo userInfo) {
        this.accountInfo = accountInfo;
        this.userInfo = userInfo;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
