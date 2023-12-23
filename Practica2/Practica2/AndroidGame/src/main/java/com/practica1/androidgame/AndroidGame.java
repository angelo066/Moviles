package com.practica1.androidgame;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.AdManager;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.SensorHandler;

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
    private Context context;

    private SensorHandler sensorHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DEBUG:
        //File file = new File("/data/user/0/com.practica1.androidgame/files/game.txt");
        //file.delete();

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
        sensorHandler = new SensorHandler(context);

        engine.setSensorHandler(sensorHandler);

        ResourceManager.getInstance().loadLevels();
        SceneManager.getInstance().addScene(new AssetsLoad());
        SceneManager.getInstance().goToNextScene();
        engine.resume();

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorHandler.onResume();
        SceneManager.getInstance().loadData();
        engine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorHandler.onPause();
        SceneManager.getInstance().saveData();
        engine.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorHandler.onDestroy();
        ResourceManager.Release();
        SceneManager.Release();
        engine.getAds().destroy();
    }

}