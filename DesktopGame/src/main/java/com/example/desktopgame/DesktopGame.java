package com.example.desktopgame;

import com.example.desktop_engine.EngineDesktop;

import com.example.desktop_engine.GraphicsDesktop;
import com.example.engine.Scene;
import com.example.mastermind.MasterMind;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

public class DesktopGame {
    public static void main(String[]args){
        JFrame frame = new JFrame("Mastermind");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.createBufferStrategy(2);


        EngineDesktop engine = new EngineDesktop(frame);

        Scene scene = new MasterMind();
        scene.init(engine);
        engine.setScene(scene);

        engine.resume();
    }


}