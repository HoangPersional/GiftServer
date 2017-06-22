package com.example.administrator.surprisegiftserver;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.surprisegiftserver.model.Client;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public int REQUEST_LOCATION = 1;
    private GoogleMap mMap;
    HashMap<String, String> data;
    Client client;
    double lat = 21.030320;
    double lng = 105.835868;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        data = (HashMap<String, String>) getIntent().getSerializableExtra("from_client");
        client = getIntent().getBundleExtra("data").getParcelable("client");
//        Log.v("MAPSAC",client.getName());
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng mylocation = null;
        if (data.get("lat") != null)
            mylocation = new LatLng(Double.parseDouble(data.get("lat")), Double.parseDouble(data.get("long")));
        else
            mylocation = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(mylocation).title(client.getName() + " is here"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 15f));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }
}
