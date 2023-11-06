package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Graphics;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

import java.util.ArrayList;

public class EndScreen implements Scene {

    private Engine engine;
    private Graphics graph;
    private int width;
    private int height;
    private ButtonObject buttonRepeat;
    private ButtonObject buttonBackMenu;
    private TextObject textEndMessage;
    private TextObject textDescriptionMessage;
    private TextObject textCode;
    private TextObject textUsedAttempts;
    private Color[] combination_win;
    int numAttempts = 0;

    boolean WIN = false;
    int NUM_SQUARES;
    int CIRCLE_RADIO = 50;

    private CircleObject[] circles;
    private Difficulty mode;

    private boolean COLOR_BLIND;

    public EndScreen(Color[] combination_win, boolean WIN, int NUM_SQUARES, Difficulty mode, int numAttempts, boolean COLOR_BLIND)
    {
        this.WIN = WIN;
        this.combination_win = combination_win;
        this.NUM_SQUARES = NUM_SQUARES;
        this.mode = mode;
        this.numAttempts = numAttempts;
        this.COLOR_BLIND = COLOR_BLIND;

    }
    @Override
    public void init(Engine engine)
    {
        this.engine = engine;
        this.graph = engine.getGraphics();

        width = 1080;
        height = 1920;
        engine.getGraphics().setSceneSize(width,height);

        // Creacion de las fuentes que utilizamos
        Font fontBoton = graph.newFont("Nexa.ttf", 70, false, false);
        Font fontIntentos = graph.newFont("Nexa.ttf", 80, false, false);
        Font fontMensaje = graph.newFont("Nexa.ttf", 100, true, false);
        Font fontDescripcion = graph.newFont("Nexa.ttf", 50, false, false);

        // Botones
        Vector2 size = new Vector2(800,120);
        Vector2 posRep = new Vector2(width/2, height/10 * 6);
        buttonRepeat = new ButtonObject(engine, width,height,posRep, new Vector2(size), 70, fontBoton,"Volver a jugar", Color.CYAN, Color.BLACK);
        buttonRepeat.centrar();

        Vector2 posVolver = new Vector2(width/2, height/10 * 7);
        buttonBackMenu = new ButtonObject(engine,width,height, posVolver, new Vector2(size), 70, fontBoton,"Elegir Dificultad", Color.CYAN, Color.BLACK);
        buttonBackMenu.centrar();

        // Textos
        String mensaje ="";
        String descripcion = "";
        String intentos = "";
        if(WIN)
        {
            mensaje = "ENHORABUENA!!";
            descripcion = "Has averiguado el codigo en";
            intentos = numAttempts + " intentos";
            engine.getAudio().loadSound("yuju.wav", "yuju");
            engine.getAudio().playSound("yuju", false);
        }
        else
        {
            mensaje = "HAS PERDIDO :(";
            descripcion = "Te has quedado sin intentos";
            engine.getAudio().loadSound("douh.wav", "douh");
            engine.getAudio().playSound("douh", false);
        }
        textEndMessage = new TextObject(engine, width,height,new Vector2(width/2,height/10), fontMensaje, mensaje, Color.BLACK);
        textEndMessage.center();
        textDescriptionMessage = new TextObject(engine,width,height, new Vector2(width/2,height/10 * 2), fontDescripcion, descripcion, Color.BLACK);
        textDescriptionMessage.center();
        textUsedAttempts = new TextObject(engine, width,height,new Vector2(width/2,height/10 * 5/2), fontIntentos, intentos, Color.BLACK);
        textUsedAttempts.center();
        textCode = new TextObject(engine,width,height, new Vector2(width/2,height/10 * 7/2), fontDescripcion, "código:", Color.BLACK);
        textCode.center();

        // Colocacion de los circulos
        setCircles();

    }

    private void setCircles()
    {
        // Creamos el array de circulos para colocarlos
        this.circles = new CircleObject[NUM_SQUARES];
        Font font;
        font = graph.newFont("Nexa.ttf", 50, false, false);

        // Calculos de posicion
        int totalWidth = NUM_SQUARES * CIRCLE_RADIO * 2;
        int spaceToEachSide = (width - totalWidth) / 2;

        // Creacion de cada circulo
        for(int i = 0; i < combination_win.length; i++)
        {
            int x = spaceToEachSide + i * (CIRCLE_RADIO * 2);

            circles[i] = new CircleObject(engine, width, height,
                    new Vector2(x,800), combination_win[i].getId(), font);

            circles[i].colorblind(COLOR_BLIND);
            circles[i].setColor(combination_win[i]);
            circles[i].uncover(true);
        }
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
        graph.fillRectangle(0,0,width,height);

        // Botones
        buttonRepeat.render();
        buttonBackMenu.render();

        // Textos
        textEndMessage.render();
        textDescriptionMessage.render();
        textUsedAttempts.render();
        textCode.render();

        // Codigo colores
        for (int i = 0; i < NUM_SQUARES; i++) {
            circles[i].render();
        }

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events)
    {
        // Comprueba el boton al que se ha pulsado mandadolo a una escena u otra
        for(int i = 0; i < events.size(); i++){
            if(buttonRepeat.handleInput(events.get(i)))
            {
                engine.getAudio().playSound("start", false);
                engine.setScene(new MasterMind(mode));
            }
            else if(buttonBackMenu.handleInput(events.get(i)))
            {
                engine.getAudio().playSound("start", false);
                engine.setScene(new SelectionMenu());
            }
        }
    }

}
