package com.ceotic.clubtrack.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.adapter.address.AddressAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.LocationPlace;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class AddressesFragment extends Fragment {
    private static final String TAG = AddressesFragment.class.getSimpleName();
    private Button btnAddLocation;
    private Realm realm;
    private AppControl appControl;

    private ListView listAddress;
    private AddressAdapter adapter;
    private List<LocationPlace> listPlaces;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addresses, container, false);
        appControl = AppControl.getInstance();
        realm = Realm.getDefaultInstance();

        btnAddLocation = view.findViewById(R.id.btn_add_addresses_fragment);
        listAddress = view.findViewById(R.id.listview_addresses_fragment);

        llenarLista();
        return view;
    }

    //region llenar lista

    public void llenarLista() {
        List<LocationPlace> typeList = new ArrayList<>();

        RealmResults<LocationPlace> findPlaces = realm.where(LocationPlace.class)
                .equalTo("idUser", appControl.currentUser.getDniUser())
                .findAll();

        Log.e(TAG, "Cantidad de tipos: " + findPlaces.size());
        for (LocationPlace loc : findPlaces) {
            //typeList.add(pro);
            Log.d(TAG, "name: " + loc.gettypeAddress());
            typeList.addAll(findPlaces);
        }

        typeList = findPlaces;
        listPlaces = typeList;
        adapter = new AddressAdapter(getContext(), typeList);
        listAddress.setAdapter(adapter);
    }
    //endregion


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
