package com.ceotic.clubtrack.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.registry.RegistryLocationActivity;
import com.ceotic.clubtrack.activities.registry.RegistryUserActivity;
import com.ceotic.clubtrack.activities.shop.ShopActivity;
import com.ceotic.clubtrack.model.DetailOrder;
import com.ceotic.clubtrack.model.Order;
import com.ceotic.clubtrack.model.Product;
import com.ceotic.clubtrack.model.User;
import com.ceotic.clubtrack.util.MenuActionBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class DialogBuyProduct extends Dialog implements View.OnClickListener {

    private static final String TAG = DialogBuyProduct.class.getSimpleName();
    private DetailOrder detailOrder;
    private Order order, orderSave;
    private Product product, productsave;
    private ImageView imvImageProduct;
    private ImageView imvRemoveProduct;
    private ImageView imvAddProduct;
    private TextView tvNameProduct;
    private TextView tvPriceProduct;
    private TextView tvDetailProduct;
    private EditText edtQuantity;
    private Button btnCancel;
    private Button btnOk;
    private int quantity = 1;
    private String idproduct = "";
    private Realm realm;

    public DialogBuyProduct(@NonNull Context context, String idproduct) {
        super(context);
        realm = Realm.getDefaultInstance();
        this.idproduct = idproduct;
        this.product = realm.where(Product.class).equalTo("idProduct", idproduct).findFirst();
        this.orderSave = orderSave;
        init();

    }

    private void init() {
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_buy_product);
        getWindow().setBackgroundDrawableResource(R.drawable.shadow);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //region asignando variables
        imvImageProduct = findViewById(R.id.imv_image_dialog);
        imvRemoveProduct = findViewById(R.id.imv_remove);
        imvAddProduct = findViewById(R.id.imv_add);
        tvNameProduct = findViewById(R.id.tv_name_dialog);
        tvPriceProduct = findViewById(R.id.tv_price_dialog);
        tvDetailProduct = findViewById(R.id.tv_detail_dialog);
        edtQuantity = findViewById(R.id.edt_quantity_dialog);
        btnCancel = findViewById(R.id.btn_cancel_dialog);
        btnOk = findViewById(R.id.btn_ok_dialog);
        //endregion

        setInfoView();
        edtQuantity.setText(quantity + "");

        //region ASIGNANDO BOTONES
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        imvAddProduct.setOnClickListener(this);
        imvRemoveProduct.setOnClickListener(this);
        //endregion

        // region cambio de cantidad a comprar
        edtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() > 0) {
                    quantity = Integer.valueOf(editable.toString());
                } else {
                    quantity = 0;
                }
            }
        });
        //endregion

    }

    //region Asigna datos al cardView
    private void setInfoView() {

        Picasso.get().load(product.getImageProduct()).into(imvImageProduct);
        tvNameProduct.setText(product.getNameProduct());
        tvPriceProduct.setText("$ " + product.getPrice());
        tvDetailProduct.setText(product.getDescriptionProduct());
    }//endregion

    //region Clic de los botones
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.btn_cancel_dialog:
                dismiss();
                break;
            case R.id.btn_ok_dialog:
                createOrder();
                saveDataCart();
                dismiss();

                Intent intent = new Intent(getContext(), ShopActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getContext().startActivity(intent);

                break;
            case R.id.imv_remove:
                quantity--;
                if (quantity < 1) {
                    quantity = 1;
                    edtQuantity.setText(quantity + "");
                } else {
                    edtQuantity.setText(quantity + "");
                }
                break;
            case R.id.imv_add:
                quantity++;
                edtQuantity.setText(quantity + "");
                break;
        }
    }//endregion

    //region Crea orden para asignar en detailOrder
    public void createOrder() {

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<Order> findOrders = realm.where(Order.class)
                        .equalTo("status", Order.CREATED)
                        .findAll();

                if (findOrders.isEmpty()) {
                    order = realm.createObject(Order.class, UUID.randomUUID().toString());
                    order.setStatus(Order.CREATED);
                }
                orderSave = realm.copyFromRealm(realm.where(Order.class)
                        .equalTo("status", Order.CREATED)
                        .findFirst());
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(getContext(), "si creo la orden ", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Se  creo la orden");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "No creo la orden");
                Toast.makeText(getContext(), "no creo la orden ", Toast.LENGTH_SHORT).show();
            }
        });

    }//endregion

    //region Guarda datos de DetailOrder
    private void saveDataCart() {

        realm.executeTransactionAsync(new Realm.Transaction() {

            @Override
            public void execute(Realm bgRealm) {

                // CONSULTO EL ID DEL PRODUCTO Y LO GUARDO EN LA CLASE DETAILORDER

                productsave = bgRealm.where(Product.class).equalTo("idProduct", idproduct).findFirst();

                detailOrder = bgRealm.createObject(DetailOrder.class, UUID.randomUUID().toString());
                detailOrder.setIdProduct(productsave.getIdProduct());
                detailOrder.setQuantity(quantity);
                detailOrder.setPrice(productsave.getPrice());
                detailOrder.setIdOrder(orderSave.getIdCart());

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                RealmResults<DetailOrder> products = realm.where(DetailOrder.class).findAll();
                Toast.makeText(getContext(), "agregado al carro " + product.getNameProduct(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "insertado" + products);

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getContext(), "No se guardo en el carro " + product.getNameProduct(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Noooooo insertadooooooo");
            }
        });

    }//endregion


}
