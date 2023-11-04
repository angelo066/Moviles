package com.practica1.engine;

/**
 * Clase que envuelve un evento de input de la aplicacion
 */
public class TouchEvent {
    /**
     * Tipos de evento
     */
    public static enum TouchEventType {
        TOUCH_DOWN,
        TOUCH_UP,
        CLICK,
        TOUCH_DRAG,
        ON_HOVER,
        ON_RELEASE
    }

    public TouchEventType type;

    public int x;

    public int y;
}
