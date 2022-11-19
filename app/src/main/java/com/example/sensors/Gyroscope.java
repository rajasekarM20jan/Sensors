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

public class Gyroscope extends AppCompatActivity implements SensorEventListener {

    TextView textView;
    ConstraintLayout gyroscopeLayout;
    SensorManager sensorManager;
    Sensor gyroscope;

    double x,y,z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);
        textView =findViewById(R.id.gyroscopeSensorTxt);
        gyroscopeLayout=findViewById(R.id.gyroscopeLayout);

        gyroscopeLayout.setBackgroundColor(getResources().getColor(R.color.black));
        sensorManager= (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        gyroscope= sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_GYROSCOPE){
            if(sensorEvent.values.length>0){
                x=sensorEvent.values[0];
                y=sensorEvent.values[1];
                z=sensorEvent.values[2];


                if(x>=1||x<=-1){
                    gyroscopeLayout.setBackgroundColor(getResources().getColor(R.color.grey));
                    textView.setText("X-Axis");
                }
                else if(y>=1||y<=-1){
                    gyroscopeLayout.setBackgroundColor(getResources().getColor(R.color.blue));
                    textView.setText("Y-Axis");
                }
                else{
                    gyroscopeLayout.setBackgroundColor(getResources().getColor(R.color.black));
                    textView.setText("");
                }

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}