package com.example.desktopgame;

import com.example.desktop_engine.EngineDesktop;

import com.example.desktop_engine.GraphicsDesktop;

import javax.swing.JFrame;

public class DesktopGame {
    public static void main(String[]args){
        JFrame frame = new JFrame("Mastermind");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.createBufferStrategy(2);

        EngineDesktop engine = new EngineDesktop(frame);

        //Crear escena
        //inicializar escena? -> scene.init();
        //asignar escena -> engine.setScene(...);

        engine.resume();
    }


}