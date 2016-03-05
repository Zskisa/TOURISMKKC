package com.zskisa.tourismkkc.apimodel;

import java.util.List;

public class ApiProfileRequest {
    private ApiLogin apiLogin;
    private List<String> typeDetailID;

    public ApiLogin getApiLogin() {
        return apiLogin;
    }

    public void setApiLogin(ApiLogin apiLogin) {
        this.apiLogin = apiLogin;
    }

    public List<String> getTypeDetailID() {
        return typeDetailID;
    }

    public void setTypeDetailID(List<String> typeDetailID) {
        this.typeDetailID = typeDetailID;
    }
}
