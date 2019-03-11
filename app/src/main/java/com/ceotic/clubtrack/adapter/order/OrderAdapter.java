package com.ceotic.clubtrack.adapter.order;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.adapter.address.AddressAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.LocationPlace;
import com.ceotic.clubtrack.model.Order;

import java.util.List;

import io.realm.Realm;

public class OrderAdapter extends BaseAdapter {

    private static final String TAG = OrderAdapter.class.getSimpleName();
    private Context context;
    private List<Order> list;
    private AppControl appControl;
    private Realm realm;

    public OrderAdapter(Context context, List<Order> list) {
        appControl = AppControl.getInstance();
        realm = Realm.getDefaultInstance();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.item_order, null);

        TextView tvNameUser = v.findViewById(R.id.tv_nameuser_item_order);
        TextView tvPrice = v.findViewById(R.id.tv_price_item_order);
        TextView tvDate = v.findViewById(R.id.tv_date_item_order);

        final Order order = list.get(position);

        tvNameUser.setText(appControl.currentUser.getNameUser());
        tvDate.setText(""+order.getDate());

        return v;
    }
}
