package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

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
    int colorText;

    public SelectionMenu() {
        this.width = 1080;
        this.height = 1920;
    }

    @Override
    public void init(Engine engine) {
        super.init(engine);


        colorText = GameManager.getInstance().getCurrentSkinPalette().getColor2();

        createTexts();

        createButtons();
    }

    private void createTexts() {
        textSelection = new TextObject(graphics, new Vector2(width / 2, height / 7),
                "BarlowCondensed-Regular.ttf", "¿En qué dificultad quieres jugar?", colorText, 75, true, false);
        textSelection.center();
    }

    private void createButtons() {
        int offsetY = 40;
        Vector2 pos = new Vector2(width / 2, height / 10 * 3);
        Vector2 size = new Vector2(width / 2, height / 10);

        int colorButton = GameManager.getInstance().getCurrentSkinPalette().getColor1();
        buttonEasy = new ButtonObject(graphics, new Vector2(pos), size, 50, colorButton,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Fácil", colorText, 80, false, false));
        buttonEasy.center();

        pos.y += (height / 10 + offsetY);
        buttonMedium = new ButtonObject(graphics, new Vector2(pos), size, 50, colorButton,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Medio", colorText, 80, false, false));
        buttonMedium.center();

        pos.y += (height / 10 + offsetY);
        buttonHard = new ButtonObject(graphics, new Vector2(pos), size, 50, colorButton,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Difícil", colorText, 80, false, false));
        buttonHard.center();

        pos.y += (height / 10 + offsetY);
        buttonImpossible = new ButtonObject(graphics, new Vector2(pos), size, 50, colorButton,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Imposible", colorText, 80, false, false));
        buttonImpossible.center();

        buttonBack = new ButtonObject(graphics, new Vector2(20, 20), new Vector2(100, 100), "volver.png");
    }

    @Override
    public void render() {
        // Fondo de APP
        int backColor = GameManager.getInstance().getCurrentSkinPalette().getColorBackground();
        graphics.clear(backColor);

        // Fondo de Juego
        graphics.setColor(backColor);
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
                SceneManager.getInstance().addScene(new MainMenu());
                SceneManager.getInstance().goToNextScene();
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
            SceneManager.getInstance().addScene(new MasterMind(mode));
            SceneManager.getInstance().goToNextScene();
        }
    }
}
