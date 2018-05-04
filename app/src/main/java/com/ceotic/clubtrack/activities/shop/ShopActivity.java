package com.ceotic.clubtrack.activities.shop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.ceotic.clubtrack.R;
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



        List<Product> typeList = new ArrayList<>();

        RealmResults<Product> findTypes = realm.where(Product.class).findAll();
        Log.e("ShopActivityNo es Error","CAntidad de usuarios: "+findTypes.size());
        for(Product pro: findTypes){
            //typeList.add(pro);
            Log.d("ShopActivity","name: " + pro.getNameProduct());
            typeList.addAll(findTypes);
        }

        typeList = findTypes;
        productList = typeList;
        productAdapter = new ProductAdapter(typeList,getApplicationContext());
        recyclerView.setAdapter(productAdapter);


        addRecycler();

    }

    public void addRecycler(){
        List<Product> typeList = new ArrayList<>();

        RealmResults<Product> findTypes = realm.where(Product.class).findAll();
        Log.e("No es Error","CAntidad de usuarios: "+findTypes.size());
        for(Product pro: findTypes){
            //typeList.add(pro);
            Log.d("MainActivity","name: " + pro.getNameProduct());
            typeList.addAll(findTypes);
        }

        typeList = findTypes;
        productList = typeList;
        productAdapter = new ProductAdapter(typeList,getApplicationContext());
        recyclerView.setAdapter(productAdapter);
    }
}
