package com.practica1.androidgame;

import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.EngineAndroid;
import com.practica1.engine.Scene;
import com.practica1.gamelogic.MasterMind;

public class AndroidGame extends AppCompatActivity {

    private SurfaceView renderView;
    private EngineAndroid engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderView = new SurfaceView(this);
        setContentView(renderView);

        engine = new EngineAndroid(renderView);

        Scene scene = new MasterMind();
        scene.init(engine);
        engine.setScene(scene);

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
}