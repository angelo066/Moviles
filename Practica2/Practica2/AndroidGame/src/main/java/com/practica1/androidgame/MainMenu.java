package com.practica1.androidgame;

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
    private TextObject textTitle;

    private int colorText;
    private int colorButton1;
    private int colorButton2;
    public MainMenu() {
        this.width = 1080;
        this.height = 1920;
    }

    @Override
    public void init(Engine engine) {
        super.init(engine);

        createButtons();
        createTexts();
    }

    private void createButtons() {
        Vector2 size = new Vector2(width / 4 * 3, height / 10);
        Vector2 pos = new Vector2(width / 2, height / 2);
        int offsetY = 50;

        colorText = GameManager.getInstance().getCurrentSkinPalette().getColor2();
        colorButton1 = GameManager.getInstance().getCurrentSkinPalette().getColor1();
        colorButton2 = GameManager.getInstance().getCurrentSkinPalette().getColor3();

        buttonQuickMatch = new ButtonObject(graphics, new Vector2(pos), size, 40, colorButton1,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Partida rápida", colorText, 80, false, false));
        buttonQuickMatch.center();

        pos.y += size.y + offsetY;
        buttonExploreWorlds = new ButtonObject(graphics, new Vector2(pos), size, 40, colorButton2,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Explorar mundos", colorText, 80, false, false));
        buttonExploreWorlds.center();

        pos.y += size.y + offsetY;
        buttonPersonalize = new ButtonObject(graphics, new Vector2(pos), size, 40, colorText,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Personalizar", colorButton1, 80, false, false));
        buttonPersonalize.center();
    }

    private void createTexts() {
        textTitle = new TextObject(graphics, new Vector2(width / 2, height / 5), "BarlowCondensed-Regular.ttf", "Master Mind",
                colorText, 200, true, false);
        textTitle.center();
    }

    @Override
    public void render() {
        // Fondo de APP
        int backColor = GameManager.getInstance().getCurrentSkinPalette().getColorBackground();
        graphics.clear(backColor);

        // Fondo de Juego
        graphics.setColor(backColor);
        graphics.fillRectangle(0, 0, width, height);

        // Objetos
        textTitle.render();
        buttonQuickMatch.render();
        buttonExploreWorlds.render();
        buttonPersonalize.render();
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

            }
        }
    }
}
