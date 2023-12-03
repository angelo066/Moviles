package com.practica1.gamelogic;


import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;

import java.util.ArrayList;

/**
 * Escena de seleccion de dificultad
 */
public class SelectionMenu extends Scene {
    private ButtonObject buttonEasy;
    private ButtonObject buttonMedium;
    private ButtonObject buttonHard;
    private ButtonObject buttonImpossible;
    private ButtonObject buttonBack;
    private TextObject textSelection;

    public SelectionMenu() {
        this.width = 1080;
        this.height = 1920;
    }

    @Override
    public void init(Engine engine) {
        super.init(engine);

        createTexts();

        createButtons();
    }

    private void createTexts() {
        textSelection = new TextObject(graphics, new Vector2(width / 2, height / 7),
                "BarlowCondensed-Regular.ttf", "¿En qué dificultad quieres jugar?", Color.BLACK, 75, true, false);
        textSelection.center();
    }

    private void createButtons() {
        int offsetY = 40;
        Vector2 pos = new Vector2(width / 2, height / 10 * 3);
        Vector2 size = new Vector2(width / 2, height / 10);

        buttonEasy = new ButtonObject(graphics, new Vector2(pos), size, 50, Color.GREEN,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Fácil", Color.BLACK, 80, false, false));
        buttonEasy.center();

        pos.y += (height / 10 + offsetY);
        buttonMedium = new ButtonObject(graphics, new Vector2(pos), size, 50, Color.YELLOW,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Medio", Color.BLACK, 80, false, false));
        buttonMedium.center();

        pos.y += (height / 10 + offsetY);
        buttonHard = new ButtonObject(graphics, new Vector2(pos), size, 50, Color.ORANGE,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Difícil", Color.BLACK, 80, false, false));
        buttonHard.center();

        pos.y += (height / 10 + offsetY);
        buttonImpossible = new ButtonObject(graphics, new Vector2(pos), size, 50, Color.RED,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Imposible", Color.BLACK, 80, false, false));
        buttonImpossible.center();

        buttonBack = new ButtonObject(graphics, new Vector2(20, 20), new Vector2(100, 100), "volver.png");
    }

    @Override
    public void render() {
        // Fondo de APP
        graphics.clear(Color.WHITE.getValue());

        // Fondo de Juego
        graphics.setColor(Color.WHITE.getValue());
        graphics.fillRectangle(0, 0, width, height);

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

            if (buttonBack.handleInput(events.get(i))) {
                engine.setScene(new MainMenu());
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                break;
            } else if (buttonEasy.handleInput(events.get(i))) {
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
            }
        }

        if (selected) {
            audio.stopSound("botonInterfaz.wav");
            audio.playSound("botonInterfaz.wav", false);
            engine.setScene(new MasterMind(mode));
        }
    }
}
