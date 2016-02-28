package com.zskisa.tourismkkc.apimodel;

public class ApiReview {
    String places_id;
    String rate_value;
    String review_detail;
    String files = "";
    String mime = "";
    ApiLogin ApiLogin;

    public String getPlaces_id() {
        return places_id;
    }

    public void setPlaces_id(String places_id) {
        this.places_id = places_id;
    }

    public String getRate_value() {
        return rate_value;
    }

    public void setRate_value(String rate_value) {
        this.rate_value = rate_value;
    }

    public String getReview_detail() {
        return review_detail;
    }

    public void setReview_detail(String review_detail) {
        this.review_detail = review_detail;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public ApiLogin getApiLogin() {
        return ApiLogin;
    }

    public void setApiLogin(ApiLogin login) {
        this.ApiLogin = login;
    }
}
