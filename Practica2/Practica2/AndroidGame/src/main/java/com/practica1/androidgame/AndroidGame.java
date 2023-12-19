package com.practica1.androidgame;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.AdManager;
import com.practica1.androidengine.Engine;

public class AndroidGame extends AppCompatActivity implements SensorEventListener {
    private Engine engine;
    private Context context;

    private SensorManager sensorManager;
    private Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_android_game);
        SurfaceView renderView = findViewById(R.id.surfaceView);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        AdManager ads = new AdManager(this, findViewById(R.id.adView));

        engine = new Engine(renderView, ads);

        ResourceManager.Init(engine);
        SceneManager.Init(engine);
        GameManager.Init(engine);

        // Yo esto no se si va a aqui aviso soy un garrulo
        context = getApplicationContext();
        GameManager.getInstance().setContext(context);

        ResourceManager.getInstance().loadLevels();
        SceneManager.getInstance().addScene(new AssetsLoad());
        SceneManager.getInstance().goToNextScene();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //u otro sensor
        //registramos el listener
        sensorManager.registerListener( this, sensor , SensorManager.SENSOR_DELAY_NORMAL);


        engine.resume();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager. SENSOR_DELAY_NORMAL);
        engine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        engine.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
        ResourceManager.Release();
        SceneManager.Release();
        engine.getAds().destroy();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            //Quitamos el valor de la gravedad
            if(sensorEvent.values[0] + sensorEvent.values[2] > 5){
                System.out.println("Douh");

            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}