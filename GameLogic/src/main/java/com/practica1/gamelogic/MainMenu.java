package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

import java.util.ArrayList;

/**
 * Escena de inicio
 */
public class MainMenu implements Scene {

    private Engine engine;
    private int width;
    private int height;
    private ButtonObject buttonPlay;
    private TextObject textTitle;


    @Override
    public void init(Engine engine)
    {
        // Seteo de
        this.engine = engine;

        width = 1080;
        height = 1920;
        engine.getGraphics().setSceneSize(width,height);

        // Creacion de los objetos de la escena
        Font fontTitulo = engine.getGraphics().newFont("Nexa.ttf", 150, false, false);
        textTitle = new TextObject(engine,width,height, new Vector2(width/2,250), fontTitulo, "Master Mind", Color.BLACK);
        textTitle.center();

        Font fontBoton = engine.getGraphics().newFont("Nexa.ttf", 80, false, false);
        buttonPlay = new ButtonObject(engine,width,height, new Vector2(width/2,height/2), new Vector2(500,150), 70, fontBoton,"Jugar", Color.CYAN, Color.BLACK);
        buttonPlay.centrar();

        engine.getAudio().loadSound("botonInterfaz.wav", "start");
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render()
    {
        // Fondo de la APP
        engine.getGraphics().clear(Color.DARK_GREY);

        // Fondo del juego
        engine.getGraphics().setColor(Color.WHITE);
        engine.getGraphics().fillRectangle(0,0,width,height);

        // Objetos
        textTitle.render();
        buttonPlay.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for(int i = 0; i < events.size(); i++)
        {
            // Pasamos a la escena de seleccion
            if(buttonPlay.handleInput(events.get(i)))
            {
                engine.getAudio().playSound("start", false);
                engine.setScene(new SelectionMenu());
            }

        }
    }
}
