package com.practica1.engine;

import java.util.ArrayList;

public interface Scene {
    public void init(Engine engine);

    public void update(double deltaTime);

    public void render();

    public void handleInput(ArrayList<TouchEvent> events);
}
