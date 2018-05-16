
package com.ceotic.clubtrack.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class LocationPlace extends RealmObject {

    @Ignore
    private final String TAG = User.class.toString();

    @PrimaryKey
    private String idLocation;

    private String idUser;


    private String address;
    private String urlAddress;
    private int typeAddress;
    private double latitude;
    private double longitude;

    public LocationPlace(String address, String urlAddress, int typeAddress, double latitude, double longitude) {
        this.idLocation = UUID.randomUUID().toString();
        this.address = address;
        this.urlAddress = urlAddress;
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
                ", urlAddress='" + urlAddress + '\'' +
                ", typeAddress=" + typeAddress +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
