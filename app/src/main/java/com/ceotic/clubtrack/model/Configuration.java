package com.ceotic.clubtrack.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Configuration extends RealmObject {
    @PrimaryKey
    private String key;
    private Boolean value;
    private String idUserLogin;

    public Configuration() {
    }

    public Configuration(String key, Boolean value) {
        this.key = key;
        this.value = value;
        this.idUserLogin = idUserLogin;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getIdUserLogin() {
        return idUserLogin;
    }

    public void setIdUserLogin(String idUserLogin) {
        this.idUserLogin = idUserLogin;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", idUserLogin='" + idUserLogin + '\'' +
                '}';
    }
}
