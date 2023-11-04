package com.practica1.desktopengine;

import com.practica1.engine.Audio;
import com.practica1.desktopengine.SoundDesktop;

import java.util.HashMap;

/**
 * Clase que se encarga de gestionar el audio de la apliacion en desktop
 */
public class AudioDesktop implements Audio {
    HashMap<String, SoundDesktop> soundsMap;

    private String soundsPath = "assets/sounds/";

    public AudioDesktop() {
        soundsMap = new HashMap<>();
    }

    @Override
    public void loadSound(String file, String id) {
        if (soundsMap.containsKey(id)) return;

        SoundDesktop newSound = new SoundDesktop(soundsPath + file);
        soundsMap.put(id, newSound);
    }

    @Override
    public void playSound(String id, boolean loop) {
        if (!soundsMap.containsKey(id)) return;

        SoundDesktop sound = soundsMap.get(id);
        if (loop) sound.loop();
        sound.play();
    }

    @Override
    public void stopSound(String id) {
        if (!soundsMap.containsKey(id)) return;
        soundsMap.get(id).stop();
    }

    @Override
    public void releaseSound(String id) {
        if (!soundsMap.containsKey(id)) return;
        soundsMap.get(id).release();
    }
}
