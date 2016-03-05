package com.zskisa.tourismkkc.apimodel;

public class ApiFeedNearRequest {
    ApiLogin apiLogin;
    String locationLat;
    String locationLng;

    public ApiLogin getApiLogin() {
        return apiLogin;
    }

    public void setApiLogin(ApiLogin apiLogin) {
        this.apiLogin = apiLogin;
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String locationLat) {
        this.locationLat = locationLat;
    }

    public String getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(String locationLng) {
        this.locationLng = locationLng;
    }
}
