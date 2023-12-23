package com.practica1.androidgame;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.AdManager;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.SensorHandler;
import com.practica1.androidengine.ShareManager;

import java.io.File;

//TODO:
//  Securizar con NDK
//  Persistencia <--
//  Paletas de Colores
//  Compartir
//  Notificiaciones
//  Memoria
//  Estilo predeterminado en la tienda


public class AndroidGame extends AppCompatActivity {
    private Engine engine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DEBUG:
        //File file = new File("/data/user/0/com.practica1.androidgame/files/game.txt");
        //file.delete();

        setContentView(R.layout.activity_android_game);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        AdManager ads = new AdManager(this, findViewById(R.id.adView));
        SensorHandler sensorHandler = new SensorHandler(this);
        ShareManager shareManager = new ShareManager(surfaceView,this);

        engine = new Engine(surfaceView);
        engine.setAds(ads);
        engine.setSensorHandler(sensorHandler);
        engine.setShareManager(shareManager);


        ResourceManager.Init(engine);
        SceneManager.Init(engine);
        GameManager.Init(engine);

        GameManager.getInstance().setContext(this);

        ResourceManager.getInstance().loadLevels();
        SceneManager.getInstance().addScene(new AssetsLoad());
        SceneManager.getInstance().goToNextScene();

        engine.resume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        engine.getSensorHandler().onResume();
        SceneManager.getInstance().loadData();
        engine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        engine.getSensorHandler().onPause();
        SceneManager.getInstance().saveData();
        //engine.Release();
        engine.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        engine.getSensorHandler().onDestroy();
        ResourceManager.Release();
        SceneManager.Release();
        engine.getAds().destroy();
        //engine.Release();
    }

}