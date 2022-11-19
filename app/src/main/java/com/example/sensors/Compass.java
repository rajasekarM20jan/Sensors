package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Compass extends AppCompatActivity implements SensorEventListener {

    ImageView needle;
    TextView txt;

    SensorManager sensorManager;
    Sensor accelerometer,magnetometer;
    float[] lastAccelerometerValue=new float[3];
    float[] lastMagnetometerValue=new float[3];
    float[] rotationMatrix=new float[9];
    float[] orientation=new float[3];

    long lastUpdatedTime=0;
    float currentDegree=0f;

    boolean accelerometerValueUpdated=false;
    boolean magnetometerValueUpdated=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        needle=findViewById(R.id.needle);
        txt=findViewById(R.id.CompassTxt);

        sensorManager= (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        accelerometer=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this,accelerometer);
        sensorManager.unregisterListener(this,magnetometer);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,magnetometer,SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            if (sensorEvent.values.length > 0) {
                System.arraycopy(sensorEvent.values,0,lastAccelerometerValue,0,sensorEvent.values.length);
                accelerometerValueUpdated=true;
            }
        }else if(sensorEvent.sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            if (sensorEvent.values.length > 0) {
                System.arraycopy(sensorEvent.values,0,lastMagnetometerValue,0,sensorEvent.values.length);
                magnetometerValueUpdated=true;
            }
        }

        if(accelerometerValueUpdated && magnetometerValueUpdated && System.currentTimeMillis()-lastUpdatedTime>20){
            SensorManager.getRotationMatrix(rotationMatrix,null,lastAccelerometerValue,lastMagnetometerValue);
            SensorManager.getOrientation(rotationMatrix,orientation);

            float azimuthRadians= orientation[0];
            float azimuthInDegrees= (float) Math.toDegrees(azimuthRadians);

            RotateAnimation rotateAnimation= new RotateAnimation(currentDegree,-azimuthInDegrees,
                    RotateAnimation.RELATIVE_TO_SELF,0.5f,
                    RotateAnimation.RELATIVE_TO_SELF,0.5f);

            rotateAnimation.setDuration(20);
            rotateAnimation.setFillAfter(true);

            needle.startAnimation(rotateAnimation);

            currentDegree=-azimuthInDegrees;
            lastUpdatedTime=System.currentTimeMillis();
            txt.setText(((int)-azimuthInDegrees)+" °");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}