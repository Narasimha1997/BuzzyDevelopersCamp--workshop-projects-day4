package com.example.narasimha.location2;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity{
    TextView tv;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.text_location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null)
                        tv.setText(location.getLatitude()+"");
                    else Toast.makeText(MainActivity.this, "No location", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (SecurityException e){
            Toast.makeText(this, "Security error", Toast.LENGTH_SHORT).show();
        }
    }

    public void startCallbackActivity(View v){
        startActivity(new Intent(this, CallbackLocation.class));
    }

    public void maps(View v){
        startActivity(new Intent(this, MapsActivity.class));
    }
}
