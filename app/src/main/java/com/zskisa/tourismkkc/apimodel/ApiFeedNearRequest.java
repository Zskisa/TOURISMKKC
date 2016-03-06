package com.zskisa.tourismkkc.apimodel;

public class ApiFeedNearRequest {
    private ApiLogin apiLogin;
    private String locationLat;
    private String locationLng;
    private String distance = "3000";//3000เมตร

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
