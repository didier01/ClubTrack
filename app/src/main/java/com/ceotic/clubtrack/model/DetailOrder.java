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
    private Order order;
    //private Product product;
    private String idProduct;
    private int quantity;
    private int price;

    public DetailOrder() {
    }

    public DetailOrder(Order order, String idProduct, int quantity, int price) {
        this.idDetailCart = UUID.randomUUID().toString();
        this.order = order;
        this.idProduct = idProduct;
        this.quantity = quantity;
        this.price = price;
    }

    public String getIdDetailCart() {
        return idDetailCart;
    }

    public void setIdDetailCart(String idDetailCart) {
        this.idDetailCart = idDetailCart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getProduct() {
        return idProduct;
    }

    public void setProduct(String idProduct ) {
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
                ", order=" + order +
                ", product=" + idProduct +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }

}
