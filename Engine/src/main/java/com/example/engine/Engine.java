package com.example.engine;

public interface Engine {
    Graphics getGraphics();

    Input getInput();

    Audio getAudio();

    void resume();

    void pause();

    void setScene(Scene scene);

    void update(double deltaTime);

    void render();

    void handleInput();
}
