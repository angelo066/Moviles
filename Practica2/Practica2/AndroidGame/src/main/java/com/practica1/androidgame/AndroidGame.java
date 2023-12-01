package com.practica1.androidgame;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.AdManager;
import com.practica1.androidengine.Engine;

public class AndroidGame extends AppCompatActivity {
    private Engine engine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_android_game);
        SurfaceView renderView = (SurfaceView) findViewById(R.id.surfaceView);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        AdManager ads = new AdManager(this, findViewById(R.id.adView));

        engine = new Engine(renderView, ads);

        ResourceManager.Init(engine);
        SceneManager.Init(engine);
        GameManager.Init(engine);

        SceneManager.getInstance().addScene(new MainMenu());
        SceneManager.getInstance().goToNextScene();

        engine.resume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        engine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        engine.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ResourceManager.Release();
        SceneManager.Release();
        engine.getAds().destroy();
    }
}