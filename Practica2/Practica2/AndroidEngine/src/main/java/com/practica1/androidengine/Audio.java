package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.media.SoundPool;
import android.view.SurfaceView;

import java.util.HashMap;

/**
 * Clase que se encarga de gestionar el audio de la apliacion en android
 */
public class Audio {

    HashMap<String, Sound> soundsMap;
    private String soundsPath = "sounds/";
    private AssetManager assetManager;

    private SoundPool soundPool;

    /**
     * @param assetMngr AssetManager
     */
    public Audio(AssetManager assetMngr) {
        assetManager = assetMngr;
        soundPool = new SoundPool.Builder().setMaxStreams(10).build();
        soundsMap = new HashMap<>();
    }


    public void loadSound(String file) {
        if (soundsMap.containsKey(file)) return;

        Sound newSound = new Sound(soundsPath + file, soundPool, assetManager);
        soundsMap.put(file, newSound);
    }


    public void playSound(String id, boolean loop) {
        if (!soundsMap.containsKey(id)) return;

        Sound sound = soundsMap.get(id);

        sound.play(loop);
    }


    public void stopSound(String id) {
        if (!soundsMap.containsKey(id)) return;
        soundsMap.get(id).stop();
    }


    public void releaseSound(String id) {
        if (!soundsMap.containsKey(id)) return;
        soundsMap.get(id).release();
        soundsMap.remove(id);
    }
}
