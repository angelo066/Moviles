package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Graphics;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

import java.util.ArrayList;

public class Final implements Scene {

    private Engine engine;
    private Graphics graph;
    private int width;
    private int height;
    private Boton botonRepetir;
    private Boton botonVolverMenu;
    private Texto MensajeFinal;
    private Texto MensajeDescripcion;
    private Texto Codigo;
    private Texto NumeroIntentos;
    private Color[] combinacion_ganadora;
    int numIntentos = 0;

    boolean HaGanado = false;
    int NUM_CASILLAS;
    int RADIO_CIRCULO = 50;

    private Circulo[] circles;
    Dificultad modo;

    private Font fuente;

    private boolean daltonicos;

    public Final(Color[] combinacion, boolean HaGanado, int casillas, Dificultad modo, int numIntentos, boolean daltonicos)
    {
        super();
        this.HaGanado = HaGanado;
        combinacion_ganadora = combinacion;
        this.NUM_CASILLAS = casillas;
        this.modo = modo;
        this.numIntentos = numIntentos;
        this.daltonicos = daltonicos;

    }
    @Override
    public void init(Engine engine)
    {
        this.engine = engine;
        this.graph = engine.getGraphics();

        width = 1080;
        height = 1920;

        int offset = 40;

        engine.getGraphics().setSceneSize(width,height);

        // Estoy hay que buscar forma de con una sola fuente cambiar el size y tal
        Font fontBoton = graph.newFont("Nexa.ttf", 70, false, false);
        Font fontIntentos = graph.newFont("Nexa.ttf", 80, false, false);
        Font fontMensaje = graph.newFont("Nexa.ttf", 100, true, false);
        Font fontDescripcion = graph.newFont("Nexa.ttf", 50, false, false);

        Vector2 pos = new Vector2(width/2,height/2);
        Vector2 size = new Vector2(800,120);

        Vector2 posRep = new Vector2(width/2, height/2 + 300);
        botonRepetir = new Boton(engine, width,height,posRep, new Vector2(size), 70, fontBoton,"Volver a jugar", Color.CYAN, Color.NEGRO);
        botonRepetir.centrar();

        Vector2 posVolver = new Vector2(width/2, height/2 + 450);
        botonVolverMenu = new Boton(engine,width,height, posVolver, new Vector2(size), 70, fontBoton,"Elegir Dificultad", Color.CYAN, Color.NEGRO);
        botonVolverMenu.centrar();

        String mensaje ="";
        String descripcion = "";
        String intentos = "";
        if(HaGanado)
        {
            mensaje = "ENHORABUENA!!";
            descripcion = "Has averiguado el codigo en";
            intentos = numIntentos + " intentos";
            MensajeFinal = new Texto(engine, width,height,new Vector2(width/2,200), fontMensaje, mensaje, Color.NEGRO);
        }
        else
        {
            mensaje = "HAS PERDIDO :(";
            descripcion = "Te has quedado sin intentos";
            MensajeFinal = new Texto(engine, width,height,new Vector2(width/2,200), fontMensaje, mensaje, Color.NEGRO);
        }
        MensajeFinal.centrar();

        MensajeDescripcion = new Texto(engine,width,height, new Vector2(width/2,350), fontDescripcion, descripcion, Color.NEGRO);
        MensajeDescripcion.centrar();
        NumeroIntentos = new Texto(engine, width,height,new Vector2(width/2,500), fontIntentos, intentos, Color.NEGRO);
        NumeroIntentos.centrar();
        Codigo = new Texto(engine,width,height, new Vector2(width/2,600), fontDescripcion, "código:", Color.NEGRO);
        Codigo.centrar();


        setCircles(engine);

    }

    private void setCircles(Engine engine) {
        this.circles = new Circulo[NUM_CASILLAS];

        this.fuente = graph.newFont("Nexa.ttf", 50, false, false);

        int totalWidth = NUM_CASILLAS * RADIO_CIRCULO * 2;
        int spaceToEachSide = (width - totalWidth) / 2;


        for(int i = 0; i < combinacion_ganadora.length; i++){
            int x = spaceToEachSide + i * (RADIO_CIRCULO * 2);


            circles[i] = new Circulo(engine, width, height,
                    new Vector2(x,800), combinacion_ganadora[i].getId(), fuente);

            circles[i].daltonismo(daltonicos);
            circles[i].setColor(combinacion_ganadora[i]);
            circles[i].descubrir(true);
        }
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
        botonRepetir.render();
        botonVolverMenu.render();
        MensajeFinal.render();
        MensajeDescripcion.render();
        NumeroIntentos.render();
        Codigo.render();

        for (int i = 0; i < NUM_CASILLAS; i++) {
            circles[i].render();
        }

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        //Recorremos el array de eventos mandandolos al tablero
        for(int i = 0; i < events.size(); i++){
            if(botonRepetir.handleInput(events.get(i)))
            {
                engine.setScene(new MasterMind(modo));
            }
            if(botonVolverMenu.handleInput(events.get(i)))
            {
                engine.setScene(new MenuDificultad());
            }
        }
    }

}
