package com.ceotic.clubtrack.activities.menu;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.settings.SettingsActivity;
import com.ceotic.clubtrack.activities.shop.OrderActivity;
import com.ceotic.clubtrack.adapter.menu.MenuAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.ProductType;
import com.ceotic.clubtrack.util.MenuActionBar;

import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends MenuActionBar {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Realm realm;
    private AppControl appControl;
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;
    private List<ProductType> lstProType;

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

        addRecycler();

    }

    //region Listar los tipos de productos
    public void addRecycler() {
        List<ProductType> typeList = new ArrayList<>();

        RealmResults<ProductType> findTypes = realm.where(ProductType.class).findAll();
        Log.e(TAG, "Cantidad de tipos : " + findTypes.size());
        for (ProductType pro : findTypes) {
            Log.d(TAG, "name: " + pro.getNameTypeProduct());
            typeList.addAll(findTypes);
        }

        typeList = findTypes;
        lstProType = typeList;
        menuAdapter = new MenuAdapter(typeList, getApplicationContext());
        menuAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(menuAdapter);
    }

    //endregion

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
    }
}
