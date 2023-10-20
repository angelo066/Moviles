package com.example.mastermind;

import com.example.engine.Engine;
import com.example.engine.Graphics;
import com.example.engine.Scene;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class MasterMind implements Scene {

    private Engine engine;
    private Graphics graph;

    private Tablero tablero;

    private int width;
    private int height;
    @Override
    public void init(Engine engine) {
        this.engine = engine;
        this.graph = engine.getGraphics();
        this.tablero = new Tablero(engine);
        tablero.configuracion(Dificultad.FACIL);

        width = 1080;
        height = 1920;
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {

        graph.clear(0xFFFFFF);

        tablero.render();

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {

    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}