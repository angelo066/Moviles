package com.practica1.desktopengine;

import com.practica1.engine.Sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundDesktop implements Sound {

    private Clip audioClip;

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
    public void loop() {
        audioClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void release() {
        audioClip.close();
    }
}
