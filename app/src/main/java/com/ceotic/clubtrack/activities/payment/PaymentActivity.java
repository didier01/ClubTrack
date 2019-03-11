package com.ceotic.clubtrack.activities.payment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.activities.shop.OrderActivity;
import com.ceotic.clubtrack.adapter.address.AddressAdapter;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.LocationPlace;
import com.ceotic.clubtrack.model.Order;
import com.ceotic.clubtrack.model.User;
import com.ceotic.clubtrack.util.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class PaymentActivity extends AppCompatActivity {
    private static final String TAG = PaymentActivity.class.getSimpleName();
    private Spinner spinner;
    private TextView tvAddress;
    private RadioButton rbtnCash, rbtnCard, rbtnPse;
    private Button btnSend;
    private RadioGroup groupPayment;
    private Realm realm;
    private AppControl appControl;
    private List<String> listPlaces1;
    Order order;
    Context context;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        realm = Realm.getDefaultInstance();
        appControl = AppControl.getInstance();

        spinner = findViewById(R.id.spinner_payment);
        tvAddress = findViewById(R.id.tv_address_payment);
        rbtnCard = findViewById(R.id.rbtn_card_payment);
        rbtnCash = findViewById(R.id.rbtn_cash_payment);
        rbtnPse = findViewById(R.id.rbtn_pse_payment);
        groupPayment = findViewById(R.id.rg_payment);
        btnSend = findViewById(R.id.btn_send_order);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rbtnCard.isChecked() || rbtnCash.isChecked() || rbtnPse.isChecked() && address != null) {
                    updateOrder();
                    Toast.makeText(getApplicationContext(), "Pedido exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Diligencie todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setSpinner();
        getAddre();
    }

    //region Llenando Spinner
    public void setSpinner() {
        List<String> typeList1 = new ArrayList<>();

        RealmResults<LocationPlace> findPlaces = realm.where(LocationPlace.class)
                .equalTo("idUser", appControl.currentUser.getDniUser())
                .findAll();

        Log.e(TAG, "Cantidad de tipos: " + findPlaces.size());
        for (LocationPlace loc : findPlaces) {
            Log.d(TAG, "name: " + loc.gettypeAddress());
            typeList1.add(loc.getAddress());
        }
        listPlaces1 = typeList1;
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, typeList1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }//endregion

    //region Actualizar cantidad
    public void updateOrder() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Date date = Calendar.getInstance().getTime();
                Order order1 = realm.copyFromRealm(realm.where(Order.class)
                        .equalTo("status", Order.CREATED)
                        .equalTo("idUser", appControl.currentUser.getIdUser())
                        .findFirst());

                if (order1 != null) {
                    order1.setStatus(Order.SENDED);
                    order1.setDate(date);
                    order1.setAddress(address);
                    realm.copyToRealmOrUpdate(order1);
                }

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                RealmResults<Order> findOrder = realm.where(Order.class)
                        .equalTo("idUser", appControl.currentUser.getDniUser())
                        .equalTo("status", Order.SENDED)
                        .findAll();
                Log.e(TAG, "Item actualizado " + findOrder.size());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "Item no actualizado ");
                error.printStackTrace();
            }
        });

    }//endregion

    //region Obtener Dirección
    public void getAddre() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                address = (String) adapterView.getItemAtPosition(pos);
                Toast.makeText(PaymentActivity.this, "" + address, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }//endregion
}
