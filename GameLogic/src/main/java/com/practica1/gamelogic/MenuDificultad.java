package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Graphics;
import com.practica1.engine.Image;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

import java.util.ArrayList;

public class MenuDificultad implements Scene {

    private Engine engine;
    private Graphics graph;
    private int width;
    private int height;
    private Boton botonFacil;
    private Boton botonMedio;
    private Boton botonDificil;
    private Boton botonImposible;
    private Boton botonVolver;
    private Texto titulo;
    Dificultad modo;
    boolean seleccionado = false;

    @Override
    public void init(Engine engine)
    {
        this.engine = engine;
        this.graph = engine.getGraphics();

        width = 1080;
        height = 1920;

        int offset = 40;

        engine.getGraphics().setSceneSize(width,height);

        Font fontBoton = graph.newFont("Nexa.ttf", 80, false, false);
        Font fontTitulo = graph.newFont("Nexa.ttf", 50, false, false);

        Vector2 pos = new Vector2(width/2,height/2);
        Vector2 size = new Vector2(500,100);

        Vector2 posFacil = new Vector2(pos.x, pos.y - (3*size.y)/2 - (3*offset));
        botonFacil = new Boton(engine, width,height,posFacil, new Vector2(size), 70, fontBoton,"Fácil", Color.VERDE, Color.NEGRO);
        botonFacil.centrar();

        Vector2 posMedio = new Vector2(pos.x, pos.y - size.y/2 - offset);
        botonMedio = new Boton(engine, width,height,posMedio, new Vector2(size), 70, fontBoton,"Medio", Color.AMARILLO, Color.NEGRO);
        botonMedio.centrar();

        Vector2 posDificil = new Vector2(pos.x, pos.y + size.y/2 + offset);
        botonDificil = new Boton(engine, width,height,posDificil, new Vector2(size), 70, fontBoton,"Difícil", Color.NARANJA, Color.NEGRO);
        botonDificil.centrar();

        Vector2 posImposible = new Vector2(pos.x, pos.y + (3*size.y)/2 + (3*offset));
        botonImposible = new Boton(engine,width,height, posImposible, new Vector2(size), 70, fontBoton,"Imposible", Color.ROJO, Color.NEGRO);
        botonImposible.centrar();

        botonVolver = new Boton(engine,width,height, new Vector2(0,0), new Vector2(100,100), "volver.png");

        titulo = new Texto(engine,width,height, new Vector2(width/2,250), fontTitulo, "Selecciona la dificultad.", Color.NEGRO);
        titulo.centrar();
    }

    @Override
    public void update(double deltaTime) {

    }

    @Override
    public void render()
    {
        //Fondo
        graph.setColor(Color.BLANCO);
        graph.fillRectangle(0,0,width,height);
        botonFacil.render();
        botonMedio.render();
        botonDificil.render();
        botonImposible.render();
        titulo.render();
        botonVolver.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        //Recorremos el array de eventos mandandolos al tablero
        for(int i = 0; i < events.size(); i++){
            if(botonFacil.handleInput(events.get(i)))
            {
                modo = Dificultad.FACIL;
                seleccionado = true;
            }
            if(botonMedio.handleInput(events.get(i)))
            {
                modo = Dificultad.MEDIO;
                seleccionado = true;
            }
            if(botonDificil.handleInput(events.get(i)))
            {
                modo = Dificultad.DIFICIL;
                seleccionado = true;
            }
            if(botonImposible.handleInput(events.get(i)))
            {
                modo = Dificultad.IMPOSIBLE;
                seleccionado = true;
            }
            if(botonVolver.handleInput(events.get(i)))
                engine.setScene(new MenuPrincipal());
            if(seleccionado)
            {
                engine.setScene(new MasterMind(modo));
            }
        }
    }
}
