package com.ceotic.clubtrack.model;

import com.ceotic.clubtrack.util.Catalog;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.Ignore;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Order extends RealmObject {

    @Ignore
    private final String TAG = Order.class.toString();

/*
    @Ignore
    public static final int STATE = Catalog.CREATED; // para crear
*/

    @Ignore
    public static final int CREATED = 1; // creada
    @Ignore
    public static final int SENDED = 2; //enviada

    @PrimaryKey
    private String idCart;

    private String date;
    private User seller;
    private String paymentMethod;
    private int status;

    public Order(String idCart, String date, User seller, String paymentMethod) {
        this.idCart = idCart;
        this.date = date;
        this.seller = seller;
        this.paymentMethod = paymentMethod;
    }

    public Order() {
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String
    toString() {
        return "Order{" +
                "idCart='" + idCart + '\'' +
                ", date='" + date + '\'' +
                ", seller=" + seller +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", status=" + status +
                '}';
    }
}
