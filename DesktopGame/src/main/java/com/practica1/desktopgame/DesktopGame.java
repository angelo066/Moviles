package com.practica1.desktopgame;

import com.practica1.desktopengine.EngineDesktop;
import com.practica1.engine.Scene;
import com.practica1.gamelogic.MasterMind;
import com.practica1.gamelogic.Menu;

import javax.swing.JFrame;

public class DesktopGame {
    public static void main(String[]args){
        JFrame frame = new JFrame("Mastermind");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.createBufferStrategy(2);


        EngineDesktop engine = new EngineDesktop(frame);

        Scene scene = new Menu();
        scene.init(engine);
        engine.setScene(scene);

        engine.resume();
    }


}