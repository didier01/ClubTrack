package com.ceotic.clubtrack.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class SubCategory extends RealmObject {
    @Ignore
    private final String TAG = SubCategory.class.toString();

    @PrimaryKey
    private String idSubCategory;

    private String idCategory;
    private String nameSubCategory;

    public SubCategory() {
    }

    public SubCategory(String idCategory, String nameSubCategory) {
        this.idSubCategory = UUID.randomUUID().toString();
        this.idCategory = idCategory;
        this.nameSubCategory = nameSubCategory;
    }

    public String getIdSubCategory() {
        return idSubCategory;
    }

    public void setIdSubCategory(String idSubCategory) {
        this.idSubCategory = idSubCategory;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameSubCategory() {
        return nameSubCategory;
    }

    public void setNameSubCategory(String nameSubCategory) {
        this.nameSubCategory = nameSubCategory;
    }

    @Override
    public String toString() {
        return "SubCategory{" +
                "idSubCategory='" + idSubCategory + '\'' +
                ", idCategory='" + idCategory + '\'' +
                ", nameSubCategory='" + nameSubCategory + '\'' +
                '}';
    }
}
