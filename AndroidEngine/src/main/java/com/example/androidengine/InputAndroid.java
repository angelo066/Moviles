package com.example.androidengine;

import android.view.SurfaceView;

import com.example.engine.Input;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class InputAndroid implements Input {
    private InputHandler handler;

    public InputAndroid(SurfaceView view) {
        handler = new InputHandler(view);
    }

    @Override
    public ArrayList<TouchEvent> getTouchEvents() {
        return handler.getTouchEvents();
    }

}
