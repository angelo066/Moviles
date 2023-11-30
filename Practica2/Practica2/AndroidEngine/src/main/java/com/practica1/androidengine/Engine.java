package com.practica1.androidengine;

import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Clase motor de la aplicacion en android
 */
public class Engine implements Runnable {

    private volatile boolean running = false;
    private Thread thread;
    private SurfaceView view;
    private Graphics graphics;
    private Input input;
    private Audio audio;
    private Scene scene;

    /**
     * @param view Ventana de la aplicacion
     */
    public Engine(SurfaceView view) {
        this.view = view;
        graphics = new Graphics(view);
        input = new Input(view);
        audio = new Audio(view.getContext().getAssets());
        scene = null;
    }

    /**
     * @return Objeto Graphics de la aplicacion
     */
    public Graphics getGraphics() {
        return graphics;
    }

    /**
     * @return Objeto Input de la aplicacion
     */
    public Input getInput() {
        return input;
    }

    /**
     * @return Objeto Audio de la aplicacion
     */
    public Audio getAudio() {
        return audio;
    }


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

    /**
     * Cambia, si es necesario, la escena al principio de frame
     */
    private void changeScene() {
        if (SceneManager.getInstance().doChangeScene()) {
            scene = SceneManager.getInstance().getScene();
            SceneManager.getInstance().setSceneChange(false);
            SceneManager.getInstance().removeScene();
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
        graphics.prepareRender();
        scene.render();
        graphics.releaseRender();
    }

    /**
     * Manejo de input del motor
     */
    private void handleInput() {

        ArrayList<TouchEvent> inputEvents = input.getTouchEvents();

        for (TouchEvent event : inputEvents) {
            event.x -= graphics.getTranslateX();
            event.y -= graphics.getTranslateY();
            event.x /= graphics.getScaleX();
            event.y /= graphics.getScaleY();
        }
        scene.handleInput(inputEvents);
    }
}
