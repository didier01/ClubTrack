package com.ceotic.clubtrack.activities.registry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.login.LoginActivity;
import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.LocationPlace;
import com.ceotic.clubtrack.model.User;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegistryLocationActivity extends AppCompatActivity {

    Button btnLocation, btnSave;
    EditText edtAddress;
    RadioButton rbtnHome, rbtnOffice,rbtnOther;
    RadioGroup groupPlaces;

    LocationPlace place;
    Realm realm;
    AppControl appControl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry_location);

        appControl = AppControl.getInstance();
        realm = Realm.getDefaultInstance();

        btnLocation = findViewById(R.id.btn_go_location);
        btnSave = findViewById(R.id.btn_save_location);
        edtAddress = findViewById(R.id.edt_address);
        rbtnHome = findViewById(R.id.rbtn_home);
        rbtnOffice = findViewById(R.id.rbtn_office);
        rbtnOther = findViewById(R.id.rbtn_other);
        groupPlaces = findViewById(R.id.rg_location);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savePlace();

            }
        });

    }


    public void savePlace() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                place = bgRealm.createObject(LocationPlace.class, UUID.randomUUID().toString());
                place.setAddress(edtAddress.getText().toString().trim());

                if (rbtnHome.isChecked() == true) {
                    place.setUrlAddress("HOME");

                } else if (rbtnOffice.isChecked() == true) {
                    place.setUrlAddress("OFFICE");

                } else if (rbtnOther.isChecked() == true) {
                    place.setUrlAddress("OTHER");
                }

                Bundle bundle = getIntent().getExtras();
                String idUser = bundle.getString("myid");
                place.setIdUser(idUser);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                RealmResults<LocationPlace> places = realm.where(LocationPlace.class).findAll();
                Toast.makeText(RegistryLocationActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                Log.e("RegisLocation succes", "insertado "+ "\n" + places);

                Intent goMenu = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(goMenu);
                RegistryLocationActivity.this.finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(), "no registrado ", Toast.LENGTH_SHORT).show();
                Log.e("RegisLocation Error", "NO insertado ");
            }
        });
    }
}
