package com.ceotic.clubtrack.adapter.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.model.ProductType;

import java.util.List;

import io.realm.Realm;

public class MenuAdapter extends BaseAdapter {

    Context context;
    List<ProductType> list;

    Realm realm;


    public MenuAdapter(Context context, List<ProductType> list) {

       // realm = Realm.getDefaultInstance();

        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(context, R.layout.item_menu,null);
        ImageView imvTypeProduct = v.findViewById(R.id.imv_item_menu);
        TextView txvTypeProduct = v.findViewById(R.id.txv_item_menu);


        return null;
    }
}
