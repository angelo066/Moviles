package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.Image;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;

/**
 * Escena de inicio
 */
public class MainMenu extends Scene {
    private ButtonObject buttonPlay;
    private TextObject textTitle;


    public MainMenu(){
        width = 1080;
        height = 1920;
    }
    @Override
    public void init(Engine engine) {
        super.init(engine);

        engine.getAudio().loadSound("botonInterfaz.wav");
        engine.getAudio().loadSound("clickboton.wav");
        engine.getAudio().loadSound("yuju.wav");
        engine.getAudio().loadSound("douh.wav");

        Font fontTitle = ResourceManager.getInstance().createFont("BarlowCondensed-Regular.ttf", 200, true, true);
        Font fontButton = ResourceManager.getInstance().createFont("Nexa.ttf", 80, false, false);

        ResourceManager.getInstance().createImage("volver.png");
        ResourceManager.getInstance().createImage("ojo.png");
        ResourceManager.getInstance().createImage("ojo2.png");


        // Creacion de los objetos de la escena
        textTitle = new TextObject(engine, width, height, new Vector2(width / 2, 380),
                fontTitle, "Master Mind", Color.BLACK,200,true,true);
        textTitle.center();

        buttonPlay = new ButtonObject(engine, width, height, new Vector2(width / 2, height / 2), new Vector2(500, 150), 40, Color.CYAN,
                new TextObject(engine,width,height,new Vector2(width / 2, height / 2),fontButton,"Jugar",Color.BLACK,80,false,false));
        buttonPlay.centrar();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        // Fondo de la APP
        engine.getGraphics().clear(Color.WHITE.getValue());

        // Fondo del juego
        engine.getGraphics().setColor(Color.WHITE.getValue());
        engine.getGraphics().fillRectangle(0, 0, width, height);

        // Objetos
        textTitle.render();
        buttonPlay.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            // Pasamos a la escena de seleccion
            if (buttonPlay.handleInput(events.get(i))) {
                engine.getAudio().stopSound("botonInterfaz.wav");
                engine.getAudio().playSound("botonInterfaz.wav", false);
                engine.setScene(new SelectionMenu());
                break;
            }
        }
    }
}
