package com.practica1.desktopgame;

import com.practica1.desktopengine.EngineDesktop;
import com.practica1.engine.Scene;
import com.practica1.gamelogic.MainMenu;

import javax.swing.JFrame;

public class DesktopGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mastermind");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIgnoreRepaint(true);
        frame.setVisible(true);

        int intentos = 100;
        while (intentos-- > 0) {
            try {
                frame.createBufferStrategy(2);
                break;
            } catch (Exception e) {
            }
        }
        if (intentos == 0) {
            System.out.println("No se ha podido crear el buffer strategy");
            return;
        }

        EngineDesktop engine = new EngineDesktop(frame);

        Scene scene = new MainMenu();
        engine.setScene(scene);

        engine.resume();
    }


}