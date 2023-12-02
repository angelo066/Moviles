package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;

/**
 * Escena de inicio
 */
public class MainMenu extends Scene {
    private ButtonObject buttonQuickMatch;
    private ButtonObject buttonExploreWorlds;
    private ButtonObject buttonPersonalize;
    private ButtonObject buttonExit;

    private TextObject textTitle;

    public MainMenu() {
        this.width = 1080;
        this.height = 1920;
    }

    @Override
    public void init(Engine engine) {
        super.init(engine);

        createTexts();

        createButtons();
    }

    private void createButtons() {
        Vector2 size = new Vector2(width / 4 * 3, height / 10);
        Vector2 pos = new Vector2(width / 2, height / 2);
        int offsetY = 50;

        buttonQuickMatch = new ButtonObject(graphics, new Vector2(pos), size, 40, Color.CYAN,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Partida rápida", Color.BLACK, 80, false, false));
        buttonQuickMatch.center();

        pos.y += size.y + offsetY;
        buttonExploreWorlds = new ButtonObject(graphics, new Vector2(pos), size, 40, Color.CYAN,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Explorar mundos", Color.BLACK, 80, false, false));
        buttonExploreWorlds.center();

        pos.y += size.y + offsetY;
        buttonPersonalize = new ButtonObject(graphics, new Vector2(pos), size, 40, Color.BLACK,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Personalizar", Color.CYAN, 80, false, false));
        buttonPersonalize.center();

        pos.y += size.y + offsetY;
        buttonExit = new ButtonObject(graphics, new Vector2(pos), size, 40, Color.RED,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Salir", Color.BLACK, 80, false, false));
        buttonExit.center();
    }

    private void createTexts() {
        textTitle = new TextObject(graphics, new Vector2(width / 2, height / 5), "BarlowCondensed-Regular.ttf", "Master Mind",
                Color.BLACK, 200, true, true);
        textTitle.center();
    }

    @Override
    public void render() {
        // Fondo de la APP
        graphics.clear(Color.WHITE.getValue());

        // Fondo del juego
        graphics.setColor(Color.WHITE.getValue());
        graphics.fillRectangle(0, 0, width, height);

        // Objetos
        textTitle.render();
        buttonQuickMatch.render();
        buttonExploreWorlds.render();
        buttonPersonalize.render();
        buttonExit.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            if (buttonQuickMatch.handleInput(events.get(i))) {
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                SceneManager.getInstance().addScene(new SelectionMenu());
                SceneManager.getInstance().goToNextScene();
                break;
            } else if (buttonPersonalize.handleInput(events.get(i))) {
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                SceneManager.getInstance().addScene(new Shop());
                SceneManager.getInstance().goToNextScene();
                break;

            }
            else if (buttonExploreWorlds.handleInput(events.get(i))) {
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                SceneManager.getInstance().addScene(new WorldSelectionMenu());
                SceneManager.getInstance().goToNextScene();
                break;

            } else if (buttonExit.handleInput(events.get(i))) {
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                System.exit(0);
                break;
            }

        }
    }
}
