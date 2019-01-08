package com.ceotic.clubtrack.activities.splash;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ceotic.clubtrack.activities.login.LoginActivity;
import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.adapter.menu.MenuAdapter;
import com.ceotic.clubtrack.control.AppControl;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

public class SplashActivity extends AppCompatActivity implements AppControl.InitComplete {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private AppControl appControl;
    private Realm realm;
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
                if (AppControl.getInstance().isLogged) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    SplashActivity.this.finish();
                } else {
                    Intent goMenu = new Intent(getApplicationContext(), LoginActivity.class);
                    goMenu.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(goMenu);
                    SplashActivity.this.finish();
                    Log.e(TAG, "Si paso el splash");
                }
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
