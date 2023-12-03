package com.practica1.androidengine;

import android.view.SurfaceView;

import com.practica1.engine.Input;
import com.practica1.engine.TouchEvent;

import java.util.ArrayList;

/**
 * Clase que se encarga de gestionar el input de la apliacion en android
 */
public class InputAndroid implements Input {
    private InputHandler handler;

    /**
     * @param view Ventana de la aplicacion
     */
    public InputAndroid(SurfaceView view) {
        handler = new InputHandler(view);
    }

    @Override
    public ArrayList<TouchEvent> getTouchEvents() {
        return handler.getTouchEvents();
    }

}
