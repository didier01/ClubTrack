package com.ceotic.clubtrack.model;

public class ProductType {
    private String idTypeProduct;

    private String nameTypeProduct;

    public ProductType(String nameTypeProduct) {
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
}

