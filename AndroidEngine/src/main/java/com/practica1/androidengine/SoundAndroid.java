package com.practica1.androidengine;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.SoundPool;

import com.practica1.engine.Sound;

import java.io.IOException;

/**
 * Clase que envuelve un sonido en la aplicacion en android
 */
public class SoundAndroid implements Sound {

    private SoundPool soundPool;
    private int soundId, streamId;

    /**
     *
     * @param file Nombre del archivo
     * @param sPool Pool de sonidos de android
     * @param assetManager AssetsManager de android
     */
    public SoundAndroid(String file, SoundPool sPool, AssetManager assetManager) {
        soundPool = sPool;
        soundId = -1;
        streamId = -1;

        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(file);
            soundId = soundPool.load(assetFileDescriptor, 1);
            assetFileDescriptor.close();
        } catch (IOException e) {
            System.err.println("Couldn't load sound");
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        streamId = soundPool.play(soundId, 1, 1, 1, 0, 1);
    }

    @Override
    public void stop() {
        soundPool.stop(streamId);
    }

    @Override
    public void loop(boolean loop) {
        if (loop)
            soundPool.setLoop(streamId, -1);
        else
            soundPool.setLoop(streamId, 0);
    }

    @Override
    public void release() {
        soundPool.unload(soundId);
    }
}
