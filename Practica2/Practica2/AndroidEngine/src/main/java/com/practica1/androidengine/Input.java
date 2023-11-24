package com.practica1.androidengine;

import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Clase que se encarga de gestionar el input de la apliacion en android
 */
public class Input {
    private InputHandler handler;

    /**
     * @param view Ventana de la aplicacion
     */
    public Input(SurfaceView view) {
        handler = new InputHandler(view);
    }

    public ArrayList<TouchEvent> getTouchEvents() {
        return handler.getTouchEvents();
    }
}
