package com.ceotic.clubtrack.adapter.menuProduct;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ceotic.clubtrack.model.Product;

import java.util.List;

import io.realm.Realm;

public class ProductAdapter extends BaseAdapter {

    Context context;
    List<Product> list;

    Realm realm;

    public ProductAdapter(Context context, List<Product> list) {
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
        return null;
    }
}
