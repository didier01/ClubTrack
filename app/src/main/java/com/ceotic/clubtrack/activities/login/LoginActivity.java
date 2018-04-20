package com.ceotic.clubtrack.activities.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.registry.RegistryUserActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnRegisUser, btnLosePassword;
    EditText edtUser, edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_login);
        edtUser = findViewById(R.id.edt_user);
        edtPassword = findViewById(R.id.edt_password);
        btnRegisUser = findViewById(R.id.btn_login_regis_user);
        btnLosePassword = findViewById(R.id.btn_lose_pass);

        btnRegisUser.setOnClickListener(this);
        btnLogin.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent goMenu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goMenu);

                break;
            case R.id.btn_login_regis_user:
                Intent goRegistry = new Intent(getApplicationContext(), RegistryUserActivity.class);
                startActivity(goRegistry);
                break;
            case R.id.btn_lose_pass:
                break;
        }
    }
}
