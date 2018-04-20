package com.ceotic.clubtrack.activities.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtUser, edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        edtUser = findViewById(R.id.edt_user);
        edtPassword = findViewById(R.id.edt_password);


        btnLogin.setOnClickListener(login);
    }

    View.OnClickListener login = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent goMenu = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(goMenu);
        }
    };
}
