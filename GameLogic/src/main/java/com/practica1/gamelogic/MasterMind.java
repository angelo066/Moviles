package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Graphics;
import com.practica1.engine.Image;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;

import java.util.ArrayList;

public class MasterMind implements Scene {

    private Engine engine;
    private Graphics graph;

    private Tablero tablero;

    private int width;
    private int height;
    private Image image;
    private Font font;

    @Override
    public void init(Engine engine) {
        this.engine = engine;
        this.graph = engine.getGraphics();

        width = 1080;
        height = 1920;

        engine.getGraphics().setSceneSize(width, height);
        this.tablero = new Tablero(engine);

        image = graph.newImage("homero.png");
        font = graph.newFont("Nexa.ttf", 80, false, false);

        //engine.getAudio().loadSound("doFlauta.wav", "a");
    }

    @Override
    public void update(double deltaTime) {
        //Crear los eventos digo yo
        tablero.update(deltaTime);
    }

    @Override
    public void render() {

        graph.clear(Color.GRIS_OSCURO);

        graph.setColor(Color.BLANCO);
        graph.fillRectangle(0, 0, width, height);

        tablero.render();

        graph.setColor(Color.MARRON);
        font.setSize(50);
        graph.setFont(font);

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        //Recorremos el array de eventos mandandolos al tablero
        for (int i = 0; i < events.size(); i++) {
            tablero.handleInput(events.get(i));
        }
    }

    void cargarHomer() {
        graph.drawImage(image, 0, 0, 400, 400);

    }

}