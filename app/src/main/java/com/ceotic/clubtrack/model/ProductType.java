package com.ceotic.clubtrack.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class ProductType extends RealmObject {

    @Ignore
    private final String TAG = ProductType.class.toString();

    @PrimaryKey
    private String idTypeProduct;

    private int imageType;
    private String nameTypeProduct;


    public ProductType(int imageType, String nameTypeProduct) {

        this.idTypeProduct = UUID.randomUUID().toString();
        this.imageType = imageType;
        this.nameTypeProduct = nameTypeProduct;
    }

    public ProductType() {
    }

    public String getNameTypeProduct() {
        return nameTypeProduct;
    }

    public void setNameTypeProduct(String nameTypeProduct) {
        this.nameTypeProduct = nameTypeProduct;
    }

    public String getIdTypeProduct() {
        return idTypeProduct;
    }

    public void setIdTypeProduct(String idTypeProduct) {
        this.idTypeProduct = idTypeProduct;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    @Override
    public String toString() {
        return "ProductType{" +
                "idTypeProduct='" + idTypeProduct + '\'' +
                ", imageType=" + imageType +
                ", nameTypeProduct='" + nameTypeProduct + '\'' +
                '}';
    }
}

