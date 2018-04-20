package com.ceotic.clubtrack.model;

public class City {

    private String idCity;

    private String nameCity;

    public City(String nameCity) {
        this.nameCity = nameCity;
    }

    public City() {
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }
}
