package com.ceotic.clubtrack.adapter.address;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.LocationPlace;

import java.util.List;

import io.realm.Realm;


public class AddressAdapter extends BaseAdapter {
    private static final String TAG = AddressAdapter.class.getSimpleName();
    private Context context;
    private List<LocationPlace> list;
    private AppControl appControl;
    private Realm realm;

    public AddressAdapter(Context context, List<LocationPlace> list) {
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
        View v = View.inflate(context, R.layout.item_address, null);
        TextView tvTypeAddress = v.findViewById(R.id.tv_item_address_url);
        TextView tvAddress = v.findViewById(R.id.tv_item_address);
        Button btnEdit = v.findViewById(R.id.btn_item_address_edit);
        Button btnDelete = v.findViewById(R.id.btn_item_address_delete);

        final LocationPlace place = list.get(position);

        tvTypeAddress.setText(place.gettypeAddress());
        tvAddress.setText(place.getAddress());

        //region Borrar Producto
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                try {
                    realm.beginTransaction();
                    place.deleteFromRealm();
                    realm.commitTransaction();

                    Toast.makeText(context, "Dirección eliminada", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "direccion eliminad");

                } catch (Exception e) {
                    Toast.makeText(context, "Dirección no eliminada", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Dirección no eliminada");
                }
            }
        }); //endregion

        return v;
    }
}
