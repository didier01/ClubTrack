package com.ceotic.clubtrack.adapter.menu;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.shop.ShopActivity;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.ProductType;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    List<ProductType> list;

    Context context;
    AppControl appControl;
    Realm realm;



    public MenuAdapter(List<ProductType> list, Context context) {

        realm = Realm.getDefaultInstance();
        appControl = AppControl.getInstance();

        this.list = list;
        this.context = context;
    }


    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);


    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ShopActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

        final ProductType productType = list.get(position);

        //int image = context.getResources().getIdentifier()
        holder.imvTypeProduct.setImageResource(productType.getImageType());
        holder.txvNameTypeProduct.setText(productType.getNameTypeProduct());



    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected ImageView imvTypeProduct;
        protected TextView txvNameTypeProduct;


        public MenuViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            imvTypeProduct = itemView.findViewById(R.id.imv_item_menu);
            txvNameTypeProduct = itemView.findViewById(R.id.txv_item_menu);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (int) getItemId();
                    Intent intent = new Intent(v.getContext(),ShopActivity.class);
                    v.getContext().startActivity(intent);
                    Log.e("MenuAdapter","ID del item "+id);

                }
            });

        }


        @Override
        public void onClick(View v) {

        }
    }
}

