package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button lightSensorButton,
            proximitySensorButton,
            accelerometerSensorButton,
            magnetometerSensorButton,
            lAccelerometerSensorButton,
            compassButton,
            gyroscopeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightSensorButton=findViewById(R.id.lightSensorButton);
        proximitySensorButton=findViewById(R.id.proximitySensorButton);
        accelerometerSensorButton=findViewById(R.id.accelerometerSensorButton);
        magnetometerSensorButton=findViewById(R.id.magnetometerSensorButton);
        lAccelerometerSensorButton=findViewById(R.id.lAccelerometerSensorButton);
        compassButton=findViewById(R.id.compassButton);
        gyroscopeButton=findViewById(R.id.gyroscopeButton);

        gyroscopeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Gyroscope.class);
                startActivity(i);
            }
        });

        magnetometerSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Magnetometer.class);
                startActivity(i);
            }
        });

        proximitySensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,ProximitySensor.class);
                startActivity(i);
            }
        });

        lAccelerometerSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,LinearAccelerometer.class);
                startActivity(i);
            }
        });

        accelerometerSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Accelerometer.class);
                startActivity(i);
            }
        });

        lightSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,LightSensor.class);
                startActivity(i);
            }
        });

        compassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Compass.class);
                startActivity(i);
            }
        });
    }
}