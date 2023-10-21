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
        
        engine.getGraphics().setSceneSize(width,height);

    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render() {

        //Fondo
        graph.setColor(0x000000);
        graph.fillRectangle(0,0,width,height);

        tablero.render();

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {

    }

}