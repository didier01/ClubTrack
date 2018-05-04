package com.ceotic.clubtrack.activities.registry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.login.LoginActivity;
import com.ceotic.clubtrack.activities.menu.MainActivity;

public class RegistryLocationActivity extends AppCompatActivity {

    Button btnLocation, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry_location);

        btnLocation = findViewById(R.id.btn_go_location);
        btnSave = findViewById(R.id.btn_save_location);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goMenu = new Intent(getApplicationContext(), LoginActivity.class);
                Toast.makeText(RegistryLocationActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                startActivity(goMenu);
                RegistryLocationActivity.this.finish();
            }
        });

    }


}
