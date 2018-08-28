package com.users.razvan.users.model;

import java.io.Serializable;

public class UserGeoPosition implements Serializable {
    private static final long serialVersionUID = -8020842257807024555L;

    private String lat;
    private String lng;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
