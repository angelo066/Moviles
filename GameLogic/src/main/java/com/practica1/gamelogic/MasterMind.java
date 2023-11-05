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

public class MasterMind implements Scene {

    private Engine engine;
    private Graphics graph;

    private Tablero tablero;

    private int width;
    private int height;
    private Font font;
    private Boton boton_daltonismo;
    private Boton boton_volver;
    private Texto indicador_intentos;
    private Dificultad modo;

    private boolean DALTONISMO = false;

    public MasterMind(Dificultad modo)
    {
        this.modo = modo;
    }

    @Override
    public void init(Engine engine) {
        this.engine = engine;
        this.graph = engine.getGraphics();

        width = 1080;
        height = 1920;

        engine.getGraphics().setSceneSize(width, height);

        this.tablero = new Tablero(engine, width, height, modo);

        font = graph.newFont("Nexa.ttf", 80, false, false);
        boton_daltonismo = new Boton(engine,width,height, new Vector2(width - 120, 20), new Vector2(100, 100), "ojo.png");
        boton_volver = new Boton(engine,width,height, new Vector2(20, 20), new Vector2(100, 100), "volver.png");
        String text = "Te quedan " + tablero.getNUM_INTENTOS_RESTANTES() + " intentos";
        indicador_intentos = new Texto(engine, width, height, new Vector2(width/2, 0), font, text, Color.NEGRO);
        indicador_intentos.centrarEnHorizontal();

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

        boton_daltonismo.render();
        boton_volver.render();

        indicador_intentos.render();
        String text = "Te quedan " + tablero.getNUM_INTENTOS_RESTANTES() + " intentos";
        indicador_intentos.setText(text);

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        //Recorremos el array de eventos mandandolos al tablero
        for (int i = 0; i < events.size(); i++) {
            tablero.handleInput(events.get(i));

            if (boton_daltonismo.handleInput(events.get(i))) {
                engine.getAudio().playSound("click", false);

                if (!DALTONISMO)
                    tablero.Daltonismo(true);
                else
                    tablero.Daltonismo(false);
                DALTONISMO = !DALTONISMO;
            }

            if(boton_volver.handleInput(events.get(i))){
                engine.setScene(new MenuDificultad());
                engine.getAudio().playSound("click", false);
            }
        }
    }


}