package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Graphics;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

import java.util.ArrayList;

public class Menu implements Scene {

    private Engine engine;
    private Graphics graph;
    private int width;
    private int height;
    private Boton boton;
    private Texto titulo;

    @Override
    public void init(Engine engine)
    {
        this.engine = engine;
        this.graph = engine.getGraphics();

        width = 1080;
        height = 1920;

        engine.getGraphics().setSceneSize(width,height);

        Vector2 size = new Vector2(500,150);
        Vector2 pos = new Vector2(width/2,height/2);
        Vector2 posTitulo = new Vector2(width/2,250);
        Font font = graph.newFont("Nexa.ttf", 80, false, false);
        Font fontTitulo = graph.newFont("Nexa.ttf", 150, false, false);
        boton = new Boton(engine, pos, size, 70, font,"Jugar", colores.CYAN, colores.NEGRO);
        boton.centrar();
        titulo = new Texto(engine, posTitulo, size, fontTitulo, "Master Mind", colores.NEGRO);
        titulo.centrar();
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render()
    {
        //Fondo
        graph.setColor(colores.BLANCO.getValue());
        graph.fillRectangle(0,0,width,height);
        boton.render();
        titulo.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {

    }
}
