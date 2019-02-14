package com.ceotic.clubtrack.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.settings.SettingsActivity;
import com.ceotic.clubtrack.activities.settings.SettingsDrawerActivity;
import com.ceotic.clubtrack.activities.shop.OrderActivity;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.DetailOrder;
import com.ceotic.clubtrack.model.Order;

import io.realm.Realm;
import io.realm.RealmResults;

public class MenuActionBar extends AppCompatActivity {

    private Order order;
    private Realm realm;
    private AppControl appControl;
    private static final String TAG = MenuActionBar.class.getSimpleName();

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        realm = Realm.getDefaultInstance();
        appControl = AppControl.getInstance();

        //region Crea objeto de Orden para hacer consulta
        try {
            order = realm.copyFromRealm(realm.where(Order.class)
                    .equalTo("status", Order.CREATED)
                    .equalTo("idUser",appControl.currentUser.getIdUser())
                    .findFirst());

            Log.e(TAG, "Se asigno la orden");
        } catch (Exception e) {
            Log.e(TAG, "NO se asigno la orden");
        }
        //endregion

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem itemCart = menu.findItem(R.id.action_car);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();

        //region Condiciones para asignar el numero al carro
        String num = "";
        try {
            RealmResults<DetailOrder> itemsOrder = realm.where(DetailOrder.class)
                    .equalTo("idOrder", order.getIdCart())
                    .findAll();

            if (itemsOrder.isEmpty() || itemsOrder == null) {
                setBadgeCount(this, icon, null);
            } else {
                num = String.valueOf(itemsOrder.size());
                setBadgeCount(this, icon, num);
            }
            Log.e(TAG, "La cantidad supera cero");
        } catch (Exception e) {
            Log.e(TAG, "No hay items ó no conto la cantidad");
        }//endregion

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //region asignando badge
    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }
        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);

    }//endregion

    //region botones del menu
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items

        switch (item.getItemId()) {
            case R.id.action_settings:
                //Intent goSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                Intent goSettings = new Intent(getApplicationContext(), SettingsDrawerActivity.class);
                startActivity(goSettings);
                return true;
            case R.id.action_car:
                try {
                    RealmResults<DetailOrder> itemsOrder = realm.where(DetailOrder.class)
                            .equalTo("idOrder", order.getIdCart())
                            .findAll();
                    if (itemsOrder.isEmpty() || itemsOrder == null) {
                        Toast.makeText(this, "El carro esta vacio", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent goCart = new Intent(this, OrderActivity.class);
                        startActivity(goCart);
                        return true;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "NO se encontraron los items");
                    Toast.makeText(this, "El carro esta vacio", Toast.LENGTH_SHORT).show();
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }//endregion
}
