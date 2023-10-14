package com.example.engine;

public interface Engine extends Runnable {
    Graphics getGraphics();

    Input getInput();

    Audio getAudio();

    void run();

    void resume();

    void pause();

    void setScene(Scene scene);
}
