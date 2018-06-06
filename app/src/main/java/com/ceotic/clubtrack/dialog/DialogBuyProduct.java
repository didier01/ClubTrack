package com.ceotic.clubtrack.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.model.Product;
import com.squareup.picasso.Picasso;


public class DialogBuyProduct extends Dialog implements View.OnClickListener {

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


    public DialogBuyProduct(@NonNull Context context, Product product) {
        super(context);
        this.product = product;
        init();
    }

    public DialogBuyProduct(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    private void init() {
        //getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_buy_product);
        getWindow().setBackgroundDrawableResource(R.drawable.shadow);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        imvImageProduct = findViewById(R.id.imv_image_dialog);
        imvRemoveProduct = findViewById(R.id.imv_remove);
        imvAddProduct = findViewById(R.id.imv_add);
        tvNameProduct = findViewById(R.id.tv_name_dialog);
        tvPriceProduct = findViewById(R.id.tv_price_dialog);
        tvDetailProduct = findViewById(R.id.tv_detail_dialog);
        edtQuantity = findViewById(R.id.edt_quantity_dialog);
        btnCancel = findViewById(R.id.btn_cancel_dialog);
        btnOk = findViewById(R.id.btn_ok_dialog);

        setInfoView();

        edtQuantity.setText(quantity + "");

        //ASIGNANDO BOTONES
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        imvAddProduct.setOnClickListener(this);
        imvRemoveProduct.setOnClickListener(this);


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


    private void setInfoView() {

        Picasso.get().load(product.getImageProduct()).into(imvImageProduct);
        tvNameProduct.setText(product.getNameProduct());
        tvPriceProduct.setText("$ " + product.getPrice());
        tvDetailProduct.setText(product.getDescriptionProduct());

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.btn_cancel_dialog:
                dismiss();
                break;
            case R.id.btn_ok_dialog:
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
    }
}
