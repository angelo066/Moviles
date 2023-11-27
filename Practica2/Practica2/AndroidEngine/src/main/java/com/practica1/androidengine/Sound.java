package com.practica1.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

import java.io.IOException;

/**
 * Clase que envuelve un sonido en la aplicacion en android
 */
public class Sound {

    private SoundPool soundPool;
    private int soundId, streamId;

    /**
     * @param file         Nombre del archivo
     * @param sPool        Pool de sonidos de android
     * @param assetManager AssetsManager de android
     */
    public Sound(String file, SoundPool sPool, AssetManager assetManager) {
        soundPool = sPool;
        soundId = -1;
        streamId = -1;

        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(file);
            soundId = soundPool.load(assetFileDescriptor, 1);
            assetFileDescriptor.close();
        } catch (IOException e) {
            System.err.println("ERROR AL CARGAR AUDIO EN ANDROID");
            e.printStackTrace();
        }
    }

    /**
     * Reproduce el sonido
     *
     * @param loop Establece el loop
     */
    public void play(boolean loop) {
        if (loop)
            streamId = soundPool.play(soundId, 1, 1, 1, -1, 1);
        else
            streamId = soundPool.play(soundId, 1, 1, 1, 0, 1);
    }

    /**
     * Para el sonido
     */
    public void stop() {
        soundPool.stop(streamId);
    }

    /**
     * Establece el loop del sonido a True/False
     *
     * @param loop
     */
    public void loop(boolean loop) {
        if (loop)
            soundPool.setLoop(streamId, -1);
        else
            soundPool.setLoop(streamId, 0);
    }

    /**
     * Libera los recursos del sonido
     */
    public void release() {
        soundPool.unload(soundId);
    }
}
