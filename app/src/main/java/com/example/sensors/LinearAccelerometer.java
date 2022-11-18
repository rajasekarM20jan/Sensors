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

public class LinearAccelerometer extends AppCompatActivity implements SensorEventListener {
    TextView textView;
    ConstraintLayout accelerometerLayout;
    SensorManager sensorManager;
    Sensor linearAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_accelerometer);

        textView=findViewById(R.id.lAccelerometerSensorTxt);
        accelerometerLayout=findViewById(R.id.lAccelerometerView);


        sensorManager=(SensorManager) getSystemService(Service.SENSOR_SERVICE);
        linearAccelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);



    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Float valX, valY, valZ;
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            valX = sensorEvent.values[0];
            valY = sensorEvent.values[1];
            valZ = sensorEvent.values[2];
            double acceleration =Math.ceil (Math.sqrt((valX * valX) + (valY * valY) + (valZ * valZ)));
            textView.setText(""+acceleration);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onResume() {
        sensorManager.registerListener(this,linearAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }
}