package com.ceotic.clubtrack.activities.registry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.model.User;

import io.realm.Realm;

public class RegistryUserActivity extends AppCompatActivity {

    Button btnNext;
    EditText edtName, edtDni, edtEmail, edtCel, edtPhone1, edtPhone2, edtUser, edtPassword, edtConfirmPass;
    Realm realm;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry_user);

        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();

        edtName = findViewById(R.id.edt_user_name);
        edtDni = findViewById(R.id.edt_user_dni);
        edtEmail = findViewById(R.id.edt_user_email);
        edtCel = findViewById(R.id.edt_user_cellphone);
        edtPhone1 = findViewById(R.id.edt_user_telephone1);
        edtPhone2 = findViewById(R.id.edt_user_telephone2);
        edtUser = findViewById(R.id.edt_regis_user);
        edtPassword = findViewById(R.id.edt_regis_password);
        edtConfirmPass = findViewById(R.id.edt_regis_confirm_password);

        btnNext = findViewById(R.id.btn_next);

        btnNext.setOnClickListener(goNext);
    }

    // boton continuar
    View.OnClickListener goNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (edtName.getText().length() == 0 || edtEmail.getText().length() == 0 || edtDni.getText().length() == 0 || edtUser.getText().length() == 0 || edtPassword.getText().length() == 0) {
                edtName.setError("Campo requerido");
                edtEmail.setError("Campo requerido");
                edtDni.setError("Campo requerido");
                edtUser.setError("Campo requerido");
                edtPassword.setError("Campo requerido");
                return;

            }
            /*if (edtUser.getText().toString().equals(user.getUser())) {
                edtUser.setError("El Usuario ya exite");
            }*/
            if (edtPassword.getText().toString().equals(edtConfirmPass.getText().toString())) {
                saveDataUser();


            } else {
                edtConfirmPass.getText().clear();
                edtConfirmPass.setError("la contraseña no es igual");

            }

            //Intent goRegisLocation = new Intent(getApplicationContext(), RegistryLocationActivity.class);
            //startActivity(goRegisLocation);


        }
    };


    private void saveDataUser() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                user = bgRealm.createObject(User.class);
                user.setDniUser(edtDni.getText().toString().trim());
                user.setNameUser(edtName.getText().toString().trim());
                user.setCellphone(edtCel.getText().toString().trim());
                user.setTelephone(edtPhone1.getText().toString().trim());
                user.setTelephone2(edtPhone2.getText().toString().trim());
                user.setEmail(edtEmail.getText().toString().trim());


                user.setUser(edtUser.getText().toString().trim());
                user.setPassword(edtPassword.getText().toString().trim());

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(RegistryUserActivity.this, "Sisas parce insertado el perro", Toast.LENGTH_SHORT).show();
                Log.d("succes", "insertado");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(RegistryUserActivity.this, "nada no se inserto el perro", Toast.LENGTH_SHORT).show();
                Log.d("Error", "Noooooo insertadooooooo");
            }
        });
    }
}
