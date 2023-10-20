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

    //Tamaño de los circulos
    private final int circleRad = 40;
    //Espacio entre circulos
    private final int circleSpace = 20;

    private int width;
    private int height;
    @Override
    public void init(Engine engine) {
        this.engine = engine;
        this.graph = engine.getGraphics();
        this.tablero = new Tablero();
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

        graph.clear(0xFFFFFF);
        graph.setColor(0xFF0000);
        int N_Colores = tablero.getN_COLORS();
        int h = graph.getHeight();
        int w = graph.getWidth();


        for(int i = 0; i < N_Colores; i++){
            colores color = colores.values()[i +1]; //Conversion del indice al color
            graph.setColor(color.getValue());   //Cogemos el valor del color
            graph.fillCircle( circleRad * (2 + i) + (circleSpace * i), h - circleRad * 2, circleRad);
        }

        int casillas = tablero.getNum_casillas();
        int intentos = tablero.getNum_intentos();

        for(int i = 0; i < intentos; i++){
            for(int j = 0; j < casillas; j++){
                graph.setColor(0x0000FF);
                graph.fillCircle(circleRad * (2 + j) + (circleSpace * j), h- 200 - circleRad * 1.3f * i, circleRad);
            }
        }

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {

    }

}