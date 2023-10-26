package com.practica1.androidengine;

import android.view.SurfaceView;

import com.practica1.engine.Input;
import com.practica1.engine.TouchEvent;

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

    public void clearEvents(){handler.clearEvents();}

}
