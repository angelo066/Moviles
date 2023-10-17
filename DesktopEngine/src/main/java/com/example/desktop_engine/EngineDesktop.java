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


public class EngineDesktop implements Engine {
    public JFrame frame;
    private boolean running = true;

    private Scene scene;
    private GraphicsDesktop graphicsDesktop;

    @Override
    public Graphics getGraphics() {
        return graphicsDesktop;
    }

    @Override
    public Input getInput() {
        return null;
    }

    @Override
    public Audio getAudio() {
        return null;
    }

    @Override
    public void run() {
        frame = new JFrame("Mastermind");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.createBufferStrategy(2);

        graphicsDesktop = new GraphicsDesktop(frame);

        BufferStrategy strategy = frame.getBufferStrategy();
        while (running) {
            //Prepare for rendering the next frame
            do {// Render single frame
                do {// Ensures that the contents of the drawing buffer are consistent
                    Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
                    // Get a new graphics context every time through the loop to make sure
                    // the strategy is validated
                    // Render to graphics
                    /*
                    * if(currentScene!=null){
                    * for(TouchEvent event: input.getTouchEvents()){
                    * event.x -= graphicsDesktop.translateX;
                    * event.y -= graphicsDesktop.translateY;
                    * event.x /= graphicsDesktop.scaleX;
                    * event.y /= graphicsDesktop.scaleY;
                    * }
                    * this.currentScene.handleInput();
                    * this.currentScene.update();
                    * this.render();
                    * }
                     */
                    graphicsDesktop.clear(0xFF00AA);
                    graphicsDesktop.setColor(0x000000);
                    graphicsDesktop.fillCircle(100, 100, 100);
                    graphics.dispose();
                } while (strategy.contentsRestored()); //Repeat if the buffer were restored
                // Display the buffer
                strategy.show();
            } while (strategy.contentsLost());//Repeat the rendering if the buffer was lost
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void setScene(Scene scene) {
        this.scene = scene;
    }
}