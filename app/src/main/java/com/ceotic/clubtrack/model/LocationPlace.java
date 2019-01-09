
package com.ceotic.clubtrack.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class LocationPlace extends RealmObject {

    @Ignore
    private final String TAG = LocationPlace.class.toString();

    @PrimaryKey
    private String idLocation;

    private String idUser;
    private String address;
    private String typeAddress;
    private double latitude;
    private double longitude;

    public LocationPlace(String idUser, String address, String typeAddress, double latitude, double longitude) {
        this.idLocation = UUID.randomUUID().toString();
        this.idUser = idUser;
        this.address = address;
        this.typeAddress = typeAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationPlace() {
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

    public String gettypeAddress() {
        return typeAddress;
    }

    public void settypeAddress(String typeAddress) {
        this.typeAddress = typeAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "LocationPlace{" +
                "idLocation='" + idLocation + '\'' +
                ", idUser='" + idUser + '\'' +
                ", address='" + address + '\'' +
                ", typeAddress='" + typeAddress + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
