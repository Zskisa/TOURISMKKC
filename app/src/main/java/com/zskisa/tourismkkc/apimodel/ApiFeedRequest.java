package com.zskisa.tourismkkc.apimodel;

public class ApiFeedRequest {
    String start = "1";
    String end = "10";
    ApiLogin apiLogin = null;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public ApiLogin getApiLogin() {
        return apiLogin;
    }

    public void setApiLogin(ApiLogin apiLogin) {
        this.apiLogin = apiLogin;
    }
}
