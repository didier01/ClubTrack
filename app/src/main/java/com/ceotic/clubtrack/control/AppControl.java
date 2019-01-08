package com.ceotic.clubtrack.control;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.model.Configuration;
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
    private ProductType productType;
    private DetailOrder detailOrder;
    private Context context;
    private boolean init = false;
    public User currentUser;
    public boolean isLogged = false;


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

                //region insertar un User
                if (realm.where(User.class).findAll().isEmpty()) {
                    User User = new User("Didier", "1061", "didier@gmail.com", "3128216677", "839", "338", "didier", "1234");
                    User User1 = new User("enrique", "1062", "enrique@gmail.com", "3052223357", "839", "338", "kike", "1234");
                    User User2 = new User("Edwin", "1062", "edwingmail.com", "3052223357", "839", "338", "edwin", "1234");
                    realm.copyToRealm(User);
                    realm.copyToRealm(User1);
                    realm.copyToRealm(User2);
                }//endregion

                //region Permitir logeo cuando sale de la app
                Configuration config = realm.where(Configuration.class)
                        .equalTo("key", "isLogged")
                        .findFirst();
                try {

                    if (config != null) {
                        Log.d(TAG, "Configuration isLogged  founded = " + config.getValue());
                        isLogged = config.getValue();
                    } else {
                        Log.d(TAG, "Configuration isLogged not founded");
                        config = new Configuration("isLogged", false);
                        realm.copyToRealm(config);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "No cambio estado isLogged");
                }

                try {
                    User user = realm.copyFromRealm(realm.where(User.class)
                            .equalTo("idUser", config.getIdUserLogin())
                            .findFirst());
                    currentUser = user;
                    Log.e(TAG, "Se asigno usuario");
                } catch (Exception e) {
                    Log.e(TAG, "No Se asigno usuario");
                }
                //endregion

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "sii inserto");
                RealmResults<ProductType> findTypes = realm.where(ProductType.class).findAll();
                Log.e(TAG, "Cantidad de tipos de productod : " + findTypes.size());

                RealmResults<User> findUsers = realm.where(User.class).findAll();
                Log.e(TAG, "Cantidad de Users : " + findUsers + "\n \n");

                RealmResults<Product> findPro = realm.where(Product.class).findAll();
                Log.e(TAG, "Cantidad de productos: " + findPro.size());

                RealmResults<LocationPlace> places = realm.where(LocationPlace.class).findAll();
                Log.e(TAG, "lugares: " + places);

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
