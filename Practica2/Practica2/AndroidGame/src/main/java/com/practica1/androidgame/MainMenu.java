package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;
import com.practica1.androidengine.Vector2;

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
    public void init(Engine engine) {
        this.engine = engine;

        width = 1080;
        height = 1920;
        engine.getGraphics().setSceneSize(width, height);

        // Creacion de los objetos de la escena
        Font fontTitle = engine.getGraphics().newFont("BarlowCondensed-Regular.ttf", 200, true, true);
        textTitle = new TextObject(engine, width, height, new Vector2(width / 2, 380), fontTitle, "Master Mind", Color.BLACK);
        textTitle.center();

        Font fontButton = engine.getGraphics().newFont("Nexa.ttf", 80, false, false);
        buttonPlay = new ButtonObject(engine, width, height, new Vector2(width / 2, height / 2), new Vector2(500, 150), 40, fontButton, "Jugar", Color.CYAN, Color.BLACK);
        buttonPlay.centrar();

        engine.getAudio().loadSound("botonInterfaz.wav", "start");
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        // Fondo de la APP
        engine.getGraphics().clear(Color.WHITE);

        // Fondo del juego
        engine.getGraphics().setColor(Color.WHITE);
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
                engine.getAudio().stopSound("start");
                engine.getAudio().playSound("start", false);
                engine.setScene(new SelectionMenu());
                break;
            }
        }
    }
}
