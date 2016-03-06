package com.zskisa.tourismkkc.apimodel;

public class ApiFeedRequest {
    private String start = "";
    private String end = "";
    private String like = "";
    private int plus = 5;
    private ApiLogin apiLogin = null;

    public ApiFeedRequest() {
        reset();
    }

    public void prePareNext() {
        start = String.valueOf(Integer.parseInt(end) + 1);
        end = String.valueOf(Integer.parseInt(end) + plus);
    }

    public void reset() {
        start = "1";
        end = "5";
    }

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

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public ApiLogin getApiLogin() {
        return apiLogin;
    }

    public void setApiLogin(ApiLogin apiLogin) {
        this.apiLogin = apiLogin;
    }
}
