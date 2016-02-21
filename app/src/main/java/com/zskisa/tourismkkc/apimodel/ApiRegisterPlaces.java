package com.zskisa.tourismkkc.apimodel;

public class ApiRegisterPlaces {

    private ApiLogin apiLogin;
    private String location_lat;
    private String location_lng;
    private String places_name;
    private String places_desc;
    private String type_detail_id;
    private String files;
    private String mime;

    public ApiLogin getApiLogin() {
        return apiLogin;
    }

    public void setApiLogin(ApiLogin apiLogin) {
        this.apiLogin = apiLogin;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public String getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(String location_lng) {
        this.location_lng = location_lng;
    }

    public String getPlaces_name() {
        return places_name;
    }

    public void setPlaces_name(String places_name) {
        this.places_name = places_name;
    }

    public String getPlaces_desc() {
        return places_desc;
    }

    public void setPlaces_desc(String places_desc) {
        this.places_desc = places_desc;
    }

    public String getType_detail_id() {
        return type_detail_id;
    }

    public void setType_detail_id(String type_detail_id) {
        this.type_detail_id = type_detail_id;
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
}
