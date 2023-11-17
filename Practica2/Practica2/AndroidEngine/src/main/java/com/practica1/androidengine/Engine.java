package com.practica1.androidengine;

import android.view.SurfaceView;

/**
 * Clase motor de la aplicacion en android
 */
public class Engine implements Runnable {

    private boolean running = false;
    private Thread thread;

    private SurfaceView view;

    private Scene scene;

    private Graphics graphicsAndroid;
    private Input inputAndroid;
    private Audio audioAndroid;

    /**
     * @param view Ventana de la aplicacion
     */
    public Engine(SurfaceView view) {
        this.view = view;
        graphicsAndroid = new Graphics(view);
        inputAndroid = new Input(view);
        audioAndroid = new Audio(view);
    }



    public Graphics getGraphics() {
        return graphicsAndroid;
    }


    public Input getInput() {
        return inputAndroid;
    }


    public Audio getAudio() {
        return audioAndroid;
    }


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


    public void resume() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }


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
