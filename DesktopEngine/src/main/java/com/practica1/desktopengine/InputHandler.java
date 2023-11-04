package com.practica1.desktopengine;

import com.practica1.engine.TouchEvent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * Clase que gestiona los eventos de input recibidos en la ventana en desktop
 */
public class InputHandler implements MouseListener, MouseMotionListener {

    private ArrayList<TouchEvent> events;

    private ArrayList<TouchEvent> pendingEvents;

    /**
     * @param view Ventana de la aplicacion
     */
    public InputHandler(JFrame view) {
        events = new ArrayList<>();
        pendingEvents = new ArrayList<>();
        view.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        TouchEvent event = new TouchEvent();
        event.x = mouseEvent.getX();
        event.y = mouseEvent.getY();
        event.type = TouchEvent.TouchEventType.CLICK;

        synchronized (this) {
            pendingEvents.add(event);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        TouchEvent event = new TouchEvent();
        event.x = mouseEvent.getX();
        event.y = mouseEvent.getY();
        event.type = TouchEvent.TouchEventType.TOUCH_DOWN;

        synchronized (this) {
            pendingEvents.add(event);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        TouchEvent event = new TouchEvent();
        event.x = mouseEvent.getX();
        event.y = mouseEvent.getY();
        event.type = TouchEvent.TouchEventType.TOUCH_UP;

        synchronized (this) {
            pendingEvents.add(event);
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        TouchEvent event = new TouchEvent();
        event.x = mouseEvent.getX();
        event.y = mouseEvent.getY();
        event.type = TouchEvent.TouchEventType.ON_RELEASE;

        synchronized (this) {
            pendingEvents.add(event);
        }
    }

    /**
     * Limpia los eventos
     */
    public void clearEvents() {
        events.clear();
    }

    /**
     * @return Devuelve los eventos ocurridos en el ultimo frame de la aplicacion
     */
    public synchronized ArrayList<TouchEvent> getTouchEvents() {
        events.addAll(pendingEvents);
        pendingEvents.clear();
        return events;
    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {


    }
}
