package com.users.razvan.users.model;

import java.io.Serializable;

public class AddressModel implements Serializable {
    private static final long serialVersionUID = -6992835177987299404L;

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private UserGeoPosition geo;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public UserGeoPosition getGeo() {
        return geo;
    }

    public void setGeo(UserGeoPosition geo) {
        this.geo = geo;
    }
}
