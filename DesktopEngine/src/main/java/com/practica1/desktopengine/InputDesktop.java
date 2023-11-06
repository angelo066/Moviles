package com.practica1.desktopengine;

import com.practica1.engine.Input;
import com.practica1.engine.TouchEvent;

import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * Clase que se encarga de gestionar el input de la apliacion en desktop
 */
public class InputDesktop implements Input {

    private InputHandler handler;

    /**
     * @param view Ventana de la aplicacion
     */
    public InputDesktop(JFrame view) {
        handler = new InputHandler(view);
    }

    @Override
    public ArrayList<TouchEvent> getTouchEvents() {
        return handler.getTouchEvents();
    }

    /**
     * Limpia los eventos del frame pasado
     */
    public void clearEvents() {
        handler.clearEvents();
    }
}
