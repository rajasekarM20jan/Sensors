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
    double sensorValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnetometer);
        textView=findViewById(R.id.magnetometerSensorTxt);
        magnetometerLayout=findViewById(R.id.magnetometerView);


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
                textView.setText(""+sensorEvent.values[0]+"\n"+sensorEvent.values[1]+"\n"+sensorEvent.values[2]);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}