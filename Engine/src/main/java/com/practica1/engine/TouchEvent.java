package com.practica1.engine;

public class TouchEvent {
    public static enum TouchEventType{
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
