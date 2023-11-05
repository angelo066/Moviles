package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Graphics;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

import java.util.ArrayList;

public class MenuPrincipal implements Scene {

    private Engine engine;
    private Graphics graph;
    private int width;
    private int height;
    private Boton botonJugar;
    private Texto titulo;

    @Override
    public void init(Engine engine)
    {
        this.engine = engine;
        this.graph = engine.getGraphics();

        width = 1080;
        height = 1920;

        engine.getGraphics().setSceneSize(width,height);

        Font fontBoton = graph.newFont("Nexa.ttf", 80, false, false);
        Font fontTitulo = graph.newFont("Nexa.ttf", 150, false, false);

        botonJugar = new Boton(engine,width,height, new Vector2(width/2,height/2), new Vector2(500,150), 70, fontBoton,"Jugar", Color.CYAN, Color.NEGRO);
        botonJugar.centrar();
        titulo = new Texto(engine,width,height, new Vector2(width/2,250), fontTitulo, "Master Mind", Color.NEGRO);
        titulo.centrar();
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render()
    {
        graph.clear(Color.GRIS_OSCURO);

        graph.setColor(Color.BLANCO);
        graph.fillRectangle(0,0,width,height);

        botonJugar.render();
        titulo.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        //Recorremos el array de eventos mandandolos al tablero
        for(int i = 0; i < events.size(); i++){
            if(botonJugar.handleInput(events.get(i)))
                engine.setScene(new MenuDificultad());
        }
    }
}
