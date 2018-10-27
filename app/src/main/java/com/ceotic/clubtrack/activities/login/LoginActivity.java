package com.ceotic.clubtrack.activities.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ceotic.clubtrack.activities.registry.MapsActivity;
import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.registry.RegistryLocationActivity;
import com.ceotic.clubtrack.activities.registry.RegistryUserActivity;
import com.ceotic.clubtrack.activities.shop.ShopActivity;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnRegisUser, btnLosePassword;
    EditText edtUser, edtPassword;
    TextView txvRecoverPass;

    Realm realm;
    AppControl appControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        realm = Realm.getDefaultInstance();
        appControl = AppControl.getInstance();

        btnLogin = findViewById(R.id.btn_login);
        edtUser = findViewById(R.id.edt_user);
        edtPassword = findViewById(R.id.edt_password);
        btnRegisUser = findViewById(R.id.btn_login_regis_user);
        btnLosePassword = findViewById(R.id.btn_lose_pass);
        txvRecoverPass = findViewById(R.id.txv_lose_pass);

        btnRegisUser.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        txvRecoverPass.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        //region variables obtenidas
        final String nameUser = edtUser.getText().toString().trim();
        final String password = edtPassword.getText().toString().trim();
        //endregion

        //region busquedas
        RealmResults<User> findUser = realm.where(User.class)
                .equalTo("user", nameUser)
                .findAll();

        RealmResults<User> findPass = realm.where(User.class)
                .equalTo("password", password)
                .findAll();
        //endregion


        switch (v.getId()) {
            case R.id.btn_login:
                if (findUser.isEmpty()) {
                    if (nameUser.length() == 0) {
                        edtUser.setError("Campo requerido");
                        return;
                    }
                    edtUser.setError("Usuario incorrecto");
                } else if (findPass.isEmpty()) {
                    if (password.length() == 0) {
                        edtPassword.setError("Campo requerido");
                        return;
                    }
                    edtPassword.setError("contraseña incorrecto");
                } else {
                    Intent goMenu = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(goMenu);
                }


                break;
            case R.id.btn_login_regis_user:
                Intent goRegistry = new Intent(getApplicationContext(), RegistryUserActivity.class);
                //Intent goRegistry = new Intent(getApplicationContext(), RegistryLocationActivity.class);
                startActivity(goRegistry);
                break;
            case R.id.btn_lose_pass:

           /* case R.id.txv_lose_pass:
                Intent goShop = new Intent(getApplicationContext(), ShopActivity.class);
                startActivity(goShop);
                break;*/
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //LoginActivity.this.finish();
    }


}
