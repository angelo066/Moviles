package com.practica1.androidgame;

import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.practica1.androidengine.Engine;
import com.practica1.gamelogic.MainMenu;
import com.practica1.androidengine.Scene;

public class AndroidGame extends AppCompatActivity {

    private SurfaceView renderView;
    private Engine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        renderView = new SurfaceView(this);
        setContentView(renderView);

        engine = new Engine(renderView);

        Scene scene = new MainMenu();
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