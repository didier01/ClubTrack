package com.ceotic.clubtrack.adapter.cart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.adapter.menuProduct.ProductAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.DetailOrder;
import com.ceotic.clubtrack.model.Order;
import com.ceotic.clubtrack.model.Product;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements View.OnClickListener {

    List<DetailOrder> list;

    Product product;

    Context context;
    AppControl appControl;
    Realm realm;

    private int quantity;
    //protected EditText edtQuantity1;

    public CartAdapter(List<DetailOrder> list, Context context) {
        realm = Realm.getDefaultInstance();
        appControl = AppControl.getInstance();
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {

        final DetailOrder order = list.get(position);

        product = realm.where(Product.class).equalTo("idProduct", order.getProduct()).findFirst();

        holder.imvCartMenu.setImageResource(product.getImageProduct());
        holder.tvNameCartProduct.setText(product.getNameProduct());
        holder.edtQuantity.setText("" + order.getQuantity());


        //region Add/Remove
        quantity = order.getQuantity();
        holder.imvLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(holder.edtQuantity.getText().toString()) - 1;

                if (quantity < 1) {
                    quantity = 1;
                    holder.edtQuantity.setText(quantity + "");
                } else {
                    holder.edtQuantity.setText(quantity + "");
                }
            }
        });
        holder.imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(holder.edtQuantity.getText().toString()) + 1;
                holder.edtQuantity.setText(quantity + "");
            }
        });
        //endregion

        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RealmResults<DetailOrder> orders = realm.where(DetailOrder.class)
                        .equalTo("idDetailCart", order.getIdDetailCart())
                        .findAll();

                orders.size();

                orders.addChangeListener(new RealmChangeListener<RealmResults<DetailOrder>>() {
                    @Override
                    public void onChange(RealmResults<DetailOrder> detailOrders) {

                       /* int quant = Integer.parseInt(holder.edtQuantity.getText().toString());
                        order.setQuantity(quant);
                        realm.insertOrUpdate(order);*/
                    }
                });

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        order.setQuantity(Integer.parseInt(holder.edtQuantity.getText().toString()));
                        realm.insertOrUpdate(order);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {

                        Toast.makeText(context, "Actualizo con exito" + quantity, Toast.LENGTH_SHORT).show();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(context, "No Actualizo " + quantity, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /// el codigo si sirve
                /*realm.beginTransaction();
                DetailOrder deleteOne = realm.where(DetailOrder.class)
                        .equalTo("idDetailCart", order.getIdDetailCart())
                        .findFirst();
                deleteOne.deleteFromRealm();
                realm.commitTransaction();*/

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {

    }


    public class CartViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imvCartMenu, imvAdd, imvLess;
        protected TextView tvNameCartProduct;
        protected Button btnDelete, btnSave;
        protected EditText edtQuantity;

        public CartViewHolder(View itemView) {
            super(itemView);

            imvCartMenu = itemView.findViewById(R.id.imv_cart_item);
            tvNameCartProduct = itemView.findViewById(R.id.tv_cart_item_name);
            imvAdd = itemView.findViewById(R.id.imv_cart_add);
            imvLess = itemView.findViewById(R.id.imv_cart_remove);
            btnSave = itemView.findViewById(R.id.btn_cart_save);
            btnDelete = itemView.findViewById(R.id.btn_cart_delete);
            edtQuantity = itemView.findViewById(R.id.edt_cart_quantity);

        }

    }
}
