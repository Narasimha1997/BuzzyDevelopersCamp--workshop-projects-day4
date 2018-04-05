package com.example.narasimha.location2;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class CallbackLocation extends AppCompatActivity {

    TextView loc_view;
    FusedLocationProviderClient locationClient;
    LocationCallback locationCallback = new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if(locationResult == null) return;
            Toast.makeText(CallbackLocation.this, "Received location update", Toast.LENGTH_SHORT).show();
            for(Location location : locationResult.getLocations()){
                loc_view.setText(location.getLatitude()+" "+location.getLongitude());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback_location);
        loc_view = findViewById(R.id.loc_view);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationClient.removeLocationUpdates(locationCallback);
        Toast.makeText(this, "Removed location updates", Toast.LENGTH_SHORT).show();
    }

    public void registerCallback(View v){
        LocationRequest req = LocationRequest.create();
        req.setInterval(2000);
        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            locationClient.requestLocationUpdates(req, locationCallback, null);
            Toast.makeText(this, "Registered location updates", Toast.LENGTH_SHORT).show();
        }catch (SecurityException e){
            Toast.makeText(this, "Security exception", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeCallback(View v){
        locationClient.removeLocationUpdates(locationCallback);
    }
}
