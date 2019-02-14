package com.ceotic.clubtrack.activities.settings;

import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.fragments.AddressesFragment;
import com.ceotic.clubtrack.fragments.ProfileFragment;

public class SettingsDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ProfileFragment.OnFragmentInteractionListener, AddressesFragment.OnFragmentInteractionListener {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction;
        Fragment fragment = new ProfileFragment();
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
        getSupportActionBar().setTitle("Perfíl");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //region ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Ocultar el icono del carrito de compras para esta actividad
        MenuItem itemCart = menu.findItem(R.id.action_car);
        itemCart.setVisible(false);
        MenuItem itemSetting = menu.findItem(R.id.action_settings);
        itemSetting.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }//endregion

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction;
        Fragment fragment = null;

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                getSupportActionBar().setTitle("" + item.getTitle());
                break;
            case R.id.nav_addresses:
                fragment = new AddressesFragment();
                getSupportActionBar().setTitle("" + item.getTitle());
                break;
            case R.id.nav_logout:
                break;
            case R.id.nav_orders:
                getSupportActionBar().setTitle(""+ item.getTitle());
                break;
            case R.id.nav_points:
                getSupportActionBar().setTitle(""+ item.getTitle());
                break;
            case R.id.nav_contact:
                getSupportActionBar().setTitle(""+ item.getTitle());
                break;
        }
        if (null != fragment) {
            transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
            drawer.closeDrawers();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
