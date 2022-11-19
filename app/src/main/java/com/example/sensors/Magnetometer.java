package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class Magnetometer extends AppCompatActivity implements SensorEventListener {
    TextView textView;
    ConstraintLayout magnetometerLayout;
    SensorManager sensorManager;
    Sensor magnetometerSensor;
    double x,y,z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetometer);
        textView=findViewById(R.id.magnetometerSensorTxt);
        magnetometerLayout=findViewById(R.id.magnetometerView);

        textView.setText("Searching...");
        textView.setTextColor(getResources().getColor(R.color.white));
        magnetometerLayout.setBackgroundColor(getResources().getColor(R.color.grey));

        sensorManager=(SensorManager) getSystemService(Service.SENSOR_SERVICE);
        magnetometerSensor=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }
    @Override
    protected void onResume() {
        sensorManager.registerListener(this, magnetometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD) {
            if (sensorEvent.values.length > 0) {
                x=sensorEvent.values[0];
                y=sensorEvent.values[1];
                z=sensorEvent.values[2];

                float magnitudeValue= (float) Math.ceil(Math.sqrt((x * x)+(y * y)+(z * z)));
                if(magnitudeValue>200){
                    textView.setText("Magnetic Field Detected");
                    textView.setTextColor(getResources().getColor(R.color.black));
                    magnetometerLayout.setBackgroundColor(getResources().getColor(R.color.orange));
                }
                else{
                    textView.setText("Searching...");
                    textView.setTextColor(getResources().getColor(R.color.white));
                    magnetometerLayout.setBackgroundColor(getResources().getColor(R.color.grey));
                }

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}