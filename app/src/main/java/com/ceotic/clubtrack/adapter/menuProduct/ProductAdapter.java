package com.ceotic.clubtrack.adapter.menuProduct;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.Product;
import com.ceotic.clubtrack.model.ProductType;

import java.util.List;

import io.realm.Realm;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProducViewHolder> {

    List<Product> list;

    Context context;
    AppControl appControl;
    Realm realm;

    public ProductAdapter(List<Product> list, Context context) {

        realm = Realm.getDefaultInstance();
        appControl = AppControl.getInstance();

        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProducViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProducViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProducViewHolder holder, int position) {

        final Product product = list.get(position);

        holder.imvProduct.setImageResource(product.getImageProduct());
        holder.txvName.setText(product.getNameProduct());
        holder.txvDescription.setText(product.getDescriptionProduct());
        holder.txvPrice.setText(""+product.getPrice());
        holder.txvQuantity.setText(""+product.getQuantity());
        holder.txvTypeQuantity.setText(product.getTypeQuantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ProducViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imvProduct;
        protected TextView txvName;
        protected TextView txvDescription;
        protected TextView txvPrice;
        protected TextView txvQuantity;
        protected TextView txvTypeQuantity;
        protected TextView txvPoints;

        public ProducViewHolder(View itemView) {
            super(itemView);

            imvProduct = itemView.findViewById(R.id.imv_item_product);
            txvName = itemView.findViewById(R.id.txv_item_name_product);
            txvDescription = itemView.findViewById(R.id.txv_item_description_product);
            txvPrice = itemView.findViewById(R.id.txv_item_price_product);
            txvQuantity = itemView.findViewById(R.id.txv_item_size_product);
            txvTypeQuantity = itemView.findViewById(R.id.txv_item_type_size_product);
        }
    }
}
