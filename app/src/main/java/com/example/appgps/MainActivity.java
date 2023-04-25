package com.example.appgps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.Manifest;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvLong, tvLat, tvAlt;
    private Switch switch1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private PermissionsMarshmallow permissionsMashmallow = new PermissionsMarshmallow(this);
    private String[] PERMISSIONS = { Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLong = findViewById(R.id.tvLong);
        tvLat = findViewById(R.id.tvLat);
        tvAlt = findViewById(R.id.tvAlt);
        switch1 = findViewById(R.id.switch1);

        switch1.setChecked(false);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    iniciarGPS();
                }else{
                    finalizarGPS();
                }
            }
        });
        //instanciar os objetos de geolocalização
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                tvLong.setText(""+location.getLongitude());
                tvLat.setText(""+location.getLatitude());
                tvAlt.setText(""+location.getAltitude());
            }
        };
        CheckPermissionGranted();

    }
    private void CheckPermissionGranted()
    {    if (permissionsMashmallow.hasPermissions(PERMISSIONS)) {
        //  permission granted
    } else {
        // request permission
        permissionsMashmallow.requestPermissions(PERMISSIONS, 2);
    }
    }
    private void finalizarGPS() {
        locationManager.removeUpdates(locationListener);

    }

    private void iniciarGPS() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0,0,locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finalizarGPS();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniciarGPS();
    }
}