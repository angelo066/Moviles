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

    @Override
    public Graphics getGraphics() {
        return null;
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
        frame = new JFrame("Buffer Strategy Example");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        frame.createBufferStrategy(3);  // Creamos tres búferes

        GraphicsDesktop g = new GraphicsDesktop(frame);
        ColorDesktop c = new ColorDesktop();
        c.c = 0xFF00AA;


        BufferStrategy strategy = frame.getBufferStrategy();
        while (running) {
            //Prepare for rendering the next frame
            do {// Render single frame
                do {// Ensures that the contents of the drawing buffer are consistent
                    // Get a new graphics context every time through the loop to make sure
                    // the strategy is validated
                    Graphics2D graphics = (Graphics2D)strategy.getDrawGraphics();
                    // Render to graphics
                    //graphics.setColor(new java.awt.Color(0xA441B6));
                    //g.setColor(c);
                    g.clear(c.c);
                    //graphics.fillRect(0,0,100,100);
                    //g.fillRectangle(0,0,100,100);
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

    }
}