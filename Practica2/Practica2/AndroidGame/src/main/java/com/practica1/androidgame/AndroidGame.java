package com.practica1.androidgame;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.AdManager;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.NotificationHandler;
import com.practica1.androidengine.NotificationWorker;
import com.practica1.androidengine.PushNotification;
import com.practica1.androidengine.SensorHandler;
import com.practica1.androidengine.ShareManager;

import java.io.File;
import java.util.concurrent.TimeUnit;

//TODO:
//  Securizar con NDK
//  Persistencia <--
//  Paletas de Colores
//  Compartir <--
//  Notificiaciones <--
//  Memoria
//  Estilo predeterminado en la tienda

public class AndroidGame extends AppCompatActivity {
    private Engine engine;

    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DEBUG:
        // File file = new
        // File("/data/user/0/com.practica1.androidgame/files/game.txt");
        // file.delete();

        //DEBUG:
        //File fileExist = new File("/data/user/0/com.practica1.androidgame/files/player.txt");
        //fileExist.delete();

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

        GameManager.getInstance().setContext(this);
        GameManager.getInstance().loadPlayerData();

        ResourceManager.getInstance().loadLevels();
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
        engine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        engine.getSensorHandler().onPause();
        SceneManager.getInstance().saveData();
        GameManager.getInstance().savePlayerData();
        notificationHandler.sendPushNotification(R.mipmap.ic_launcher, "MasterMind", "Notificacion de prueba",
                "Parte extensible Parte extensible Parte extensible Parte extensible Parte extensible Parte extensible Parte extensible");
        notificationHandler.setPushNotificationWorker(10, TimeUnit.SECONDS,R.mipmap.ic_launcher, "MasterMind", "Notificacion de prueba",
                "Parte extensible Parte extensible Parte extensible Parte extensible Parte extensible Parte extensible Parte extensible");
        // engine.Release();
        engine.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        engine.getSensorHandler().onDestroy();
        ResourceManager.Release();
        SceneManager.Release();
        engine.getAds().destroy();
        engine.Release();
    }
}