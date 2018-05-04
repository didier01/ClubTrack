package com.ceotic.clubtrack.activities.menu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.shop.ShopActivity;
import com.ceotic.clubtrack.adapter.menu.MenuAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.LocationPlace;
import com.ceotic.clubtrack.model.ProductType;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Realm realm;
    AppControl appControl;

    RecyclerView recyclerView;
    MenuAdapter menuAdapter;

    List<ProductType> lstProType;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appControl = AppControl.getInstance();
        realm = Realm.getDefaultInstance();

        recyclerView = findViewById(R.id.recycler_menu);
        GridLayoutManager llm = new GridLayoutManager(getApplicationContext(), 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);


        List<ProductType> typeList = new ArrayList<>();

        RealmResults<ProductType> findTypes = realm.where(ProductType.class).findAll();
        Log.e("MainActvity No es Error", "Cantidad de usuarios: " + findTypes.size());
        for (ProductType pro : findTypes) {
            //typeList.add(pro);
            Log.d("MainActivity", "name: " + pro.getNameTypeProduct());
            typeList.addAll(findTypes);
        }

        typeList = findTypes;
        lstProType = typeList;
        menuAdapter = new MenuAdapter(typeList, getApplicationContext());
        recyclerView.setAdapter(menuAdapter);


    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
    }
}
