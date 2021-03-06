package com.ceotic.clubtrack.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject{
    @Ignore
    private final String TAG = Product.class.toString();

    @PrimaryKey
    private String idProduct;

    private String idSubCategory;
    private String idCategory;

    private String nameProduct;
    private int imageProduct;
    private String descriptionProduct;
    private int price;
    private int quantity;
    private String typeQuantity;
    private int points;

    public Product(String idSubCategory,String idCategory, String nameProduct, int imageProduct, String descriptionProduct, int price, int quantity, String typeQuantity, int points) {
        this.idProduct = UUID.randomUUID().toString();
        this.idSubCategory = idSubCategory;
        this.idCategory = idCategory;
        this.nameProduct = nameProduct;
        this.imageProduct = imageProduct;
        this.descriptionProduct = descriptionProduct;
        this.price = price;
        this.quantity = quantity;
        this.typeQuantity = typeQuantity;
        this.points = points;
    }

    public Product() {
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

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(int imageProduct) {
        this.imageProduct = imageProduct;
    }

    public String getDescriptionProduct() {
        return descriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        this.descriptionProduct = descriptionProduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTypeQuantity() {
        return typeQuantity;
    }

    public void setTypeQuantity(String typeQuantity) {
        this.typeQuantity = typeQuantity;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct='" + idProduct + '\'' +
                ", idSubCategory='" + idSubCategory + '\'' +
                ", idCategory='" + idCategory + '\'' +
                ", nameProduct='" + nameProduct + '\'' +
                ", imageProduct=" + imageProduct +
                ", descriptionProduct='" + descriptionProduct + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", typeQuantity='" + typeQuantity + '\'' +
                ", points=" + points +
                '}';
    }
}
