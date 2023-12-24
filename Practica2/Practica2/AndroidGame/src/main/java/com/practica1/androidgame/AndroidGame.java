package com.practica1.androidgame;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.AdManager;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.NotificationHandler;
import com.practica1.androidengine.SensorHandler;
import com.practica1.androidengine.ShareManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AndroidGame extends AppCompatActivity {
    private Engine engine;

    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        File file = new File("/data/user/0/com.practica1.androidgame/files/player.txt");
        if(file.exists()){
            boolean deleted = file.delete();
        }*/

        setContentView(R.layout.activity_android_game);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        AdManager ads = new AdManager(this, findViewById(R.id.adView));
        SensorHandler sensorHandler = new SensorHandler(this);
        ShareManager shareManager = new ShareManager(surfaceView, this);
        notificationHandler = new NotificationHandler(this);

        engine = new Engine(surfaceView);
        engine.setAds(ads);
        engine.setSensorHandler(sensorHandler);
        engine.setShareManager(shareManager);

        ResourceManager.Init(engine);
        GameManager.Init(engine);
        SceneManager.Init(engine);
        NDKManager.Init();

        GameManager.getInstance().setContext(this);

        ResourceManager.getInstance().loadLevels();
        GameManager.getInstance().loadPlayerData();
        SceneManager.getInstance().addScene(new AssetsLoad());
        SceneManager.getInstance().goToNextScene();

        engine.resume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        engine.getSensorHandler().onResume();
        GameManager.getInstance().loadPlayerData();
        SceneManager.getInstance().loadData();
        notificationHandler.stopPushNotificationWorker();
        engine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        engine.getSensorHandler().onPause();
        SceneManager.getInstance().saveData();
        GameManager.getInstance().savePlayerData();
        notificationHandler.setPushNotificationWorkerPeriodic(10, TimeUnit.SECONDS, 20, TimeUnit.MINUTES, R.mipmap.ic_launcher,
                "MasterMind", "¡Vuelve a jugar MasterMind!",
                "El ojo de la verdad espera tu llegada, entra y juega ahora, ¡te esperan muchos niveles!");
        engine.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GameManager.getInstance().savePlayerData();
        engine.getSensorHandler().onDestroy();
        ResourceManager.Release();
        SceneManager.Release();
        engine.getAds().destroy();
        engine.Release();
    }
}