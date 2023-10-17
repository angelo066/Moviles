package com.example.engine;

import java.util.ArrayList;

public interface Scene {
    public void init();

    public void update();

    public void render();

    public void handleInput(ArrayList<TouchEvent> events);
}
