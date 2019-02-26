package com.ceotic.clubtrack.activities.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.registry.RegistryLocationActivity;
import com.ceotic.clubtrack.activities.registry.RegistryUserActivity;
import com.ceotic.clubtrack.activities.shop.ShopActivity;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.Configuration;
import com.ceotic.clubtrack.model.User;

import io.realm.Realm;
import io.realm.RealmResults;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button btnLogin, btnRegisUser, btnLosePassword;
    private EditText edtUser, edtPassword;
    private TextView txvRecoverPass;
    private Realm realm;
    private AppControl appControl;

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

        if (AppControl.getInstance().isLogged) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:
                //region variables obtenidas
                final String nameUser = edtUser.getText().toString().trim();
                final String password = edtPassword.getText().toString().trim();
                //endregion

                if (nameUser.length() == 0) {
                    edtUser.setError("Campo requerido");
                    return;
                } else if (password.length() == 0) {
                    edtPassword.setError("Campo requerido");
                    return;
                } else {
                    try {
                        final User currentUser = realm.copyFromRealm(realm.where(User.class)
                                .equalTo("user", nameUser)
                                .equalTo("password", password)
                                .findFirst());

                        if (currentUser != null) {
                            realm.executeTransactionAsync(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {

                                    try {
                                        Configuration config = realm.where(Configuration.class)
                                                .equalTo("key", "isLogged")
                                                .findFirst();
                                        config.setValue(true);
                                        config.setIdUserLogin(currentUser.getIdUser());
                                    } catch (Exception e) {
                                    }
                                }
                            }, new Realm.Transaction.OnSuccess() {
                                @Override
                                public void onSuccess() {
                                    Configuration config = realm.where(Configuration.class)
                                            .equalTo("key", "isLogged")
                                            .findFirst();
                                    Log.e(TAG, "se logeo" + currentUser.getIdUser());
                                    Log.e(TAG, "se logeo" + config.getIdUserLogin());
                                }
                            }, new Realm.Transaction.OnError() {
                                @Override
                                public void onError(Throwable error) {
                                    Log.e(TAG, "no se logeo");
                                }
                            });
                            appControl.currentUser = currentUser;
                            Intent goMenu = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(goMenu);
                            finish();
                        } else {
                            edtUser.setError("");
                        }
                    }catch (Exception e){
                        edtPassword.getText().clear();
                        edtUser.getText().clear();
                        edtUser.setError("");
                        edtPassword.setError("");
                        Toast.makeText(LoginActivity.this, "Usuario y/o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                    }
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
