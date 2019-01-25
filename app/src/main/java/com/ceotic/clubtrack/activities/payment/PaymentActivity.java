package com.ceotic.clubtrack.activities.payment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.LocationPlace;
import com.ceotic.clubtrack.model.Order;
import com.ceotic.clubtrack.model.User;
import com.ceotic.clubtrack.util.Constants;

import io.realm.Realm;
import io.realm.RealmResults;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    private Spinner spinner;
    private TextView tvAddress;
    private Realm realm;
    private AppControl appControl;
    private Order order;
    private User user;
    private LocationPlace place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        realm = Realm.getDefaultInstance();
        appControl = AppControl.getInstance();

        spinner = findViewById(R.id.spinner_payment);
        tvAddress = findViewById(R.id.tv_address_payment);

        String[] typeAddress = {Constants.HOME, Constants.OFFICE, Constants.OTHER};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeAddress));

        getAddre();
    }

    //region Obtener Dirección
    public void getAddre() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                String item = (String) adapterView.getItemAtPosition(pos);
                Toast.makeText(PaymentActivity.this, "" + item, Toast.LENGTH_SHORT).show();

                try {
                    final LocationPlace place1 = realm.where(LocationPlace.class)
                            .equalTo("idUser", appControl.currentUser.getDniUser())
                            .equalTo("typeAddress", item)
                            .findFirst();
                    if (place1 != null) {
                        tvAddress.setText(place1.getAddress());
                    } else {
                        tvAddress.setText("No hay dirección");
                    }
                    Log.e(TAG, "direccion  " + '\n' + place1);

                } catch (Exception e) {
                    Log.e(TAG, "no encontro la direccion");
                }
                RealmResults<LocationPlace> places = realm.where(LocationPlace.class).findAll();
                RealmResults<User> users = realm.where(User.class).findAll();
                Log.e(TAG, "Usuarios " + "\n" + users);
                Log.e(TAG, "Places " + "\n" + places);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }//endregion
}
