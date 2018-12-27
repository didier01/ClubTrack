package com.ceotic.clubtrack.activities.shop;

import android.app.Activity;
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
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.activities.settings.SettingsActivity;
import com.ceotic.clubtrack.adapter.cart.CartAdapter;
import com.ceotic.clubtrack.adapter.menuProduct.ProductAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.dialog.DialogBuyProduct;
import com.ceotic.clubtrack.model.DetailOrder;
import com.ceotic.clubtrack.model.Order;
import com.ceotic.clubtrack.model.Product;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = OrderActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<DetailOrder> orderList;
    private TextView tvAddMore, tvDeleteAll;
    private Button btnOrder;
    private Order order;
    Realm realm;
    AppControl appControl;

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
        tvDeleteAll.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_cart);
        GridLayoutManager llm = new GridLayoutManager(getApplicationContext(), 1);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        addRecycler();

    }

    //region Intent para volver al menu principal
    public void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this.startActivity(intent);
    }//endregion

    //region Llenando el recycler
    public void addRecycler() {

        //------------------ Crea objeto de Orden para hacer consulta
        try {
            order = realm.copyFromRealm(realm.where(Order.class)
                    .equalTo("status", Order.CREATED)
                    .findFirst());
        } catch (Exception e) {
            Log.e(TAG, "NO se asigno la orden");
        }
        //-------------------

        List<DetailOrder> typeList = new ArrayList<>();

        RealmResults<DetailOrder> findTypes = realm.where(DetailOrder.class)
                .equalTo("idOrder", order.getIdCart())
                .findAll();

        Log.e(TAG, "Cantidad de tipos: " + findTypes.size());
        for (DetailOrder order : findTypes) {
            //typeList.add(pro);
            Log.d(TAG, "name: " + order.getIdProduct());
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

        //------ oculta el icono del carrito para esta actividad
        MenuItem itemCart = menu.findItem(R.id.action_car);
        itemCart.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent goSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(goSettings);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //endregion

    ///region Botones del sistema
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_cart_menu_add_more:
                Intent goMenu = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(goMenu);
                break;
            case R.id.tv_cart_menu_empty:
                deleteAll();
                break;
            case R.id.btn_cart_order:
                break;
        }

    }
    ///endregion

    //region Vaciar lista de productos
    public void deleteAll() {
        try {
            RealmResults<DetailOrder> findItems = realm.where(DetailOrder.class)
                    .equalTo("idOrder", order.getIdCart())
                    .findAll();

            realm.beginTransaction();
            findItems.deleteAllFromRealm();
            realm.commitTransaction();

            Toast.makeText(this, "Lista vacia", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "items eliminados");
            goMain();
        } catch (Exception e) {
            Toast.makeText(this, "Registros no eliminados", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "items no eliminados");
        }
    }//endregion

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goMain();
    }



}
