package com.ceotic.clubtrack.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @Ignore
    private final String TAG = User.class.toString();

    @PrimaryKey
    private String idUser;

    private String dniUser;
    private String nameUser;
    private String email;
    private String cellphone;
    private String telephone;
    private String telephone2;

    private String user;
    private String password;

    // Contructores lleons y vacios

    public User(String dniUser, String nameUser, String email, String cellphone, String telephone, String telephone2, String user, String password) {

        this.idUser = UUID.randomUUID().toString();
        this.dniUser = dniUser;
        this.nameUser = nameUser;
        this.email = email;
        this.cellphone = cellphone;
        this.telephone = telephone;
        this.telephone2 = telephone2;
        this.user = user;
        this.password = password;
    }

    public User() {
    }

    // Getters y Setters

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getDniUser() {
        return dniUser;
    }

    public void setDniUser(String dniUser) {
        this.dniUser = dniUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser='" + idUser + '\'' +
                ", dniUser='" + dniUser + '\'' +
                ", nameUser='" + nameUser + '\'' +
                ", email='" + email + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", telephone='" + telephone + '\'' +
                ", telephone2='" + telephone2 + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
