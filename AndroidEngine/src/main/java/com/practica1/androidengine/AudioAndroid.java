package com.practica1.androidengine;

import com.practica1.engine.Audio;
import com.practica1.engine.Sound;

import java.util.HashMap;

/**
 * Clase que se encarga de gestionar el audio de la apliacion en android
 */
public class AudioAndroid implements Audio {

    HashMap<String, Sound> soundsMap;
    private String soundsPath = "sounds/";

    @Override
    public void loadSound(String file, String id) {
        if (soundsMap.containsKey(id)) return;


    }

    @Override
    public void playSound(String file, boolean loop) {

    }

    @Override
    public void stopSound(String file) {

    }

    @Override
    public void releaseSound(String id) {

    }
}
