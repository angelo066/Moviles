package com.practica1.androidengine;

import android.view.SurfaceView;

import com.practica1.engine.Audio;
import com.practica1.engine.Engine;
import com.practica1.engine.Graphics;
import com.practica1.engine.Input;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;

import java.util.ArrayList;

/**
 * Clase motor de la aplicacion en android
 */
public class EngineAndroid implements Engine, Runnable {

    private volatile boolean running = false;
    private Thread thread;

    private SurfaceView view;

    private Scene scene;
    private Scene newScene;


    private GraphicsAndroid graphicsAndroid;
    private InputAndroid inputAndroid;
    private AudioAndroid audioAndroid;

    /**
     * @param view Ventana de la aplicacion
     */
    public EngineAndroid(SurfaceView view) {
        this.view = view;
        graphicsAndroid = new GraphicsAndroid(view);
        inputAndroid = new InputAndroid(view);
        audioAndroid = new AudioAndroid(view.getContext().getAssets());
        scene = null;
        newScene = null;
    }


    @Override
    public Graphics getGraphics() {
        return graphicsAndroid;
    }

    @Override
    public Input getInput() {
        return inputAndroid;
    }

    @Override
    public Audio getAudio() {
        return audioAndroid;
    }


    @Override
    public void run() {
        if (thread != Thread.currentThread())
            throw new RuntimeException("run() should not be called directly");            // Programación defensiva

        while (running && view.getWidth() == 0) ;

        long lastFrameTime = System.nanoTime();

        while (running) {

            //cambiar la escena
            changeScene();

            if (scene == null) break;

            //handleInput
            handleInput();

            //update
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            update(elapsedTime);

            // renderizado
            render();
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
    public void update(double deltaTime) {
        scene.update(deltaTime);
    }

    /**
     * Renderizado del motor
     */
    public void render() {
        graphicsAndroid.prepareRender();
        scene.render();
        graphicsAndroid.releaseRender();
    }

    /**
     * Manejo de input del motor
     */
    private void handleInput() {

        ArrayList<TouchEvent> inputEvents = inputAndroid.getTouchEvents();

        for (TouchEvent event : inputEvents) {
            event.x -= graphicsAndroid.getTranslateX();
            event.y -= graphicsAndroid.getTranslateY();
            event.x /= graphicsAndroid.getScaleX();
            event.y /= graphicsAndroid.getScaleY();

        }
        scene.handleInput(inputEvents);

    }
}
