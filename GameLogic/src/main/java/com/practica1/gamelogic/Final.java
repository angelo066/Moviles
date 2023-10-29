package com.practica1.gamelogic;

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
    private BotonEscena botonRepetir;
    private BotonEscena botonVolverMenu;
    private Texto MensajeFinal;
    private Texto MensajeDescripcion;
    private Texto Codigo;
    private Texto NumeroIntentos;
    private Circulo[] combinacion_ganadora;
    int numIntentos = 0;

    boolean HaGanado = false;
    int NUM_CASILLAS = 5;
    int RADIO_CIRCULO = 30;

    public Final(Circulo[] combinacion, boolean HaGanado)
    {
        super();

        /*int NUM_CASILLAS = combinacion.length;
        combinacion_ganadora =  new Circulo[NUM_CASILLAS];
        for (int i = 0; i < NUM_CASILLAS; i++) {
            combinacion_ganadora[i] = new Circulo(engine);
            combinacion_ganadora[i].setColor(combinacion[i].getColor());
        }*/

        combinacion_ganadora =  new Circulo[NUM_CASILLAS];
        for (int i = 0; i < NUM_CASILLAS; i++) {
            combinacion_ganadora[i] = new Circulo(engine);
            combinacion_ganadora[i].setColor(colores.values()[i+1]);
        }
        setCirclePositions();

        this.HaGanado = HaGanado;
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
        botonRepetir = new BotonEscena(engine, posRep, new Vector2(size), 70, fontBoton,"Volver a jugar", colores.CYAN, colores.NEGRO);
        botonRepetir.centrar();

        Vector2 posVolver = new Vector2(width/2, height/2 + 450);
        botonVolverMenu = new BotonEscena(engine, posVolver, new Vector2(size), 70, fontBoton,"Elegir Dificultad", colores.CYAN, colores.NEGRO);
        botonVolverMenu.centrar();

        String mensaje ="";
        String descripcion = "";
        String intentos = "";
        if(HaGanado)
        {
            mensaje = "ENHORABUENA!!";
            descripcion = "Has averiguado el codigo en";
            intentos = numIntentos + " intentos";
            MensajeFinal = new Texto(engine, new Vector2(width/2,200), fontMensaje, mensaje, colores.NEGRO);
        }
        else
        {
            mensaje = "HAS PERDIDO :(";
            descripcion = "Te has quedado sin intentos";
            MensajeFinal = new Texto(engine, new Vector2(width/2,200), fontMensaje, mensaje, colores.NEGRO);
        }
        MensajeFinal.centrar();

        MensajeDescripcion = new Texto(engine, new Vector2(width/2,350), fontDescripcion, descripcion, colores.NEGRO);
        MensajeDescripcion.centrar();
        NumeroIntentos = new Texto(engine, new Vector2(width/2,500), fontIntentos, intentos, colores.NEGRO);
        NumeroIntentos.centrar();
        Codigo = new Texto(engine, new Vector2(width/2,600), fontDescripcion, "código:", colores.NEGRO);
        Codigo.centrar();

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
        botonRepetir.render();
        botonVolverMenu.render();
        MensajeFinal.render();
        MensajeDescripcion.render();
        NumeroIntentos.render();
        Codigo.render();

        for (int i = 0; i < NUM_CASILLAS; i++) {
            combinacion_ganadora[i].render();
        }

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        //Recorremos el array de eventos mandandolos al tablero
        for(int i = 0; i < events.size(); i++){

        }
    }

    private void setCirclePositions()
    {
        int w = engine.getGraphics().getWidth();
        int totalWidth = NUM_CASILLAS * RADIO_CIRCULO*2;
        int spaceToEachSide = (w - totalWidth) / 2;

        // Rectangulo de fondo
        engine.getGraphics().setColor(colores.GRIS.getValue());

        // Crear las bolas y establecer sus posiciones
        for (int i = 0; i < NUM_CASILLAS; i++) {
            int x = spaceToEachSide + i * (RADIO_CIRCULO*2);
            combinacion_ganadora[i].pos.x = x;
            combinacion_ganadora[i].pos.y = 800;
            combinacion_ganadora[i].descubrir(true);
            combinacion_ganadora[i].render();
        }
    }
}
