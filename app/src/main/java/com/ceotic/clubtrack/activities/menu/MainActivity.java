package com.ceotic.clubtrack.activities.menu;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.adapter.menu.MenuAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.Category;
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
    private List<Category> lstProType;

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
        List<Category> typeList = new ArrayList<>();

        RealmResults<Category> findTypes = realm.where(Category.class).findAll();
        Log.e(TAG, "Cantidad de tipos : " + findTypes.size());
        for (Category pro : findTypes) {
            Log.d(TAG, "name: " + pro.getNameCategory());
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
