package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Graphics;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

import java.util.ArrayList;

public class MenuDificultad implements Scene {

    private Engine engine;
    private Graphics graph;
    private int width;
    private int height;
    private BotonEscena botonFacil;
    private BotonEscena botonMedio;
    private BotonEscena botonDificil;
    private BotonEscena botonImposible;
    private Texto titulo;

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
        botonFacil = new BotonEscena(engine, posFacil, new Vector2(size), 70, fontBoton,"Fácil", colores.VERDE, colores.NEGRO);
        botonFacil.centrar();

        Vector2 posMedio = new Vector2(pos.x, pos.y - size.y/2 - offset);
        botonMedio = new BotonEscena(engine, posMedio, new Vector2(size), 70, fontBoton,"Medio", colores.AMARILLO, colores.NEGRO);
        botonMedio.centrar();

        Vector2 posDificil = new Vector2(pos.x, pos.y + size.y/2 + offset);
        botonDificil = new BotonEscena(engine, posDificil, new Vector2(size), 70, fontBoton,"Difícil", colores.NARANJA, colores.NEGRO);
        botonDificil.centrar();

        Vector2 posImposible = new Vector2(pos.x, pos.y + (3*size.y)/2 + (3*offset));
        botonImposible = new BotonEscena(engine, posImposible, new Vector2(size), 70, fontBoton,"Imposible", colores.ROJO, colores.NEGRO);
        botonImposible.centrar();

        titulo = new Texto(engine, new Vector2(width/2,250), fontTitulo, "Selecciona la dificultad.", colores.NEGRO);
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
        botonFacil.render();
        botonMedio.render();
        botonDificil.render();
        botonImposible.render();
        titulo.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        //Recorremos el array de eventos mandandolos al tablero
        for(int i = 0; i < events.size(); i++){
            botonFacil.handleInput(events.get(i));
            botonMedio.handleInput(events.get(i));
            botonDificil.handleInput(events.get(i));
            botonImposible.handleInput(events.get(i));
        }
    }
}