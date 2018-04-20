package com.ceotic.clubtrack.model;

public class Product {

    private String idPro;
    private String idTypeProduct;

    private String idProduct;
    private String nameProduct;
    private String imageProduct;
    private String descriptionProduct;
    private int price;
    private int quantity;
    private int typeQuantity;
    private int points;

    public Product(String idProduct, String nameProduct, String imageProduct, String descriptionProduct, int price, int quantity, int typeQuantity, int points) {
        this.idProduct = idProduct;
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

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
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

    public int getTypeQuantity() {
        return typeQuantity;
    }

    public void setTypeQuantity(int typeQuantity) {
        this.typeQuantity = typeQuantity;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
