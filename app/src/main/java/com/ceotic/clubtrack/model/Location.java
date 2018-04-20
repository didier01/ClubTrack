package com.ceotic.clubtrack.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Location extends RealmObject{

    @Ignore
    @PrimaryKey
    private String idUser;
    private String idLocation;

    private String address;
    private String urlAddress;
    private int typeAddress;


    public Location(String idUser, String idLocation, String address, String urlAddress, int typeAddress) {
        this.idUser = idUser;
        this.idLocation = idLocation;
        this.address = address;
        this.urlAddress = urlAddress;
        this.typeAddress = typeAddress;
    }

    public Location() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public int getTypeAddress() {
        return typeAddress;
    }

    public void setTypeAddress(int typeAddress) {
        this.typeAddress = typeAddress;
    }
}
