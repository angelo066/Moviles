package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

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
    private ButtonObject buttonImposible;
    private ButtonObject buttonBack;
    private Texto textSelection;

    @Override
    public void init(Engine engine) {
        this.engine = engine;

        width = 1080;
        height = 1920;
        engine.getGraphics().setSceneSize(width, height);

        int offset = 40;

        // Creacion de los objetos de la escena
        // Mensaje de seleccion
        Font fontSelection = engine.getGraphics().newFont("Nexa.ttf", 50, false, false);
        textSelection = new Texto(engine, width, height, new Vector2(width / 2, height/8), fontSelection, "Selecciona la dificultad.", Color.BLACK);
        textSelection.center();

        // Botones
        Font fontBoton = engine.getGraphics().newFont("Nexa.ttf", 80, false, false);
        Vector2 pos = new Vector2(width / 2, height / 2);
        Vector2 size = new Vector2(500, 100);

        Vector2 posEasy = new Vector2(pos.x, pos.y - (3 * size.y) / 2 - (3 * offset));
        buttonEasy = new ButtonObject(engine, width, height, posEasy, new Vector2(size), 70, fontBoton, "Fácil", Color.GREEN, Color.BLACK);
        buttonEasy.centrar();

        Vector2 posMedium = new Vector2(pos.x, pos.y - size.y / 2 - offset);
        buttonMedium = new ButtonObject(engine, width, height, posMedium, new Vector2(size), 70, fontBoton, "Medio", Color.YELLOW, Color.BLACK);
        buttonMedium.centrar();

        Vector2 posHard = new Vector2(pos.x, pos.y + size.y / 2 + offset);
        buttonHard = new ButtonObject(engine, width, height, posHard, new Vector2(size), 70, fontBoton, "Difícil", Color.ORANGE, Color.BLACK);
        buttonHard.centrar();

        Vector2 posImposible = new Vector2(pos.x, pos.y + (3 * size.y) / 2 + (3 * offset));
        buttonImposible = new ButtonObject(engine, width, height, posImposible, new Vector2(size), 70, fontBoton, "Imposible", Color.RED, Color.BLACK);
        buttonImposible.centrar();

        buttonBack = new ButtonObject(engine, width, height, new Vector2(20, 20), new Vector2(100, 100), "volver.png");

        // Carga de audio
        engine.getAudio().loadSound("clickboton.wav", "click");
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render()
    {
        // Fondo de APP
        engine.getGraphics().clear(Color.DARK_GREY);

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
        buttonImposible.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {

        boolean selected = false;
        Difficulty modo = Difficulty.EASY;

        // Dependiendo del boton, vamos a una escena u otra
        // Si es un boton de juego asignamos la dificultad
        for (int i = 0; i < events.size() && !selected; i++) {

            if (buttonEasy.handleInput(events.get(i))) {
                modo = Difficulty.EASY;
                selected = true;
            } else if (buttonMedium.handleInput(events.get(i))) {
                modo = Difficulty.MEDIUM;
                selected = true;
            } else if (buttonHard.handleInput(events.get(i))) {
                modo = Difficulty.HARD;
                selected = true;
            } else if (buttonImposible.handleInput(events.get(i))) {
                modo = Difficulty.IMPOSSIBLE;
                selected = true;
            } else if (buttonBack.handleInput(events.get(i))) {
                engine.setScene(new MainMenu());
                engine.getAudio().playSound("click", false);
            }

        }

        if (selected){
            engine.setScene(new MasterMind(modo));
            engine.getAudio().playSound("click", false);
        }

    }
}
