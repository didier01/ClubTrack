package com.ceotic.clubtrack.activities.registry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.model.User;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

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

    // Logica boton continuar
    View.OnClickListener goNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // region variables que obtengo
            final String email = edtEmail.getEditableText().toString().trim();
            final String nameUser = edtUser.getEditableText().toString().trim();
            final String dniUser = edtDni.getEditableText().toString().trim();
            //endregion

            // region Consultas si exixten los parametros en bd
            RealmResults<User> findUser = realm.where(User.class)
                    .equalTo("user",nameUser)
                    .findAll();

            RealmResults<User> findEmail = realm.where(User.class)
                    .equalTo("email",email)
                    .findAll();

            RealmResults<User> findDni = realm.where(User.class)
                    .equalTo("dniUser",dniUser)
                    .findAll();
            //endregion

            //region Condiciones para insertar
            if (edtName.getText().length() == 0  ) {
                edtName.setError("Campo requerido");
                return;

            }else if (!edtPassword.getText().toString().equals(edtConfirmPass.getText().toString())) {
                edtConfirmPass.setError("la contraseña no es igual");
                if (edtPassword.getText().toString().length() == 0){
                    edtPassword.setError("Campo requerido");
                    return;
                }
                return;

            } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
                if (email.length() == 0){
                    edtEmail.setError("Campo requerido");
                    return;
                }
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                Log.d("BADDDDD","el correo no es valido");
                return;
            } else if(!findEmail.isEmpty()){
                edtEmail.setError("Correo ya esta en uso");
                Toast.makeText(getApplicationContext(), "This mail is already used", Toast.LENGTH_SHORT).show();
                Log.e("BADDDDD","el correo ya esta en uso");
                return;
            } else if(!findUser.isEmpty()) {
                if (nameUser.length() == 0){
                    edtUser.setError("Campo requerido");
                    return;
                }
                edtUser.setError("Este nombre no esta disponible");
                Toast.makeText(getApplicationContext(), "This user Name Already exist", Toast.LENGTH_SHORT).show();
                Log.d("BADDDDD","el nombre de usuario ya existe");
                return;
            } else if (!findDni.isEmpty()){
                if (dniUser.length() == 0){
                    edtDni.setError("Campo requerido");
                    return;
                }
                edtDni.setError("Esta cedula ya esta registrada");
                Toast.makeText(getApplicationContext(), "This Dni is Already used", Toast.LENGTH_SHORT).show();
                Log.d("BADDDDD","la cedula ya existe");
                return;
            }

            else{

                Log.d("OOOKKKK","todo OK con el registro");
                saveDataUser();


            }
            //endregion


        }
    };


    private void saveDataUser() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                user = bgRealm.createObject(User.class, UUID.randomUUID().toString());

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
                RealmResults<User> users = realm.where(User.class).findAll();
                Toast.makeText(RegistryUserActivity.this, "Registro exitoso" , Toast.LENGTH_SHORT).show();
                Log.d("succes", "insertado" + users);

                String idUser = edtDni.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("myid",idUser);

                Intent goRegisLocation = new Intent(getApplicationContext(), RegistryLocationActivity.class);
                goRegisLocation.putExtras(bundle);
                startActivity(goRegisLocation);

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(RegistryUserActivity.this, "No se registro el usuario", Toast.LENGTH_SHORT).show();
                Log.d("Error", "Noooooo insertadooooooo");
            }
        });
    }
}
