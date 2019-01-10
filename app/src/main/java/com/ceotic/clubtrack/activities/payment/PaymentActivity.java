package com.ceotic.clubtrack.activities.payment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.LocationPlace;
import com.ceotic.clubtrack.model.Order;
import com.ceotic.clubtrack.model.User;
import com.ceotic.clubtrack.util.Constants;

import io.realm.Realm;

public class PaymentActivity extends AppCompatActivity {
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

        String[] typeAddress = {Constants.HOME,Constants.OFFICE,Constants.OTHER};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeAddress));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
}
