package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;

import java.util.ArrayList;

/**
 * Escena de inicio
 */
public class MainMenu extends Scene {
    private ButtonObject buttonPlay;
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
        Vector2 size = new Vector2(width / 2, height / 10);
        Vector2 pos = new Vector2(width / 2, height / 2);

        buttonPlay = new ButtonObject(graphics, new Vector2(pos), size, 40, Color.CYAN,
                new TextObject(graphics, new Vector2(pos), "Nexa.ttf", "Jugar", Color.BLACK, 80, false, false));
        buttonPlay.center();
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
        buttonPlay.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            if (buttonPlay.handleInput(events.get(i))) {
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                engine.setScene(new SelectionMenu());
                break;
            }
        }
    }
}
