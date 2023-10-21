package com.practica1.androidengine;

import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

import com.practica1.engine.TouchEvent;

import java.util.ArrayList;

public class InputHandler implements View.OnTouchListener {

    private ArrayList<TouchEvent> events;

    private ArrayList<TouchEvent> pendingEvents;

    public InputHandler(SurfaceView view){
        events = new ArrayList<>();
        pendingEvents = new ArrayList<>();
        view.setOnTouchListener(this);
    }
    public synchronized ArrayList<TouchEvent> getTouchEvents() {
        events.addAll(pendingEvents);
        pendingEvents.clear();
        return events;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TouchEvent event = new TouchEvent(); //pool de eventos por hacer
        int index = motionEvent.getActionIndex();
        int action = motionEvent.getActionMasked();
        int finger = motionEvent.getPointerId(index);

        event.x = (int) motionEvent.getX(finger);
        event.y = (int) motionEvent.getY(finger);

        if(action == MotionEvent.ACTION_DOWN)
            event.type = TouchEvent.TouchEventType.TOUCH_DOWN;
        else if(action == MotionEvent.ACTION_UP)
            event.type = TouchEvent.TouchEventType.TOUCH_UP;
        else if(action == MotionEvent.ACTION_BUTTON_PRESS)
            event.type = TouchEvent.TouchEventType.CLICK;
        else if(action == MotionEvent.ACTION_MOVE)
            event.type = TouchEvent.TouchEventType.TOUCH_DRAG;

        synchronized (this) {
            pendingEvents.add(event);
        }

        return true;
    }
}
