package com.zskisa.tourismkkc.apimodel;

public class ApiLogin {
    private String userEmail;
    private String userPassword;

    public ApiLogin(String userEmail, String userPassword) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
