package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.media.SoundPool;
import android.view.SurfaceView;

import com.practica1.engine.Audio;

import java.util.HashMap;

/**
 * Clase que se encarga de gestionar el audio de la apliacion en android
 */
public class AudioAndroid implements Audio {

    HashMap<String, SoundAndroid> soundsMap;
    private String soundsPath = "sounds/";
    private AssetManager assetManager;

    private SoundPool soundPool;

    /**
     * @param assetMngr AssetManager
     */
    public AudioAndroid(AssetManager assetMngr) {
        assetManager = assetMngr;
        soundPool = new SoundPool.Builder().setMaxStreams(10).build();
        soundsMap = new HashMap<>();
    }

    @Override
    public void loadSound(String file) {
        if (soundsMap.containsKey(file)) return;

        SoundAndroid newSound = new SoundAndroid(soundsPath + file, soundPool, assetManager);
        soundsMap.put(file, newSound);
    }

    @Override
    public void playSound(String id, boolean loop) {
        if (!soundsMap.containsKey(id)) return;

        SoundAndroid sound = soundsMap.get(id);

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
