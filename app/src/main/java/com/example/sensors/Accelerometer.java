package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;

public class Accelerometer extends AppCompatActivity implements SensorEventListener {
    TextView textView;
    ConstraintLayout accelerometerLayout;
    SensorManager sensorManager;
    Sensor accelerometerSensor;
    Boolean notFirstTime;
    CameraManager cam;
    Boolean flash;
    Vibrator vibrator;
    String myCam;
    double xSensorValue,ySensorValue,zSensorValue
            ,previousX,previousY,previousZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        textView=findViewById(R.id.accelerometerSensorTxt);
        accelerometerLayout=findViewById(R.id.accelerometerView);

        flash=false;
        textView.setTextColor(getResources().getColor(R.color.white));
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        notFirstTime=false;
        cam=(CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            myCam=cam.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        sensorManager=(SensorManager) getSystemService(Service.SENSOR_SERVICE);
        accelerometerSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
    @Override
    protected void onResume() {
        sensorManager.registerListener(this, accelerometerSensor,
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
        float x,y,z;
        float alpha=0.9f;
        if(sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            if (sensorEvent.values.length > 0) {
                xSensorValue=sensorEvent.values[0];
                ySensorValue=sensorEvent.values[1];
                zSensorValue=sensorEvent.values[2];

                x=(1-alpha)*sensorEvent.values[0];
                y=(1-alpha)*sensorEvent.values[1];
                z=(1-alpha)*sensorEvent.values[2];

                double data=Math.ceil(Math.sqrt((x*x)+(y*y)+(z*z)));
                textView.setText(""+data);
                if(notFirstTime){
                    if(((xSensorValue)>20&& Math.abs(xSensorValue-previousX)>20)){

                        if(flash){

                            flash=false;
                            setFlashLight(flash);

                            textView.setTextColor(getResources().getColor(R.color.white));
                        }
                        else{
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                vibrator.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
                            }
                            flash=true;
                            setFlashLight(flash);

                            textView.setTextColor(getResources().getColor(R.color.orange));
                        }


                    }
                }
                previousX=xSensorValue;
                previousY=ySensorValue;
                previousZ=zSensorValue;
                notFirstTime=true;
            }
        }

    }

    private void setFlashLight(boolean b)  {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                 cam.setTorchMode(myCam,b);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}