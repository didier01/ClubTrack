package com.ceotic.clubtrack.activities.shop;

import android.content.Context;
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
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.activities.settings.SettingsActivity;
import com.ceotic.clubtrack.adapter.cart.CartAdapter;
import com.ceotic.clubtrack.adapter.menuProduct.ProductAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.DetailOrder;
import com.ceotic.clubtrack.model.Product;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener{

    Realm realm;
    AppControl appControl;
    Context context;

    RecyclerView recyclerView;
    CartAdapter cartAdapter;

    List<DetailOrder> orderList;

    TextView tvAddMore, tvDeleteAll;
    Button btnOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        appControl = AppControl.getInstance();
        realm = Realm.getDefaultInstance();

        tvAddMore = findViewById(R.id.tv_cart_menu_add_more);
        tvDeleteAll = findViewById(R.id.tv_cart_menu_empty);
        btnOrder = findViewById(R.id.btn_cart_order);

        tvAddMore.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_cart);
        GridLayoutManager llm = new GridLayoutManager(getApplicationContext(), 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        addRecycler();

    }


    //region Llenando el recycler
    public void addRecycler() {
        List<DetailOrder> typeList = new ArrayList<>();

        RealmResults<DetailOrder> findTypes = realm.where(DetailOrder.class)
               // .equalTo("idTypeProduct", name)
                .findAll();
        Log.e("OrderActivity es Error", "Cantidad de tipos: " + findTypes.size());
        for (DetailOrder order : findTypes) {
            //typeList.add(pro);
            Log.d("OrderActivity", "name: " + order.getProduct());
            typeList.addAll(findTypes);
        }

        typeList = findTypes;
        orderList = typeList;
        cartAdapter = new CartAdapter(typeList, getApplicationContext());
        recyclerView.setAdapter(cartAdapter);
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
            case R.id.action_car:
                Intent goCar = new Intent(this, OrderActivity.class);
                startActivity(goCar);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //endregion

    ///region Botones del sistema
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_cart_menu_add_more:
                Intent goMenu = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(goMenu);
                break;
            case R.id.tv_cart_menu_empty:
                break;
            case R.id.btn_cart_order:
                break;
        }

    }
    ///endregion
}
