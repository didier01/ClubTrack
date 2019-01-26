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
import com.ceotic.clubtrack.control.AppControl;
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
    private Product product;
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
    private AppControl appControl;

    public DialogBuyProduct(@NonNull Context context, String idproduct) {
        super(context);
        realm = Realm.getDefaultInstance();
        appControl = AppControl.getInstance();
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
                        .equalTo("idUser", appControl.currentUser.getIdUser())
                        .findAll();

                if (findOrders.isEmpty()) {
                    order = realm.createObject(Order.class, UUID.randomUUID().toString());
                    order.setStatus(Order.CREATED);
                    order.setIdUser(appControl.currentUser.getIdUser());
                }
                orderSave = realm.copyFromRealm(realm.where(Order.class)
                        .equalTo("status", Order.CREATED)
                        .equalTo("idUser", appControl.currentUser.getIdUser())
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
            public void execute(Realm realm) {

                // CONSULTO EL ID DEL PRODUCTO Y LO GUARDO EN LA CLASE DETAILORDER

                Product productSave = realm.where(Product.class).equalTo("idProduct", idproduct).findFirst();

                try {
                    final DetailOrder detailOrder1 = realm.where(DetailOrder.class)
                            .equalTo("idProduct", idproduct)
                            .and()
                            .equalTo("idOrder", orderSave.getIdCart())
                            .findFirst();

                    if (detailOrder1 != null) {
                        int quant = detailOrder1.getQuantity() + quantity;
                        detailOrder1.setQuantity(quant);
                        realm.copyToRealmOrUpdate(detailOrder1);
                    } else {
                        detailOrder = realm.createObject(DetailOrder.class, UUID.randomUUID().toString());
                        detailOrder.setIdProduct(productSave.getIdProduct());
                        detailOrder.setQuantity(quantity);
                        detailOrder.setPrice(productSave.getPrice());
                        detailOrder.setIdOrder(orderSave.getIdCart());
                    }

                } catch (Exception e) {
                    Log.e(TAG, "No inserto");
                }

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
