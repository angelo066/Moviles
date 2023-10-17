package com.example.desktop_engine;

import com.example.engine.Audio;
import com.example.engine.Engine;
import com.example.engine.Graphics;
import com.example.engine.Input;
import com.example.engine.Scene;

import javax.swing.JFrame;

import java.awt.Canvas;
import java.awt.image.BufferStrategy;
import java.awt.Graphics2D;


public class EngineDesktop implements Engine, Runnable {
    private boolean running = false;
    private Thread thread;


    public JFrame frame;

    private BufferStrategy bufferStrategy;


    private Scene scene;
    private GraphicsDesktop graphicsDesktop;
    private InputDesktop inputDesktop;

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
        return null;
    }

    public EngineDesktop(JFrame view) {
        frame = view;
        graphicsDesktop = new GraphicsDesktop(frame);
        inputDesktop = new InputDesktop(frame);
        //audio
        bufferStrategy = frame.getBufferStrategy();
    }

    @Override
    public void run() {

        while (running) {

            if (thread != Thread.currentThread()) {
                // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
                // Programación defensiva
                throw new RuntimeException("run() should not be called directly");
            }

            // Si el Thread se pone en marcha
            // muy rápido, la vista podría todavía no estar inicializada.
            while (running && frame.getWidth() == 0) ;

            //comprobar que la escena no sea nula
            //if (scene != null) {

            //HandleInput
            handleInput();


            //Update
            long lastFrameTime = System.nanoTime();

            long informePrevio = lastFrameTime; // Informes de FPS
            int frames = 0;
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


            //Renderizado
            do {
                do {
                    Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                    render();
                    graphics.dispose();
                } while (bufferStrategy.contentsRestored());
                bufferStrategy.show();
            } while (bufferStrategy.contentsLost());
        }
        //}
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
        //scene.update(deltaTime);
    }

    @Override
    public void render() {
        //scene.render();

        //esto de momento para probar
        graphicsDesktop.clear(0xFFFFAA);
        graphicsDesktop.setColor(0x010ad00);
        graphicsDesktop.fillCircle(100, 100, 100);
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