package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.androidengine.EngineAndroid;
import com.example.engine.Scene;
import com.example.mastermind.MasterMind;

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