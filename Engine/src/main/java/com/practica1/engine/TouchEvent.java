package com.practica1.engine;

/**
 * Clase que envuelve un evento de input de la aplicacion
 */
public class TouchEvent {
    /**
     * Tipos de evento
     */
    public enum TouchEventType {
        TOUCH_DOWN,
        TOUCH_UP,
        TOUCH_DRAG
    }

    public TouchEventType type;

    public int x;

    public int y;
}
