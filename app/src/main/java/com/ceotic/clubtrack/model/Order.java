package com.ceotic.clubtrack.model;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Order extends RealmObject {

    @Ignore
    private final String TAG = Order.class.toString();

/*
    @Ignore
    public static final int STATE = Constants.CREATED; // para crear
*/

    @Ignore
    public static final int CREATED = 1; // creada
    @Ignore
    public static final int SENDED = 2; //enviada

    @PrimaryKey
    private String idCart;

    private Date date;
    private String paymentMethod;
    private String idUser;
    private int status;
    private int price;
    private String address;

    public Order(Date date, String paymentMethod, String idUser, int status, String address, int price) {
        this.idCart = UUID.randomUUID().toString();
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.idUser = idUser;
        this.status = status;
        this.address = address;
        this.price = price;
    }

    public Order() {
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idCart='" + idCart + '\'' +
                ", date=" + date +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", idUser='" + idUser + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", address='" + address + '\'' +
                '}';
    }
}
