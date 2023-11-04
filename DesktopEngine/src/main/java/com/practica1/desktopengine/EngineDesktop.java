package com.practica1.desktopengine;

import com.practica1.engine.Audio;
import com.practica1.engine.Engine;
import com.practica1.engine.Graphics;
import com.practica1.engine.Input;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;

import javax.swing.JFrame;

import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;

/**
 * Clase motor de la aplicacion en desktop
 */
public class EngineDesktop implements Engine, Runnable {
    private boolean running = false;
    private Thread thread;

    public JFrame frame;

    private Scene scene;

    private GraphicsDesktop graphicsDesktop;
    private InputDesktop inputDesktop;
    private AudioDesktop audioDesktop;

    /**
     * @param view Ventana de la aplicacion
     */
    public EngineDesktop(JFrame view) {
        frame = view;
        graphicsDesktop = new GraphicsDesktop(frame);
        inputDesktop = new InputDesktop(frame);
        audioDesktop = new AudioDesktop();
    }


    @Override
    public Graphics getGraphics() {
        return graphicsDesktop;
    }

    @Override
    public Input getInput() {
        return inputDesktop;
    }

    @Override
    public Audio getAudio() {
        return audioDesktop;
    }


    @Override
    public void run() {
        if (thread != Thread.currentThread())
            throw new RuntimeException("run() should not be called directly"); //Programación defensiva


        // Si el Thread se pone en marcha muy rápido, la vista podría todavía no estar inicializada.
        while (running && frame.getWidth() == 0) ;

        if (scene == null) pause();

        long lastFrameTime = System.nanoTime();

        while (running) {
            //HandleInput
            handleInput();

            //Update
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            update(elapsedTime);


            //Renderizado
            BufferStrategy bufferStrategy = frame.getBufferStrategy();

            do {
                do {
                    render();
                } while (bufferStrategy.contentsRestored());
                bufferStrategy.show();
            } while (bufferStrategy.contentsLost());

        }

    }

    @Override
    public void resume() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void pause() {
        if (running) {
            running = false;
            while (true) {
                try {
                    thread.join();
                    thread = null;
                    break;
                } catch (InterruptedException ie) {
                    //Esto no debería ocurrir nunca
                }
            }
        }
    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
        this.scene.init(this);
    }

    /**
     * Update del motor
     *
     * @param deltaTime
     */
    private void update(double deltaTime) {
        scene.update(deltaTime);
    }

    /**
     * Renderizado del motor
     */
    private void render() {
        graphicsDesktop.prepareRender();
        scene.render();
        graphicsDesktop.releaseRender();
    }

    /**
     * Manejo de input del motor
     */
    private void handleInput() {

        for (TouchEvent event : inputDesktop.getTouchEvents()) {
            event.x -= graphicsDesktop.getTranslateX();
            event.y -= graphicsDesktop.getTranslateY();
            event.x /= graphicsDesktop.getScaleX();
            event.y /= graphicsDesktop.getScaleY();

        }
        scene.handleInput(inputDesktop.getTouchEvents());

        inputDesktop.clearEvents();
    }
}