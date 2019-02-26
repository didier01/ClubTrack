package com.ceotic.clubtrack.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Category extends RealmObject {

    @Ignore
    private final String TAG = Category.class.toString();

    @PrimaryKey
    private String idCategory;

    private int imageCategory;
    private String nameCategory;


    public Category(int imageCategory, String nameCategory) {

        this.idCategory = UUID.randomUUID().toString();
        this.imageCategory = imageCategory;
        this.nameCategory = nameCategory;
    }

    public Category() {
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public int getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(int imageCategory) {
        this.imageCategory = imageCategory;
    }

    @Override
    public String toString() {
        return "Category{" +
                "idCategory='" + idCategory + '\'' +
                ", imageCategory=" + imageCategory +
                ", nameCategory='" + nameCategory + '\'' +
                '}';
    }
}

