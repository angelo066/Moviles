package com.practica1.desktopengine;

import com.practica1.engine.Input;
import com.practica1.engine.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;

public class InputDesktop implements Input {

    private InputHandler handler;
    public InputDesktop(JFrame view) {
        handler = new InputHandler(view);
    }

    @Override
    public ArrayList<TouchEvent> getTouchEvents() {
        return handler.getTouchEvents();
    }

    public void clearEvents(){handler.clearEvents();}
}
