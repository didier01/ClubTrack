package com.ceotic.clubtrack.activities.shop;

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
import android.widget.ListView;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.settings.SettingsActivity;
import com.ceotic.clubtrack.adapter.menu.MenuAdapter;
import com.ceotic.clubtrack.adapter.menuProduct.ProductAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.Product;
import com.ceotic.clubtrack.model.ProductType;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShopActivity extends AppCompatActivity {

    Realm realm;
    AppControl appControl;

    RecyclerView recyclerView;
    ProductAdapter productAdapter;

    List<Product> productList;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        appControl = AppControl.getInstance();
        realm = Realm.getDefaultInstance();

        recyclerView = findViewById(R.id.recycler_product);
        GridLayoutManager llm = new GridLayoutManager(getApplicationContext(), 2);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        name = getIntent().getStringExtra("name");
        addRecycler();

    }

    //region Llenando el recycler
    public void addRecycler() {
        List<Product> typeList = new ArrayList<>();

        RealmResults<Product> findTypes = realm.where(Product.class)
                .equalTo("idTypeProduct", name)
                .findAll();
        Log.e("ShopActivityNo es Error", "Cantidad de tipos: " + findTypes.size());
        for (Product pro : findTypes) {
            //typeList.add(pro);
            Log.d("ShopActivity", "name: " + pro.getNameProduct());
            typeList.addAll(findTypes);
        }

        typeList = findTypes;
        productList = typeList;
        productAdapter = new ProductAdapter(typeList, getApplicationContext());
        recyclerView.setAdapter(productAdapter);
    }
    //endregion

    //region ajustes actionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent goSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(goSettings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion
}
