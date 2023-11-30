package com.practica1.androidgame;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.practica1.androidengine.Engine;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.practica1.androidengine.SceneManager;

public class AndroidGame extends AppCompatActivity {

    private SurfaceView renderView;
    private Engine engine;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderView = (SurfaceView) findViewById(R.id.surfaceView);
        adView = (AdView) findViewById(R.id.adView);
        setContentView(R.layout.activity_android_game);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        engine = new Engine(renderView);

        ResourceManager.Init(engine);
        SceneManager.Init(engine);

        SceneManager.getInstance().addScene(new MainMenu());
        SceneManager.getInstance().setSceneChange(true);

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
        adView.destroy();
    }
}