package com.practica1.desktopengine;

import com.practica1.engine.Audio;

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
    public void loadSound(String file) {
        if (soundsMap.containsKey(file)) return;

        SoundDesktop newSound = new SoundDesktop(soundsPath + file);
        soundsMap.put(file, newSound);
    }

    @Override
    public void playSound(String id, boolean loop) {
        if (!soundsMap.containsKey(id)) return;

        SoundDesktop sound = soundsMap.get(id);

        sound.play(loop);
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
        soundsMap.remove(id);
    }
}
