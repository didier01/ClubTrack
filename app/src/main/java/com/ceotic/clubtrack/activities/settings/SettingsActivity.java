package com.ceotic.clubtrack.activities.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.login.LoginActivity;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.Configuration;

import io.realm.Realm;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = SettingsActivity.class.getSimpleName();
    private Button btnLogout;
    private EditText edtName, edtEmail, edtDni, edtPhone, edtCell, edtUser, edtPassword;
    private AppControl appControl;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        appControl = AppControl.getInstance();
        appControl.currentActivity = SettingsActivity.class.getSimpleName();
        realm = Realm.getDefaultInstance();

        btnLogout = findViewById(R.id.btn_logout_settings);
        edtName = findViewById(R.id.edt_name_settings);
        edtEmail = findViewById(R.id.edt_email_settings);
        edtDni = findViewById(R.id.edt_dni_settings);
        edtPhone = findViewById(R.id.edt_phone_settings);
        edtCell = findViewById(R.id.edt_cell_settings);
        edtUser = findViewById(R.id.edt_username_settings);
        edtPassword = findViewById(R.id.edt_pass_settings);

        try {
            edtName.setText(appControl.currentUser.getNameUser());
            edtEmail.setText(appControl.currentUser.getEmail());
            edtDni.setText(appControl.currentUser.getDniUser());
            edtCell.setText(appControl.currentUser.getCellphone());
            edtPhone.setText(appControl.currentUser.getTelephone());
            edtPassword.setText(appControl.currentUser.getPassword());
            edtUser.setText(appControl.currentUser.getUser());

            Log.e(TAG, "asigno los valores");
        } catch (Exception e) {
            Log.e(TAG, "No asigno los valores");
        }

        btnLogout = findViewById(R.id.btn_logout_settings);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appControl.isLogged = false;
                appControl = null;

                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        Configuration configuration = realm.where(Configuration.class)
                                .equalTo("key", "isLogged")
                                .findFirst();
                        configuration.setValue(false);
                        realm.insertOrUpdate(configuration);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "si cambio ");
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                        Log.d(TAG, "no  cambio");
                    }
                });

                Intent goLogin = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(goLogin);
                finish();
            }
        });
    }
}
