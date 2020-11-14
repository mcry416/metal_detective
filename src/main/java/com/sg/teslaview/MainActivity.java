package com.sg.teslaview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayst.dashboardview.DashboardView;
import com.ihat.pihat.circleprogress.CircleProgress;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private TextView textViewX;
    private TextView textViewY;
    private TextView textViewZ;
    private ProgressBar progressBarX;
    private ProgressBar progressBarY;
    private ProgressBar progressBarZ;
    private SoundPool soundPool;

    // Third widget to show a progress information.
    private CircleProgress circleProgressX;
    private CircleProgress circleProgressY;
    private CircleProgress circleProgressZ;

    private void initView(){
        textViewX = (TextView) findViewById(R.id.text_view_x);
        textViewY = (TextView) findViewById(R.id.text_view_y);
        textViewZ = (TextView) findViewById(R.id.text_view_z);
        progressBarX = (ProgressBar) findViewById(R.id.progress_bar_x);
        progressBarY = (ProgressBar) findViewById(R.id.progress_bar_y);
        progressBarZ = (ProgressBar) findViewById(R.id.progress_bar_z);
        circleProgressX = (CircleProgress) findViewById(R.id.circle_progress_x);
        circleProgressY = (CircleProgress) findViewById(R.id.circle_progress_y);
        circleProgressZ = (CircleProgress) findViewById(R.id.circle_progress_z);

    }

    // Init the Sensor and SensorManager, notice the parameter of (Sensor.TYPE_MAGNETIC_FIELD),
    // we get magnetic sensor to use.
    private void initSensor(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initSensor();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Register SensorManager listener.
        sensorManager.registerListener(MainActivity.this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();
        // Unregister SensorManager listener when callback onPause().
        sensorManager.unregisterListener(this);
    }

    /**
     * Notice event is an array to store float values.
     * @param event event[0] for x axis, event[1] for y axis, event[2] for z axis.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        textViewX.setText(String.valueOf(event.values[0]));
        progressBarX.setProgress(0 - (int) event.values[0]);
        circleProgressX.setValueText(String.valueOf(event.values[0]));
        circleProgressX.setSweepValue(-event.values[0]);

        textViewY.setText(String.valueOf(event.values[1]));
        progressBarY.setProgress((int) event.values[1]);
        circleProgressY.setValueText(String.valueOf(event.values[1]));
        circleProgressY.setSweepValue(event.values[1]);

        textViewZ.setText(String.valueOf(event.values[2]));
        progressBarZ.setProgress(0 - (int) event.values[2]);
        circleProgressZ.setValueText(String.valueOf(event.values[2]));
        circleProgressZ.setSweepValue(event.values[2]);

        if((0 - event.values[0]) > 50){
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone rt = RingtoneManager.getRingtone(getApplicationContext(), uri);
            rt.play();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
