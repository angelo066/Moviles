package com.practica1.desktopengine;

import com.practica1.engine.Sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Clase que envuelve un sonido en la aplicacion en desktop
 */
public class SoundDesktop implements Sound {

    private Clip audioClip;

    /**
     * @param file Nombre del archivo
     */
    public SoundDesktop(String file) {
        try {
            File audio = new File(file);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audio);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioInputStream);
        } catch (Exception e) {
            System.err.println("Couldn't load audio file");
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        if (!audioClip.isRunning()) {
            audioClip.setFramePosition(0);
            audioClip.start();
        }
    }

    @Override
    public void stop() {
        if (audioClip.isRunning())
            audioClip.stop();
    }

    @Override
    public void loop(boolean loop) {
        if(loop)
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
        else
            audioClip.loop(0);
    }

    @Override
    public void release() {
        audioClip.close();
    }
}
