package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Graphics;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

import java.util.ArrayList;

/**
 * Escena de juego
 */
public class MasterMind implements Scene {

    private Engine engine;
    private Graphics graph;
    private int width;
    private int height;

    // Instancia al tablero (lleva la logica de juego)
    private Tablero TAB;

    private ButtonObject buttonColorBlind;
    private ButtonObject buttonBack;
    private Texto textAttempts;
    private Difficulty mode;

    private boolean COLOR_BLIND = false;

    public MasterMind(Difficulty mode)
    {
        this.mode = mode;
    }

    @Override
    public void init(Engine engine)
    {
        this.engine = engine;
        this.graph = engine.getGraphics();
        width = 1080;
        height = 1920;
        engine.getGraphics().setSceneSize(width, height);

        // Creacion del tablero de juego
        this.TAB = new Tablero(engine, width, height, mode);

        // Botones
        buttonColorBlind = new ButtonObject(engine,width,height, new Vector2(width -120, 20), new Vector2(100, 100), "ojo.png");
        buttonBack = new ButtonObject(engine,width,height, new Vector2(20, 20), new Vector2(100, 100), "volver.png");

        // Texto de indicacion de intentos restantes
        Font font = graph.newFont("Nexa.ttf", 50, false, false);
        String text = "Te quedan " + TAB.getNUM_INTENTOS_RESTANTES() + " intentos";
        textAttempts = new Texto(engine, width, height, new Vector2(width/2, 0), font, text, Color.BLACK);
        textAttempts.centerHorizontal();

    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render()
    {
        // Fondo APP
        graph.clear(Color.DARK_GREY);

        // Fondo Juego
        graph.setColor(Color.WHITE);
        graph.fillRectangle(0, 0, width, height);

        // Tablero con todos los intentos
        TAB.render();

        // Botones
        buttonColorBlind.render();
        buttonBack.render();

        // Texto de los intentos restantes
        String text = "Te quedan " + TAB.getNUM_INTENTOS_RESTANTES() + " intentos";
        textAttempts.setText(text);
        textAttempts.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        //Recorremos el array de eventos mandandolos al tablero
        for (int i = 0; i < events.size(); i++)
        {
            // Input del tablero
            TAB.handleInput(events.get(i));

            // Activar / Desactivar daltonismo
            if (buttonColorBlind.handleInput(events.get(i))) {
                engine.getAudio().playSound("click", false);

                if (!COLOR_BLIND)
                    TAB.colorblind(true);
                else
                    TAB.colorblind(false);
                COLOR_BLIND = !COLOR_BLIND;
            }

            // Volver a la escena de seleccion
            if(buttonBack.handleInput(events.get(i))){
                engine.setScene(new SelectionMenu());
                engine.getAudio().playSound("click", false);
            }
        }
    }


}