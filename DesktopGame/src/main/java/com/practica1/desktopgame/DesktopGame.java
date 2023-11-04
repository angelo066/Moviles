package com.practica1.desktopgame;

import com.practica1.desktopengine.EngineDesktop;
import com.practica1.engine.Scene;
import com.practica1.gamelogic.Final;
import com.practica1.gamelogic.MasterMind;
import com.practica1.gamelogic.MenuDificultad;
import com.practica1.gamelogic.MenuPrincipal;

import javax.swing.JFrame;

//Temita de los botones : Clase boton. Devuelve si has pulsado dentro. Switch con botones de la escena
//Restore y save: en android no se apilan las transformaciones. Al principio y al final de cada frame

//FALTA:
//Pasar handleInput a GameObject
//Renderizar los intentos <--
//Enlazar las escenas
//Modo daltonico
//Sonido en android
//Sacar clase colores a otro sitio
//Revisar codigo y comentar
//Hacer un clear en las escenas antes de pintar?

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

        //Scene scene = new MenuPrincipal();
        Scene scene = new MasterMind();
        //Scene scene = new Final(null,true);
        engine.setScene(scene);

        engine.resume();
    }


}