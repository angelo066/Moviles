package com.example.androidengine;

import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine.Audio;
import com.example.engine.Engine;
import com.example.engine.Graphics;
import com.example.engine.Input;
import com.example.engine.Scene;

public class EngineAndroid implements Engine, Runnable {

    private boolean running = false;
    private Thread thread;

    private SurfaceView view;
    private SurfaceHolder holder;

    private Canvas canvas;

    private Scene scene;

    private GraphicsAndroid graphicsAndroid;
    private InputAndroid inputAndroid;

    public EngineAndroid(SurfaceView view) {
        this.view = view;
        holder = view.getHolder();
        graphicsAndroid = new GraphicsAndroid(view);
        inputAndroid = new InputAndroid(view);
        //audio
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
        return null;
    }

    @Override
    public void run() {
        if (thread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }

        // Si el Thread se pone en marcha
        // muy rápido, la vista podría todavía no estar inicializada.
        while (running && view.getWidth() == 0) ;

        if (scene == null) pause();

        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        // Bucle de juego principal.
        while (running) {

            //handleInput
            handleInput();

            //update
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;

            // Informe de FPS
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            update(elapsedTime);
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            graphicsAndroid.save();


            // renderizado
            while (!holder.getSurface().isValid()) ;
            canvas = this.holder.lockCanvas();

            float scaleX = (float) view.getWidth() / scene.getWidth();
            float scaleY = (float) view.getHeight() / scene.getHeight();
            float scale = Math.min(scaleX,scaleY);
            graphicsAndroid.scale(scale, scale);

            float translateX = (view.getWidth() - scene.getWidth() * scale) / 2f;
            float translateY = (view.getHeight() - scene.getHeight() * scale) / 2f;
            graphicsAndroid.translate(translateX, translateY);

            this.render();

            graphicsAndroid.restore();

            holder.unlockCanvasAndPost(canvas);
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
    }

    @Override
    public void update(double deltaTime) {
        scene.update(deltaTime);
    }

    @Override
    public void render() {
        graphicsAndroid.updateCanvas(canvas);
        scene.render();
        graphicsAndroid.updateCanvas(null);
    }

    @Override
    public void handleInput() {
        /*
         * for(TouchEvent event: input.getTouchEvents()){
         * event.x -= graphicsDesktop.translateX;
         * event.y -= graphicsDesktop.translateY;
         * event.x /= graphicsDesktop.scaleX;
         * event.y /= graphicsDesktop.scaleY;
         * }
         * scene.handleInput(input.getTouchEvents());
         */
    }
}
