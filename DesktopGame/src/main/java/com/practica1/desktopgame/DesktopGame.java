package com.practica1.desktopgame;

import com.practica1.desktopengine.EngineDesktop;
import com.practica1.engine.Scene;
import com.practica1.gamelogic.Dificultad;
import com.practica1.gamelogic.MasterMind;
import com.practica1.gamelogic.MenuPrincipal;

import javax.swing.JFrame;

//Temita de los botones : Clase boton. Devuelve si has pulsado dentro. Switch con botones de la escena
//Restore y save: en android no se apilan las transformaciones. Al principio y al final de cada frame <-- en principio

//FALTA:
//Sonido en android <--
//Hacer un clear en las escenas antes de pintar

public class DesktopGame {
    public static void main(String[]args){
        JFrame frame = new JFrame("Mastermind");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIgnoreRepaint(true);
        frame.setVisible(true);

        int intentos = 100;
        while(intentos-- > 0){
            try{
                frame.createBufferStrategy(2);
                break;
            }
            catch (Exception e){}
        }
        if(intentos == 0){
            System.out.println("No se ha podido crear el buffer strategy");
            return;
        }

        EngineDesktop engine = new EngineDesktop(frame);

        Scene scene = new MenuPrincipal();
        engine.setScene(scene);

        engine.resume();
    }


}