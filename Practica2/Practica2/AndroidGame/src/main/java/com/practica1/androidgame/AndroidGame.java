package com.practica1.androidgame;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.practica1.androidengine.Ads;
import com.practica1.androidengine.Engine;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.practica1.androidengine.SceneManager;

public class AndroidGame extends AppCompatActivity {

    private SurfaceView renderView;
    private Engine engine;
    private Ads ads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_android_game);
        renderView = (SurfaceView) findViewById(R.id.surfaceView);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        ads = new Ads(this, findViewById(R.id.adView));
        ads.loadBanner();

        engine = new Engine(renderView);

        ResourceManager.Init(engine);
        SceneManager.Init(engine);
        GameManager.Init(engine);

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
        ads.destroy();
    }
}