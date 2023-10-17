package com.example.desktop_engine;

import com.example.engine.Input;
import com.example.engine.TouchEvent;

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

}
