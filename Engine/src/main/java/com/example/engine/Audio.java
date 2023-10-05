package com.example.engine;

public interface Audio {
    Sound playSound(String file, boolean loop);

    void stopSound(String file);
}
