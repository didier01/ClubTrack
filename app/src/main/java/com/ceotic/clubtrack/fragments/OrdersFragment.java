package com.ceotic.clubtrack.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.adapter.address.AddressAdapter;
import com.ceotic.clubtrack.adapter.menuProduct.ProductAdapter;
import com.ceotic.clubtrack.adapter.order.OrderAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.LocationPlace;
import com.ceotic.clubtrack.model.Order;
import com.ceotic.clubtrack.model.Product;
import com.ceotic.clubtrack.model.SubCategory;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class OrdersFragment extends Fragment {
    private static final String TAG = OrdersFragment.class.getSimpleName();
    private Realm realm;
    private AppControl appControl;

    private ListView list;
    private OrderAdapter adapter;
    private List<Order> listOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        appControl = AppControl.getInstance();
        realm = Realm.getDefaultInstance();

        list = view.findViewById(R.id.listview_orders_fragment);

        llenarLista();
        return view;
    }

    //region Llenando el recycler
    public void llenarLista() {
        List<Order> typeList = new ArrayList<>();

        RealmResults<Order> findOrder = realm.where(Order.class)
                .equalTo("idUser", appControl.currentUser.getDniUser())
                .equalTo("status", Order.SENDED)
                .findAll();

        Log.e(TAG, "Cantidad de tipos: " + findOrder.size());
        for (Order order : findOrder) {
            //typeList.add(pro);
            Log.d(TAG, "name: " + order.getAddress());
            typeList.addAll(findOrder);
        }

        typeList = findOrder;
        listOrder = typeList;
        adapter = new OrderAdapter(getContext(), typeList);
        list.setAdapter(adapter);
    }
    //endregion

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
