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

public class ProximitySensor extends AppCompatActivity implements SensorEventListener {

    TextView textView;
    ConstraintLayout proximitySensorLayout;
    SensorManager sensorManager;
    Sensor proximitySensor;
    double sensorValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity_sensor);

        textView=findViewById(R.id.proximitySensorTxt);
        proximitySensorLayout=findViewById(R.id.proximitySensorView);

        sensorManager=(SensorManager) getSystemService(Service.SENSOR_SERVICE);
        proximitySensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }
    @Override
    protected void onResume() {
        sensorManager.registerListener(this, proximitySensor,
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
        if(sensorEvent.sensor.getType()==Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values.length > 0) {
                sensorValues = sensorEvent.values[0];

                if(sensorValues==0.0){
                    textView.setText("Sensor Activated");
                    textView.setTextColor(getResources().getColor(R.color.orange));
                    proximitySensorLayout.setBackgroundColor(getResources().getColor(R.color.black));
                }else{
                    textView.setText("Sensor Deactivated");
                    textView.setTextColor(getResources().getColor(R.color.black));
                    proximitySensorLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}