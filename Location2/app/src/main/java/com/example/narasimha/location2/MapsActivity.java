package com.example.narasimha.location2;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    FusedLocationProviderClient fusedLocationProviderClient;
    Marker marker;
    double lat, lan;

    private GoogleMap mMap;
    LocationCallback callback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if(locationResult!=null){
                for(Location l : locationResult.getLocations()) {
                    drawMap(l);
                    lat = l.getLatitude(); lan = l.getLongitude();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, callback, null);
            Toast.makeText(this, "Registered location callback", Toast.LENGTH_SHORT).show();
        }catch (SecurityException e){
            Toast.makeText(this, "Enable location permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        fusedLocationProviderClient.removeLocationUpdates(callback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(-34, 151);
        MarkerOptions opts = new MarkerOptions().position(location).title("My location");
        marker = mMap.addMarker(opts);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 40));
        mMap.setTrafficEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                fusedLocationProviderClient.removeLocationUpdates(callback);
                launchWeather();
                return false;
            }
        });
    }

    public void drawMap(Location location){
        LatLng lng = new LatLng(location.getLatitude(), location.getLongitude());
        marker.setPosition(lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lng));
    }

    public void launchWeather(){
        Intent weatherIntent = new Intent(this, Weather.class);
        weatherIntent.putExtra("lat", lat);
        weatherIntent.putExtra("lng", lan);
        startActivity(weatherIntent);
    }
}
