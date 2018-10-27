package com.ceotic.clubtrack.activities.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ceotic.clubtrack.activities.login.LoginActivity;
import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.control.AppControl;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

public class SplashActivity extends AppCompatActivity implements AppControl.InitComplete {

    AppControl appControl;
    Realm realm;
    private boolean loading = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        appControl = AppControl.getInstance();
        appControl.currentActivity = SplashActivity.class.getSimpleName();
        realm = Realm.getDefaultInstance();


        appControl.init(SplashActivity.this, getApplicationContext());

        startAnimation();
    }

    private void startAnimation() {
        TimerTask tarea2 = new TimerTask() {
            @Override
            public void run() {
                //Intent goMenu = new Intent(getApplicationContext(), LoginActivity.class);
                Intent goMenu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goMenu);
            }
        };


        Timer timer = new Timer();
        timer.schedule(tarea2, 3000);


    }

    @Override
    public void initComplete(boolean result) {
        loading = result;
    }
}
