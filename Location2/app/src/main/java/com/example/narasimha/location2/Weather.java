package com.example.narasimha.location2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Weather extends AppCompatActivity {
    TextView coords, weather_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        coords = findViewById(R.id.coordinates);
        weather_container = findViewById(R.id.weather_container);
        Intent this_intent = getIntent();

        //update coords:
        double lat = this_intent.getDoubleExtra("lat", 0.00);
        double lng = this_intent.getDoubleExtra("lng", 0.00);
        coords.setText(lat+","+lng);
        updateWeather(lat, lng);
    }

    public void updateWeather(double lat, double lng){
        String url = "http://api.openweathermap.org/data/2.5/weather";
        Ion.with(this).load("GET", url)
                .setBodyParameter("lat", Double.toString(lat))
                .setBodyParameter("lon", Double.toString(lng))
                .setBodyParameter("appid", "d6424b10732aba17709becf641dcc73a")
                .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if(e!=null) Toast.makeText(Weather.this, "Error", Toast.LENGTH_SHORT).show();
                if(result==null){
                    Toast.makeText(Weather.this, "Weather data is null", Toast.LENGTH_SHORT).show();
                }else{
                    String weather = result.toString();
                    weather_container.setText(weather);
                }
            }
        });
    }

    public void staticTest(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
}
