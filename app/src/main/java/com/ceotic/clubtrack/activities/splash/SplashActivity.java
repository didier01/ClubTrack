package com.ceotic.clubtrack.activities.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startAnimation();
    }

    private void startAnimation() {
        TimerTask tarea2 = new TimerTask() {
            @Override
            public void run() {
                Intent goMenu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goMenu);
            }
        };


        Timer timer = new Timer();
        timer.schedule(tarea2, 3000);
    }
}
