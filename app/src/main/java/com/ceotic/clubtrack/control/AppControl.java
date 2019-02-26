package com.ceotic.clubtrack.control;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.model.Category;
import com.ceotic.clubtrack.model.Configuration;
import com.ceotic.clubtrack.model.DetailOrder;
import com.ceotic.clubtrack.model.LocationPlace;
import com.ceotic.clubtrack.model.Product;
import com.ceotic.clubtrack.model.SubCategory;
import com.ceotic.clubtrack.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class AppControl {

    public final String TAG = AppControl.class.toString();

    private static final AppControl ourInstance = new AppControl();

    public String currentActivity = "currentActivity";
    Realm realm;
    private Category productType;
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

                //region insertar categorias
                if (realm.where(Category.class).findAll().isEmpty()) {

                    //productType = realm.createObject(Category.class);
                    Category pdt1 = new Category(R.drawable.cheeses, "Quesos");
                    Category pdt2 = new Category(R.drawable.derivatives, "Derivados");
                    Category pdt3 = new Category(R.drawable.dairy, "Lacteos");

                    realm.copyToRealm(pdt1);
                    realm.copyToRealm(pdt2);
                    realm.copyToRealm(pdt3);
                }//endregion

                //region insertar subcategorias
                if (realm.where(SubCategory.class).findAll().isEmpty()) {

                    SubCategory cat1 = new SubCategory("Quesos", "Queso parmesano");
                    SubCategory cat2 = new SubCategory("Quesos", "Queso mozzarella");
                    SubCategory cat3 = new SubCategory("Quesos", "Queso campesino");
                    SubCategory cat4 = new SubCategory("Quesos", "Queso otro");
                    SubCategory cat5 = new SubCategory("Lacteos", "Lacteos");
                    SubCategory cat6 = new SubCategory("Derivados", "Derivados");

                    realm.copyToRealm(cat1);
                    realm.copyToRealm(cat2);
                    realm.copyToRealm(cat3);
                    realm.copyToRealm(cat4);
                    realm.copyToRealm(cat5);
                    realm.copyToRealm(cat6);

                }//endregion


                //region Insertar productos
                if (realm.where(Product.class).findAll().isEmpty()) {

                    Product prod = new Product("Queso parmesano","Quesos", "Queso parmesano", R.drawable.cheese_parmesano, "Delicioso", 6000, 500, "gr", 10);
                    Product prod1 = new Product("Queso mozzarella","Quesos", "Queso mozzarella", R.drawable.cheese_mozzarella, "Para acomppñar eñ desayuno", 7000, 600, "gr", 10);
                    Product prod2 = new Product("Queso mozzarella","Quesos", "Queso mozzarella", R.drawable.cheese_mozzarella1, "Siempre fresco", 6500, 400, "gr", 10);
                    Product prod3 = new Product("Queso campesino","Quesos", "Queso campesino", R.drawable.cheese_campesino, "del campo a tu mesa", 7000, 500, "gr", 10);
                    Product prod4 = new Product("Queso campesino","Quesos", "Cuajada", R.drawable.cuajada_colombiana, "saludable", 6000, 400, "gr", 10);
                    Product prod5 = new Product("Queso otro", "Quesos","Queso costeño", R.drawable.cheese_coste_o, "al mejor precio", 2000, 500, "gr", 10);
                    Product prod6 = new Product("Queso otro","Quesos", "Queso cheddar", R.drawable.cheese_cheddar, "", 9000, 450, "gr", 10);
                    Product prod7 = new Product("Queso otro","Quesos", "Queso edam", R.drawable.cheese_edam, "", 15000, 450, "gr", 10);

                    Product milk = new Product("Lacteos","Lacteos", "Leche", R.drawable.milk, "", 2500, 1000, "ml", 10);
                    Product yogurt = new Product("Lacteos","Lacteos", "Yogurt", R.drawable.yogurt, "", 7000, 1000, "ml", 10);
                    Product kumis = new Product("Lacteos","Lacteos", "Kumis", R.drawable.kumis, "", 10000, 1000, "ml", 10);
                    Product suero = new Product("Lacteos","Lacteos", "Suero de leche", R.drawable.suero_de_leche, "", 10000, 1000, "ml", 10);

                    Product milkcream = new Product("Derivados","Derivados", "Crema de leche", R.drawable.milk_cream, "", 3500, 250, "gr", 10);
                    Product lechera = new Product("Derivados","Derivados", "Leche condensada", R.drawable.condensada, "", 2500, 150, "gr", 10);
                    Product butter = new Product("Derivados","Derivados", "Mantequilla", R.drawable.butter, "", 4500, 250, "gr", 10);

                    realm.copyToRealm(prod);
                    realm.copyToRealm(prod1);
                    realm.copyToRealm(prod2);
                    realm.copyToRealm(prod3);
                    realm.copyToRealm(prod4);
                    realm.copyToRealm(prod5);
                    realm.copyToRealm(prod6);
                    realm.copyToRealm(prod7);

                    realm.copyToRealm(milk);
                    realm.copyToRealm(yogurt);
                    realm.copyToRealm(kumis);
                    realm.copyToRealm(suero);

                    realm.copyToRealm(milkcream);
                    realm.copyToRealm(lechera);
                    realm.copyToRealm(butter);
                }

                //endregion

                //region insertar un User
                if (realm.where(User.class).findAll().isEmpty()) {
                    User User = new User("1061", "Didier", "didier@gmail.com", "3128216677", "839", "338", "didier", "1234");
                    User User1 = new User("1062", "Enrique", "enrique@gmail.com", "3052223357", "839", "338", "kike", "1234");
                    realm.copyToRealm(User);
                    realm.copyToRealm(User1);
                }//endregion

                //region insertar ubicacion
                if (realm.where(LocationPlace.class).findAll().isEmpty()) {
                    LocationPlace place = new LocationPlace("1061", "Carrera 28a # 8 a 17", "Hogar", 2.446273, -76.626234);
                    LocationPlace place2 = new LocationPlace("1061", "Calle 26A Norte #431", "Oficina", 2.455105, -76.589232);
                    LocationPlace place1 = new LocationPlace("1062", "VILLA DOCENTE, Calle 26A Norte ## 4-29, Popayán, Cauca", "Oficina", 2.455105, -76.589232);
                    LocationPlace place3 = new LocationPlace("1061", "Carrera 28a # 8 a 17", "Hogar", 2.446273, -76.626234);
                    LocationPlace place4 = new LocationPlace("1061", "Calle 26A Norte #431", "Oficina", 2.455105, -76.589232);
                    LocationPlace place5 = new LocationPlace("1061", "VILLA DOCENTE, Calle 26A Norte ## 4-29, Popayán, Cauca", "Oficina", 2.455105, -76.589232);
                    LocationPlace place6 = new LocationPlace("1061", "Carrera 28a # 8 a 17", "Hogar", 2.446273, -76.626234);
                    LocationPlace place7 = new LocationPlace("1061", "Calle 26A Norte #431", "Oficina", 2.455105, -76.589232);
                    LocationPlace place8 = new LocationPlace("1061", "Calle 26A Norte #431", "Oficina", 2.455105, -76.589232);
                    LocationPlace place9 = new LocationPlace("1061", "Calle 26A Norte #431", "Oficina", 2.455105, -76.589232);
                    LocationPlace place10 = new LocationPlace("1061", "Calle 26A Norte #431", "Oficina", 2.455105, -76.589232);
                    LocationPlace place11 = new LocationPlace("1061", "Calle 26A Norte #431", "Oficina", 2.455105, -76.589232);


                    realm.copyToRealm(place);
                    realm.copyToRealm(place1);
                    realm.copyToRealm(place2);
                    realm.copyToRealm(place3);
                    realm.copyToRealm(place4);
                    realm.copyToRealm(place5);
                    realm.copyToRealm(place6);
                    realm.copyToRealm(place7);
                    realm.copyToRealm(place8);
                    realm.copyToRealm(place9);
                    realm.copyToRealm(place10);
                    realm.copyToRealm(place11);
                }
                //endregion

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
                RealmResults<Category> findTypes = realm.where(Category.class).findAll();
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
