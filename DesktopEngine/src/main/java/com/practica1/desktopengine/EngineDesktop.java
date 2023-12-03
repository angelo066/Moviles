package com.practica1.desktopengine;

import com.practica1.engine.Audio;
import com.practica1.engine.Engine;
import com.practica1.engine.Graphics;
import com.practica1.engine.Input;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;

import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * Clase motor de la aplicacion en desktop
 */
public class EngineDesktop implements Engine, Runnable {
    private volatile boolean running = false;
    private Thread thread;

    public JFrame frame;

    private Scene scene;
    private Scene newScene;

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
        scene = null;
        newScene = null;
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

        long lastFrameTime = System.nanoTime();

        while (running) {

            //cambiar la escena
            changeScene();

            if (scene == null) break;

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

    /**
     * Reanuda la ejecucion de la aplicacion
     */
    public void resume() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Pausa la ejecucion de la aplicacion
     */
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
        if (this.scene == null)
            this.scene = scene;
        else
            newScene = scene;

        scene.init(this);
    }

    /**
     * Cambia, si es necesario, la escena al principio de frame
     */
    private void changeScene() {
        if (newScene != null) {
            scene = newScene;
            newScene = null;
        }
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

        ArrayList<TouchEvent> inputEvents = inputDesktop.getTouchEvents();

        for (TouchEvent event : inputEvents) {
            event.x -= graphicsDesktop.getTranslateX();
            event.y -= graphicsDesktop.getTranslateY();
            event.x /= graphicsDesktop.getScaleX();
            event.y /= graphicsDesktop.getScaleY();

        }
        scene.handleInput(inputEvents);
    }
}