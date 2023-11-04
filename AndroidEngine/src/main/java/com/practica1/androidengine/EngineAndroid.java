package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.practica1.engine.Audio;
import com.practica1.engine.Engine;
import com.practica1.engine.Graphics;
import com.practica1.engine.Input;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;

/**
 * Clase motor de la aplicacion en android
 */
public class EngineAndroid implements Engine, Runnable {

    private boolean running = false;
    private Thread thread;

    private SurfaceView view;

    private Scene scene;

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
        audioAndroid = new AudioAndroid();
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

        if (scene == null) pause();

        long lastFrameTime = System.nanoTime();

        while (running) {

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

        for (TouchEvent event : inputAndroid.getTouchEvents()) {
            event.x -= graphicsAndroid.getTranslateX();
            event.y -= graphicsAndroid.getTranslateY();
            event.x /= graphicsAndroid.getScaleX();
            event.y /= graphicsAndroid.getScaleY();
        }
        scene.handleInput(inputAndroid.getTouchEvents());

        inputAndroid.clearEvents();

    }
}
