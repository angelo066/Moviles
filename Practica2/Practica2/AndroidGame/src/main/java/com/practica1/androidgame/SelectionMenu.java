package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;
import com.practica1.androidengine.Vector2;

import java.util.ArrayList;

/**
 * Escena de seleccion de dificultad
 */
public class SelectionMenu implements Scene {

    private Engine engine;
    private int width;
    private int height;

    private ButtonObject buttonEasy;
    private ButtonObject buttonMedium;
    private ButtonObject buttonHard;
    private ButtonObject buttonImpossible;
    private ButtonObject buttonBack;
    private TextObject textSelection;

    @Override
    public void init(Engine engine) {
        this.engine = engine;

        width = 1080;
        height = 1920;
        engine.getGraphics().setSceneSize(width, height);

        int offset = 40;

        // Creacion de los objetos de la escena
        // Mensaje de seleccion
        Font fontSelection = engine.getGraphics().newFont("BarlowCondensed-Regular.ttf", 75, true, false);
        textSelection = new TextObject(engine, width, height, new Vector2(width / 2, height / 7), fontSelection, "¿En qué dificultad quieres jugar?", Color.BLACK);
        textSelection.center();

        // Botones
        Font fontButton = engine.getGraphics().newFont("Nexa.ttf", 80, false, false);
        Vector2 pos = new Vector2(width / 2, height / 2);
        Vector2 size = new Vector2(500, 150);

        Vector2 posEasy = new Vector2(pos.x, pos.y - (3 * size.y) / 2 - (3 * offset));
        buttonEasy = new ButtonObject(engine, width, height, posEasy, new Vector2(size), 50, fontButton, "Fácil", Color.GREEN, Color.BLACK);
        buttonEasy.centrar();

        Vector2 posMedium = new Vector2(pos.x, pos.y - size.y / 2 - offset);
        buttonMedium = new ButtonObject(engine, width, height, posMedium, new Vector2(size), 50, fontButton, "Medio", Color.YELLOW, Color.BLACK);
        buttonMedium.centrar();

        Vector2 posHard = new Vector2(pos.x, pos.y + size.y / 2 + offset);
        buttonHard = new ButtonObject(engine, width, height, posHard, new Vector2(size), 50, fontButton, "Difícil", Color.ORANGE, Color.BLACK);
        buttonHard.centrar();

        Vector2 posimposible = new Vector2(pos.x, pos.y + (3 * size.y) / 2 + (3 * offset));
        buttonImpossible = new ButtonObject(engine, width, height, posimposible, new Vector2(size), 50, fontButton, "Imposible", Color.RED, Color.BLACK);
        buttonImpossible.centrar();

        buttonBack = new ButtonObject(engine, width, height, new Vector2(20, 20), new Vector2(100, 100), "volver.png");

        // Carga de audio
        engine.getAudio().loadSound("clickboton.wav", "click");
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {
        // Fondo de APP
        engine.getGraphics().clear(Color.WHITE);

        // Fondo de Juego
        engine.getGraphics().setColor(Color.WHITE);
        engine.getGraphics().fillRectangle(0, 0, width, height);

        // Texto de seleccion
        textSelection.render();

        // Boton de volver
        buttonBack.render();

        // Botones de seleccion
        buttonEasy.render();
        buttonMedium.render();
        buttonHard.render();
        buttonImpossible.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {

        boolean selected = false;
        Difficulty mode = Difficulty.EASY;

        // Dependiendo del boton, vamos a una escena u otra
        // Si es un boton de juego asignamos la dificultad
        for (int i = 0; i < events.size() && !selected; i++) {

            if (buttonEasy.handleInput(events.get(i))) {
                mode = Difficulty.EASY;
                selected = true;
            } else if (buttonMedium.handleInput(events.get(i))) {
                mode = Difficulty.MEDIUM;
                selected = true;
            } else if (buttonHard.handleInput(events.get(i))) {
                mode = Difficulty.HARD;
                selected = true;
            } else if (buttonImpossible.handleInput(events.get(i))) {
                mode = Difficulty.IMPOSSIBLE;
                selected = true;
            } else if (buttonBack.handleInput(events.get(i))) {
                engine.setScene(new MainMenu());
                engine.getAudio().stopSound("click");
                engine.getAudio().playSound("click", false);
            }

        }

        if (selected) {
            engine.getAudio().stopSound("click");
            engine.getAudio().playSound("click", false);
            engine.setScene(new MasterMind(mode));
        }
    }
}
