package com.ceotic.clubtrack.adapter.cart;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.ceotic.clubtrack.activities.shop.OrderActivity;
import com.ceotic.clubtrack.activities.shop.ShopActivity;
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

    private static final String TAG = CartAdapter.class.getSimpleName();
    private List<DetailOrder> list;
    private Product product;
    private Context context;
    private AppControl appControl;
    private Realm realm;
    private int quantity;

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

        final DetailOrder detailOrder = list.get(position);

        // Asigna un objeto para  realizar la consulta y actualizar
        final DetailOrder detailOrder1 = realm.copyFromRealm(realm.where(DetailOrder.class)
                .equalTo("idDetailCart", list.get(position).getIdDetailCart())
                .findFirst());

        //region asigna los datos del producto
        product = realm.where(Product.class).equalTo("idProduct", detailOrder.getIdProduct()).findFirst();

        holder.imvCartMenu.setImageResource(product.getImageProduct());
        holder.tvNameCartProduct.setText(product.getNameProduct());
        holder.edtQuantity.setText("" + detailOrder.getQuantity());
        holder.tvItemPrice.setText("$"+detailOrder.getPrice());
        //endregion

        //region Add/Remove
        quantity = detailOrder.getQuantity();
        holder.imvLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Disminuir cantidad
                quantity = Integer.parseInt(holder.edtQuantity.getText().toString()) - 1;
                if (quantity < 1) {
                    quantity = 1;
                    holder.edtQuantity.setText(quantity + "");
                } else {
                    holder.edtQuantity.setText(quantity + "");
                }//endregion

                //region Actualizar cantidad
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        detailOrder1.setQuantity(Integer.parseInt(holder.edtQuantity.getText().toString()));
                        realm.copyToRealmOrUpdate(detailOrder1);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "Item actualizado ");
                        Intent intent = new Intent(context, OrderActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Item no actualizado ");
                        error.printStackTrace();
                    }
                });
                //endregion
            }
        });
        holder.imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //region Aumentar
                quantity = Integer.parseInt(holder.edtQuantity.getText().toString()) + 1;
                holder.edtQuantity.setText(quantity + "");
                //endregion

                //region Actualizar cantidad
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        detailOrder1.setQuantity(Integer.parseInt(holder.edtQuantity.getText().toString()));
                        realm.copyToRealmOrUpdate(detailOrder1);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "Item actualizado ");
                        Intent intent = new Intent(context, OrderActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        context.startActivity(intent);
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "Item no actualizado ");
                        error.printStackTrace();
                    }
                });
                //endregion
            }
        });
        //endregion

        //region Actualiza Cantidad del producto
        /*
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        detailOrder1.setQuantity(Integer.parseInt(holder.edtQuantity.getText().toString()));
                        realm.copyToRealmOrUpdate(detailOrder1);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {

                        Toast.makeText(context, "Cantidad actualizada", Toast.LENGTH_SHORT).show();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        Toast.makeText(context, "No Actualizo", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onError: ");
                        error.printStackTrace();
                    }
                });
            }
        });*/ //endregion

        //region Borrar Producto de la lista
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {

            //region Borrar Producto
            public void deleteProduct() {
                try {
                    realm.beginTransaction();
                    detailOrder.deleteFromRealm();
                    realm.commitTransaction();
                    Intent intent = new Intent(context, OrderActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    context.startActivity(intent);

                    Toast.makeText(context, "Registro eliminado", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(context, "Registro no eliminado", Toast.LENGTH_SHORT).show();
                }

            }//endregion

            @Override
            public void onClick(View v) {

                deleteProduct();
                notifyDataSetChanged();
                if (list.size() == 0) {

                    Toast.makeText(context, "Lista vacia", Toast.LENGTH_SHORT).show();
                    Intent goMenu = new Intent(context, ShopActivity.class);
                    goMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(goMenu);
                    Log.e(TAG, "Lista vacia, Se cambio el estado de la orden");
                }
            }
        });//endregion de

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
        protected TextView tvNameCartProduct, tvItemPrice;
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
            tvItemPrice = itemView.findViewById(R.id.tv_cart_item_price);

        }

    }
}
