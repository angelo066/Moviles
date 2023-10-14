package com.example.engine;

public interface Scene {
    public void init();

    public void update();

    public void render();

    public void handleInput();
}
