package com.practica1.engine;

public interface Audio {

    void loadSound(String file, String id);

    void playSound(String id, boolean loop);

    void stopSound(String id);

    void releaseSound(String id);
}
