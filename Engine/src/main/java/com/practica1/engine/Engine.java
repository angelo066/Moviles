package com.practica1.engine;

public interface Engine {
    Graphics getGraphics();

    Input getInput();

    Audio getAudio();

    void resume();

    void pause();

    void setScene(Scene scene);

}
