package com.ceotic.clubtrack.adapter.menu;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.shop.ShopActivity;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.Category;
import com.ceotic.clubtrack.util.Constants;

import java.util.List;

import io.realm.Realm;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private static final String TAG = MenuAdapter.class.getSimpleName();
    private List<Category> list;
    private Context context;
    private AppControl appControl;
    private Realm realm;

    public MenuAdapter(List<Category> list, Context context) {
        realm = Realm.getDefaultInstance();
        appControl = AppControl.getInstance();
        this.list = list;
        this.context = context;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

        final Category productType = list.get(position);

        holder.imvTypeProduct.setImageResource(productType.getImageCategory());
        holder.txvNameTypeProduct.setText(productType.getNameCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = productType.getNameCategory();
                Constants.NAME = productType.getNameCategory();

                Log.e(TAG,"Name type "+ name);
                Toast.makeText(v.getContext(), ""+ name , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(),ShopActivity.class);
                intent.putExtra("name",name);
                v.getContext().startActivity(intent);
            }
        });

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

        }

        @Override
        public void onClick(View v) {

        }
    }
}

