package com.ceotic.clubtrack.activities.registry;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ceotic.clubtrack.R;
import com.ceotic.clubtrack.activities.login.LoginActivity;
import com.ceotic.clubtrack.activities.menu.MainActivity;
import com.ceotic.clubtrack.control.AppControl;
import com.ceotic.clubtrack.model.LocationPlace;
import com.ceotic.clubtrack.model.User;
import com.ceotic.clubtrack.util.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegistryLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = RegistryLocationActivity.class.getSimpleName();
    private Button btnLocation, btnSave;
    private EditText edtAddress;
    private RadioButton rbtnHome, rbtnOffice, rbtnOther;
    private RadioGroup groupPlaces;
    private LocationPlace place;
    private Realm realm;
    private AppControl appControl;
    private GoogleMap mMap;
    private double latitud, longitud;
    private Marker marker;
    private FusedLocationProviderClient mFusedLocationClient;
    private Context context;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry_location);

        appControl = AppControl.getInstance();
        realm = Realm.getDefaultInstance();

        btnLocation = findViewById(R.id.btn_go_location);
        btnSave = findViewById(R.id.btn_save_location);
        edtAddress = findViewById(R.id.edt_address);
        rbtnHome = findViewById(R.id.rbtn_home);
        rbtnOffice = findViewById(R.id.rbtn_office);
        rbtnOther = findViewById(R.id.rbtn_other);
        groupPlaces = findViewById(R.id.rg_location);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.regis_map);
        mapFragment.getMapAsync(this);

        btnSave.setOnClickListener(clicSave);

        //region Crea objeto de Orden para hacer consulta
        try {
            user = realm.copyFromRealm(realm.where(User.class)
                    .equalTo("dniUser", Constants.DNI_USER_LOCATION)
                    .findFirst());

            Log.e(TAG, "Se asigno el usuario");
        } catch (Exception e) {
            Log.e(TAG, "NO se asigno el usuario");
        }
        //endregion

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        btnLocation.setOnClickListener(clicLocation);
    }

    //region guardar datos
    public void savePlace() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                place = bgRealm.createObject(LocationPlace.class, UUID.randomUUID().toString());
                place.setAddress(edtAddress.getText().toString().trim());
                place.setLatitude(latitud);
                place.setLongitude(longitud);
                place.setIdUser(user.getDniUser());

                if (rbtnHome.isChecked()) {
                    place.settypeAddress(Constants.HOME);

                } else if (rbtnOffice.isChecked()) {
                    place.settypeAddress(Constants.OFFICE);

                } else if (rbtnOther.isChecked()) {
                    place.settypeAddress(Constants.OTHER);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                RealmResults<LocationPlace> places = realm.where(LocationPlace.class).findAll();
                Toast.makeText(RegistryLocationActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "insertado " + '\n' + places);

                Intent goMenu = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(goMenu);
                RegistryLocationActivity.this.finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(), "no registrado ", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "NO insertado ");
            }
        });
    }


    //endregion

    //region botones
    View.OnClickListener clicLocation = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            myLocation();
        }
    };

    View.OnClickListener clicSave = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            savePlace();
        }
    };//endregion

    //region Agregar Marcador
    public void addMarker(double latitud, double longitud) {

        LatLng coordenate = new LatLng(latitud, longitud);
        CameraUpdate place = CameraUpdateFactory.newLatLngZoom(coordenate, 16);
        if (marker != null) marker.remove();
        marker = mMap.addMarker(new MarkerOptions()
                .position(coordenate)
                .title("Mi ubicacion actual"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordenate));
        mMap.animateCamera(place);
    }
    //endregion

    //region actualizarPosicion
    public void actulizarUbicaion(Location location) {
        if (location != null) {
            latitud = location.getLatitude();
            longitud = location.getLongitude();
            addMarker(latitud, longitud);
        }
    }
    //endregion

    //region asignar ubicacion
    private void myLocation() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            actulizarUbicaion(location);
                            getAddressFromLocation(location.getLatitude(), location.getLongitude());
                        }
                    });
        } else {
            checkLocationPermission();
        }
    }
    //endregion

    //region pedir permisos de ubicacion
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                //crea un dialogo por si acaso

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        myLocation();
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
//endregion


    //region Obtener Direccion del lugar
    /*
    private String getAddressFromLocation(double latitude, double longitude) {

        String address = "";
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnAddress = addresses.get(0);
                StringBuilder stringAddress = new StringBuilder("");
                for (int i = 0; i <= returnAddress.getMaxAddressLineIndex(); i++) {
                    stringAddress.append(returnAddress.getAddressLine(i)).append("");
                }
                address = stringAddress.toString();
                edtAddress.setText(address);
                Log.e(TAG + " Cuurent location: ", stringAddress.toString());
                Toast.makeText(context, "Address" + stringAddress.toString(), Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG + " Cuurent location: ", "no location");
            }
        } catch (Exception e) {
            Log.e(TAG + " Cuurent location: ", "Cant get address");
        }
        return address;

    }*/
    //endregion

    private String getAddressFromLocation(double latitude, double longitude) {

        String address = "";
        String cityName = "";
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            Address returnAddress = addresses.get(0);
            address = addresses.get(0).getAddressLine(0);
            cityName = addresses.get(0).getLocality();
            String stateName = addresses.get(0).getAdminArea();
            //String countryName = addresses.get(0).getAddressLine(2);

           /* StringBuilder stringAddress = new StringBuilder("");
            for (int i = 0; i <= returnAddress.getMaxAddressLineIndex(); i++) {
                stringAddress.append(returnAddress.getAddressLine(0)).append("");
            }
            address = stringAddress.toString();*/
            edtAddress.setText(address);
            //Log.e(TAG + " Cuurent location: ", stringAddress.toString());
            Log.e(TAG + " Cuurent location: ", address);
            Log.e(TAG + " Cuurent city: ", cityName);
            Log.e(TAG + " Cuurent state: ", stateName);
            //Toast.makeText(context, "Address" + stringAddress.toString(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, "city: " + cityName, Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Log.e(TAG + " Cuurent location2: ", "Cant get address");
        }
        return address;

    }

}
