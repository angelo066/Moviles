package com.practica1.androidgame;

import android.app.Activity;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;

/**
 * Escena de inicio
 */
public class MainMenu extends Scene {
    private ButtonObject buttonPlay;
    private ButtonObject buttonExit;

    private TextObject textTitle;

    public MainMenu() {
        this.width = 1080;
        this.height = 1920;
    }

    @Override
    public void init(Engine engine) {
        super.init(engine);

        //Carga de assets
        engine.getAudio().loadSound("botonInterfaz.wav");
        engine.getAudio().loadSound("clickboton.wav");
        engine.getAudio().loadSound("yuju.wav");
        engine.getAudio().loadSound("douh.wav");

        ResourceManager.getInstance().createFont("BarlowCondensed-Regular.ttf", 200, true, true);
        ResourceManager.getInstance().createFont("Nexa.ttf", 80, false, false);

        ResourceManager.getInstance().createImage("volver.png");
        ResourceManager.getInstance().createImage("ojo.png");
        ResourceManager.getInstance().createImage("ojo2.png");

        createTexts();

        createButtons();
    }

    private void createButtons() {
        buttonPlay = new ButtonObject(graphics, new Vector2(width / 2, height / 2), new Vector2(width / 4 * 3, height / 10), 40, Color.CYAN,
                new TextObject(graphics, new Vector2(width / 2, height / 2), "Nexa.ttf", "Jugar", Color.BLACK, 80, false, false));
        buttonPlay.center();

        buttonExit = new ButtonObject(graphics, new Vector2(width / 2, height / 6 * 4), new Vector2(width / 4 * 2, height / 10), 40, Color.RED,
                new TextObject(graphics, new Vector2(width / 2, height / 6 * 4), "Nexa.ttf", "Salir", Color.BLACK, 80, false, false));
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
        buttonPlay.render();
        buttonExit.render();
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
            else if(buttonExit.handleInput(events.get(i))) {
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                System.exit(0);
                break;
            }
        }
    }
}
