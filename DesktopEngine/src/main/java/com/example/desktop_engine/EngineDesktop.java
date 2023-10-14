package com.example.desktop_engine;

import com.example.engine.Audio;
import com.example.engine.Engine;
import com.example.engine.Graphics;
import com.example.engine.Input;
import com.example.engine.Scene;

import javax.swing.JFrame;

import java.awt.Canvas;
import java.awt.image.BufferStrategy;

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
        //Canvas canvas = new Canvas();
        //frame.add(canvas);
        frame.setVisible(true);

        frame.createBufferStrategy(3);  // Creamos tres búferes

        GraphicsDesktop g = new GraphicsDesktop(frame);
        ColorDesktop c = new ColorDesktop();
        c.c = 0xFF00AA;

        while (running) {
            BufferStrategy bs = frame.getBufferStrategy();
            if (bs == null) {
                frame.createBufferStrategy(3);
                return;
            }
            g.setColor(c);
            g.fillRectangle(0,0,100,200);
            g.clear(0xFF0000);

            /*Graphics g = bs.getDrawGraphics();

            // Dibuja en el búfer
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.fillOval(50, 50, 50, 50);

            // Muestra el búfer en la pantalla
            g.dispose();*/
            bs.show();
            /*try {
                Thread.sleep(16);  // Ajusta el tiempo de espera para controlar la velocidad de la animación
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
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