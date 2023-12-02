package com.example.temperaturesensor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private TextView temperatureTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatureTextView = findViewById(R.id.temperatureTextView);

        // Initialize SensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Check if temperature sensor is available
        if (sensorManager != null) {
            temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

            if (temperatureSensor == null) {
                temperatureTextView.setText("Temperature sensor not available");
            }
        }
        temperatureTextView.setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register the sensor listener
        if (temperatureSensor != null) {
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the sensor listener to save battery
        if (temperatureSensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Resources res = getResources();
        int hot = res.getColor(R.color.hot);
        int warm = res.getColor(R.color.warm);
        int cool = res.getColor(R.color.cool);
        int frigid = res.getColor(R.color.frigid);
        int white = res.getColor(R.color.white);
        int black = res.getColor(R.color.black);
        // Update the temperature value on the screen
        float temperatureValue = event.values[0];
        temperatureTextView.setText("Temperature: " + temperatureValue + " °C");
        // Controls background color of the temperatureTextView
        if(temperatureValue >= 95){
            temperatureTextView.setBackgroundColor(hot);
            temperatureTextView.setTextColor(black);
        } else if (temperatureValue >= 70 && temperatureValue < 95) {
            temperatureTextView.setBackgroundColor(warm);
            temperatureTextView.setTextColor(black);
        }else if (temperatureValue >= 35 && temperatureValue < 70) {
            temperatureTextView.setBackgroundColor(cool);
            temperatureTextView.setTextColor(white);
        }else {
            temperatureTextView.setBackgroundColor(frigid);
            temperatureTextView.setTextColor(white);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this example
    }
}
