package com.example.interfaces;

public interface Audio {
    Sound playSound(String file, boolean loop);

    void stopSound(String file);
}
