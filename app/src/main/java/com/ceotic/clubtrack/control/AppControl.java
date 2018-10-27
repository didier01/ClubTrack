package com.ceotic.clubtrack.control;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.model.DetailOrder;
import com.ceotic.clubtrack.model.LocationPlace;
import com.ceotic.clubtrack.model.Order;
import com.ceotic.clubtrack.model.Product;
import com.ceotic.clubtrack.model.ProductType;
import com.ceotic.clubtrack.model.User;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class AppControl {

    public final String TAG = AppControl.class.toString();

    private static final AppControl ourInstance = new AppControl();

    public String currentActivity = "currentActivity";
    Realm realm;
    ProductType productType;
    DetailOrder detailOrder;
    private Context context;
    private boolean init = false;


    //region patron singleton
    public static AppControl getInstance() {
        return ourInstance;
    }

    public AppControl() {
    }
    //endregion


    public interface InitComplete {
        public void initComplete(boolean result);
    }

    public boolean init(final InitComplete listener, final Context context) {

        this.context = context;
        init = true;
        realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                //region insertar tipos de prouctos
                if (realm.where(ProductType.class).findAll().isEmpty()) {

                    //productType = realm.createObject(ProductType.class);
                    ProductType pdt1 = new ProductType(R.drawable.aseococina, "Aseo Cocina");
                    ProductType pdt2 = new ProductType(R.drawable.care, "Cuidado personal");
                    ProductType pdt3 = new ProductType(R.drawable.ropa, "Cuidado ropa");
                    ProductType pdt4 = new ProductType(R.drawable.lineahogar, "Linea hogar");

                    realm.copyToRealm(pdt1);
                    realm.copyToRealm(pdt2);
                    realm.copyToRealm(pdt3);
                    realm.copyToRealm(pdt4);
                }//endregion

                //region Insertar productos
                if (realm.where(Product.class).findAll().isEmpty()) {

                    Product prod = new Product("Aseo Cocina", "Track", R.drawable.cocina, "Jabon para platos", 6000, 400, "gr", 10);
                    Product prod1 = new Product("Cuidado ropa", "Track 1", R.drawable.ropa, "Jabon para vasos", 5000, 600, "gr", 10);
                    Product prod2 = new Product("Cuidado personal", "mas Track", R.drawable.care, "Jabon mas platos", 666, 400, "gr", 10);
                    Product prod3 = new Product("Linea hogar", "Track", R.drawable.cocina, "Jabon huele rico", 9000, 900, "gr", 10);
                    Product prod4 = new Product("Aseo Cocina", "Track", R.drawable.ropa, "Jabon huele mas rico", 6000, 400, "gr", 10);
                    Product prod5 = new Product("Cuidado personal", "Track", R.drawable.care, "Jabon no huele", 20000, 100, "gr", 10);

                    realm.copyToRealm(prod);
                    realm.copyToRealm(prod1);
                    realm.copyToRealm(prod2);
                    realm.copyToRealm(prod3);
                    realm.copyToRealm(prod4);
                    realm.copyToRealm(prod5);
                }

                //endregion

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e("AppControl Menu", "sii inserto");
                RealmResults<ProductType> findTypes = realm.where(ProductType.class).findAll();
                Log.e("AppControl No es Error", "Cantidad de tipos de productod : " + findTypes.size());

                RealmResults<User> findUsers = realm.where(User.class).findAll();
                Log.e("AppControl No es Error", "Cantidad de usuarios : " + findUsers.size());

                RealmResults<Product> findPro = realm.where(Product.class).findAll();
                Log.e("AppControl No es Error", "Cantidad de productos: " + findPro.size());

                RealmResults<LocationPlace> places = realm.where(LocationPlace.class).findAll();
                Log.e("AppControl No es Error", "lugares: " + places);

                listener.initComplete(true);

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("Menu", "no inserto");
                listener.initComplete(true);
                ((Activity) context).finishAffinity();
                ((Activity) context).finish();
            }
        });
        return true;


    }


}
