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
import android.widget.Toast;

public class LightSensor extends AppCompatActivity implements SensorEventListener {

    TextView textView;
    ConstraintLayout lightSensorLayout;
    SensorManager sensorManager;
    Sensor lightSensor;
    double sensorValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);

        textView=findViewById(R.id.lightSensorTxt);
        lightSensorLayout=findViewById(R.id.lightSensorView);

        sensorManager=(SensorManager) getSystemService(Service.SENSOR_SERVICE);
        lightSensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType()==Sensor.TYPE_LIGHT) {
            if (sensorEvent.values.length > 0) {
                sensorValues = sensorEvent.values[0];

                if(sensorValues<10.0){
                    textView.setText("Its too dark here...");
                    textView.setTextColor(getResources().getColor(R.color.black));
                    lightSensorLayout.setBackgroundColor(getResources().getColor(R.color.white));
                }else{
                    textView.setText("Its normal here...");
                    textView.setTextColor(getResources().getColor(R.color.white));
                    lightSensorLayout.setBackgroundColor(getResources().getColor(R.color.black));
                }
            } else {
                Toast.makeText(this, "LightSensorNotFound", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }
}