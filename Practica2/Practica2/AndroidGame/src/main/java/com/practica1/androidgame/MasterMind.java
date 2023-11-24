package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;

/**
 * Escena de juego
 */
public class MasterMind implements Scene {

    private Engine engine;
    private Graphics graph;
    private int width;
    private int height;
    private TabObject tab;     // Instancia al tablero (lleva la logica de juego)
    private ButtonObject buttonColorBlind;
    private ButtonObject buttonColorBlindActive;
    private ButtonObject buttonBack;
    private TextObject textAttempts;
    private TextObject title;
    private Difficulty mode;
    private boolean colorBlind;

    public MasterMind(Difficulty mode) {
        this.mode = mode;
    }

    @Override
    public void init(Engine engine) {
        this.engine = engine;
        this.graph = engine.getGraphics();
        width = 1080;
        height = 1920;
        engine.getGraphics().setSceneSize(width, height);

        engine.getAudio().loadSound("clickboton.wav");

        // Creacion del tablero de juego
        tab = new TabObject(engine, width, height, mode);
        tab.init();

        // Botones
        buttonColorBlind = new ButtonObject(engine, width, height, new Vector2(width - 120, 20), new Vector2(100, 100), "ojo.png");
        buttonColorBlindActive = new ButtonObject(engine, width, height, new Vector2(width - 120, 20), new Vector2(100, 100), "ojo2.png");

        buttonBack = new ButtonObject(engine, width, height, new Vector2(20, 20), new Vector2(100, 100), "volver.png");

        // Texto de indicacion de intentos restantes
        Font font = graph.newFont("Nexa.ttf", 45, false, false);
        String text = "Te quedan " + tab.getRemainingAttempts() + " intentos";
        textAttempts = new TextObject(engine, width, height, new Vector2(width / 2, 72), font, text, Color.BLACK);
        textAttempts.centerHorizontal();

        Font fontTitle = graph.newFont("BarlowCondensed-Regular.ttf", 60, true, false);
        title = new TextObject(engine, width, height, new Vector2(width / 2, 50), fontTitle, "Averigua el código", Color.BLACK);
        title.center();

        colorBlind = false;
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        // Fondo APP
        engine.getGraphics().clear(Color.WHITE.getValue());

        // Fondo Juego
        graph.setColor(Color.WHITE.getValue());
        graph.fillRectangle(0, 0, width, height);

        // Tablero con todos los intentos
        tab.render();

        // Botones
        if (colorBlind)
            buttonColorBlindActive.render();
        else
            buttonColorBlind.render();

        buttonBack.render();

        // Texto de los intentos restantes
        String text = "Te quedan " + tab.getRemainingAttempts() + " intentos";
        textAttempts.setText(text);
        textAttempts.render();

        title.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            // Handle input del tablero, false si el juego no se ha acabado, true e.o.c
            if (tab.handleInput(events.get(i)))
                break;

            // Activar / Desactivar daltonismo
            if (colorBlind) {
                if (buttonColorBlindActive.handleInput(events.get(i))) {
                    engine.getAudio().stopSound("clickboton.wav");
                    engine.getAudio().playSound("clickboton.wav", false);
                    tab.colorblind(false);
                    colorBlind = !colorBlind;
                }
            } else {
                if (buttonColorBlind.handleInput(events.get(i))) {
                    engine.getAudio().stopSound("clickboton.wav");
                    engine.getAudio().playSound("clickboton.wav", false);
                    tab.colorblind(true);
                    colorBlind = !colorBlind;
                }
            }


            // Volver a la escena de seleccion
            if (buttonBack.handleInput(events.get(i))) {
                engine.getAudio().stopSound("clickboton.wav");
                engine.getAudio().playSound("clickboton.wav", false);
                engine.setScene(new SelectionMenu());
                break;
            }
        }
    }
}
