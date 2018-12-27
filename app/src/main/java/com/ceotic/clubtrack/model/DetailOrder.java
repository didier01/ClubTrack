package com.ceotic.clubtrack.model;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class DetailOrder extends RealmObject {

    @Ignore
    private final String TAG = DetailOrder.class.toString();

    @PrimaryKey
    private String idDetailCart;

    private String idOrder;
    private String idProduct;
    private int quantity;
    private int price;

    public DetailOrder(String idOrder, String idProduct, int quantity, int price) {
        this.idDetailCart = UUID.randomUUID().toString();
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
    }

    public DetailOrder() {
    }

    public String getIdDetailCart() {
        return idDetailCart;
    }

    public void setIdDetailCart(String idDetailCart) {
        this.idDetailCart = idDetailCart;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "DetailOrder{" +
                "idDetailCart='" + idDetailCart + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", idProduct='" + idProduct + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
