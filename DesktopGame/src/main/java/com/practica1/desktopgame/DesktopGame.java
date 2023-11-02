package com.practica1.desktopgame;

import com.practica1.desktopengine.EngineDesktop;
import com.practica1.engine.Scene;
import com.practica1.gamelogic.Final;
import com.practica1.gamelogic.MasterMind;
import com.practica1.gamelogic.MenuDificultad;
import com.practica1.gamelogic.MenuPrincipal;

import javax.swing.JFrame;

//Que devolver en el hanbdleInput
//¿Se puede volver a elegir un circulo sin rep

public class DesktopGame {
    public static void main(String[]args){
        JFrame frame = new JFrame("Mastermind");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.createBufferStrategy(2);


        EngineDesktop engine = new EngineDesktop(frame);

        //Scene scene = new MenuPrincipal();
        Scene scene = new MasterMind();
        //Scene scene = new Final(null,true);
        engine.setScene(scene);

        engine.resume();
    }


}