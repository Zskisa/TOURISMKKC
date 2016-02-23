package com.zskisa.tourismkkc;

public class AddPlace {

    public String id;
    public String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AddPlace(String id, String type) {
        this.id = id;
        this.type = type;
    }
}
