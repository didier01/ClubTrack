package com.ceotic.clubtrack.activities.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.adapter.menuProduct.ProductAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.Category;
import com.ceotic.clubtrack.model.Order;
import com.ceotic.clubtrack.model.Product;
import com.ceotic.clubtrack.model.SubCategory;
import com.ceotic.clubtrack.util.Constants;
import com.ceotic.clubtrack.util.MenuActionBar;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShopActivity extends MenuActionBar {

    private static final String TAG = ShopActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private String name;
    private Realm realm;
    private Order order;
    private AppControl appControl;
    private Category type;

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


        final Category type1 = realm.where(Category.class)
                .equalTo("nameCategory",Constants.NAME)
                .findFirst();

        final SubCategory subCategory = realm.where(SubCategory.class)
                .equalTo("idCategory",Constants.NAME)
                .findFirst();

        //region Crea objeto de Orden para hacer consulta
        try {
            order = realm.copyFromRealm(realm.where(Order.class)
                    .equalTo("status", Order.CREATED)
                    .findFirst());
            Log.e(TAG, "Se asigno la orden");
        } catch (Exception e) {
            Log.e(TAG, "NO se asigno la orden");
        }
        //endregion

        addRecycler(subCategory);
        productAdapter.notifyDataSetChanged();

        getSupportActionBar().setTitle(Constants.NAME);

    }

    //region Llenando el recycler
    public void addRecycler(SubCategory subCategory) {
        List<Product> typeList = new ArrayList<>();

        RealmResults<Product> findTypes = realm.where(Product.class)
                .equalTo("idCategory", subCategory.getIdCategory())
                .findAll();

        Log.e(TAG, "Cantidad de tipos: " + findTypes.size());
        Log.e(TAG, "Cantidad de tipos: " + findTypes);
        for (Product product : findTypes) {
            //typeList.add(pro);
            Log.d(TAG, "name: " + product.getNameProduct());
            typeList.addAll(findTypes);
        }

        typeList = findTypes;
        productList = typeList;
        productAdapter = new ProductAdapter(typeList, getApplicationContext());
        recyclerView.setAdapter(productAdapter);
    }
    //endregion


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        this.startActivity(intent);
    }
}
