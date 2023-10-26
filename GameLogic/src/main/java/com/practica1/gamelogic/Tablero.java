package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

import java.util.Random;
import java.util.ArrayList;


// LOS COLORES A PARTIR DEL MARRON NO SE USAN PARA EL JUEGO
enum colores {
    NO_COLOR(0xFF333333),
    ROJO(0xFFFF0000),
    NARANJA(0xFFFF8000),
    AMARILLO(0xFFFFFF00),
    VERDE(0xFF00FF00),
    CYAN(0xFF00FFFF),
    AZUL(0XFF0000FF),
    MORADO(0xFFFF00FF),
    ROSA(0xFFFF0080),
    MARRON(0xFF804000),
    BLANCO(0xFFFFFFFF),
    GRIS(0xFFC6C6C6),
    GRIS_OSCURO(0xFF8C8C8C);

    private int value;

    private colores(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }
}

enum Dificultad {
    FACIL,
    MEDIO,
    DIFICIL,
    IMPOSIBLE
}

class IntentoFila {

    public IntentoFila(){};
    Circulo[] combinacion = null;
    int aciertos_pos = 0;
    int aciertos_color = 0;

    public void handleInput(TouchEvent touchEvent){

        for(int i=0; i< combinacion.length; i++){
            combinacion[i].handleInput(touchEvent);
        }
    }
}


public class Tablero extends GameObject {
    private int N_COLORS;
    private IntentoFila[] tablero;
    private Circulo[] combinacion_ganadora;
    private Circulo[] colores_elegidos;
    private Random rand;

    private int NUM_CASILLAS;
    private int NUM_INTENTOS;

    private int INTENTO_ACTUAL = 0;
    private boolean repetion = false;

    private Vector2[] pos_intentos;
    Vector2 pos_cabecera;
    Vector2 pos_colores;

    private final int N_DIVISIONES_PANTALLA = 12;
    private final int H_OFFSET = 20;
    private final int V_OFFSET = 40;
    private final int RADIO_CIRCULO = 50;

    private Circulo selectedCircle; //Circulo que elegimos al clickar
    public Tablero(Engine e) {
        super(e);

        pos_cabecera = new Vector2(0,0);
        pos_intentos = new Vector2[N_DIVISIONES_PANTALLA - 2];
        int height = engine.getGraphics().getHeight();
        int separacion = height / N_DIVISIONES_PANTALLA;
        for(int i = 0; i< N_DIVISIONES_PANTALLA - 2; i++)
        {
            pos_intentos[i] = new Vector2(0, (i+1) * separacion);
        }
        pos_colores = new Vector2(0, separacion * (N_DIVISIONES_PANTALLA-1));

        rand = new Random();
        initTablero();
    }

    @Override
    public void init() {

    }

    @Override
    public void render() {
        int h = engine.getGraphics().getSceneHeight();
        int w = engine.getGraphics().getSceneWidth();

        // Calcular el espacio total ocupado por las bolas y el espacio a cada lado
        int offset = 20;


        // Para cada intento
        for(int i = 0; i< NUM_INTENTOS; i++)
        {
            dibujaIntento(i);
        }

        // Los colores disponibles
        dibujaColoresDisponibles();


    }

    @Override
    public void update(double deltaTime) {

    }

    public void initTablero() {
        // Asignamos la dificultad
        configuracion(Dificultad.IMPOSIBLE);

        // Rellenamos el tablero
        tablero = new IntentoFila[NUM_INTENTOS];
        for (int i = 0; i < tablero.length; i++) {
            tablero[i] = new IntentoFila();
            tablero[i].combinacion = new Circulo[NUM_CASILLAS];
            for (int j = 0; j < NUM_CASILLAS; j++) {
                tablero[i].combinacion[j] = new Circulo(engine);
                tablero[i].combinacion[j].setColor(colores.NO_COLOR);
            }
        }

        // Creamos los circulos de los colores elegidos para la partida
        colores_elegidos = new Circulo[N_COLORS];
        for(int i = 0; i< N_COLORS; i++)
        {
            colores_elegidos[i] = new Circulo(engine);
            colores_elegidos[i].setColor(colores.values()[i+1]);
        }

        // Movidas
        combinacionConRep();
        combinacionJugador();
    }

    private void combinacionConRep() {
        combinacion_ganadora =  new Circulo[NUM_CASILLAS];
        for (int i = 0; i < NUM_CASILLAS; i++) {
            combinacion_ganadora[i] = new Circulo(engine);
            int index = rand.nextInt((N_COLORS - 0) + 1) + 0;

            colores c = colores.values()[index];

            combinacion_ganadora[i].setColor(colores.values()[index]);
        }
    }

    private void combinacionSinRep(int cas) {
        combinacion_ganadora =  new Circulo[NUM_CASILLAS];

        // Creamos una lista con todos los colores disponibles
        ArrayList<colores> coloresAux = new ArrayList<>();
        for (int i = 0; i < N_COLORS; i++)
            coloresAux.add(colores.values()[i]);

        // Cogemos colores aleatorios de la lista
        for (int i = 0; i < NUM_CASILLAS; i++) {
            combinacion_ganadora[i] = new Circulo(engine);
            // Cogemos un color aleatorio de la lista y lo quitamos
            int index = rand.nextInt((coloresAux.size() - 0) + 1) + 0;
            colores c = coloresAux.get(index);
            coloresAux.remove(index);

            // Asignamos el color
            combinacion_ganadora[i].setColor(c);
        }
    }

    // Provisional para rellenar los colores
    private void combinacionJugador() {
        // Volcamos los colores disponibles a una lista y cuando elige un color lo sacamos de la lista
        ArrayList<colores> coloresAux = new ArrayList<>();
        for (int i = 0; i < N_COLORS; i++)
            coloresAux.add(colores.values()[i]);

        // Ahora ponemos los colores aleatorios
        for (int i = 0; i < NUM_CASILLAS; i++) {
            // Cogemos un color aleatorio de la lista y lo quitamos
            //int index = rand.nextInt((coloresAux.size() - 0) + 1) + 0;
            int index = rand.nextInt(coloresAux.size());
            colores c = coloresAux.get(index);
            coloresAux.remove(index);

            // Asignamoos el color a la fila del tablero
            tablero[INTENTO_ACTUAL].combinacion[i].setColor(c);
        }
    }

    public boolean checkIntento() {
        boolean win = false;

        // Volcamos la combinacion ganadora en una lista Auxiliar
        ArrayList<colores> colores_elegidos = new ArrayList<>();
        for (int i = 0; i < NUM_CASILLAS; i++) {
            colores_elegidos.add(combinacion_ganadora[i].getColor());
        }

        // Tenemos que comprobar la fila del tablero que coincida con el intento actual
        for (int i = 0; i < NUM_CASILLAS; i++) {
            // Comprobar posicion
            if (tablero[INTENTO_ACTUAL].combinacion[i].getColor() == combinacion_ganadora[i].getColor())
                tablero[INTENTO_ACTUAL].aciertos_pos++;

            // Comprobar colores -> para cada elemento de la comb del jugador, recorremos la lista hasta encontrar el color que buscamos
            int j = 0;
            boolean encontrado = false;
            while (j < colores_elegidos.size() && !encontrado) {
                if (tablero[INTENTO_ACTUAL].combinacion[i].getColor() == colores_elegidos.get(j)) {
                    // Eliminamos el color de la lista
                    colores_elegidos.remove(j);
                    tablero[INTENTO_ACTUAL].aciertos_color++;
                    encontrado = true;
                }
            }

        }

        // Si ha acertado todas ha ganao
        if (tablero[INTENTO_ACTUAL].aciertos_pos == NUM_CASILLAS && tablero[INTENTO_ACTUAL].aciertos_color == NUM_CASILLAS)
            win = true;

        return win;
    }

    public void configuracion(Dificultad modo) {
        switch (modo) {
            case FACIL:
                N_COLORS = 4;
                NUM_CASILLAS = 4;
                NUM_INTENTOS = 6;
                repetion = false;
                break;
            case MEDIO:
                N_COLORS = 6;
                NUM_CASILLAS = 4;
                NUM_INTENTOS = 8;
                repetion = false;
                break;
            case DIFICIL:
                N_COLORS = 8;
                NUM_CASILLAS = 5;
                NUM_INTENTOS = 10;
                repetion = true;
                break;
            case IMPOSIBLE:
                N_COLORS = 9;
                NUM_CASILLAS = 6;
                NUM_INTENTOS = 10;
                repetion = true;
                break;

        }
    }

    public int getN_COLORS() {
        return N_COLORS;
    }

    public int getNUM_CASILLAS() {
        return NUM_CASILLAS;
    }

    public int getNUM_INTENTOS() {
        return NUM_INTENTOS;
    }

    public void handleInput(TouchEvent touchEvent){
        boolean selected = false;
        for(int i=0; i < colores_elegidos.length;i++){
            selected = colores_elegidos[i].handleInput(touchEvent);

            if(selected)
                selectedCircle = colores_elegidos[i];
        }
    }

    private void dibujaIntento(int i)
    {
        int w = engine.getGraphics().getWidth();
        int totalWidth = combinacion_ganadora.length * RADIO_CIRCULO*2;
        int spaceToEachSide = (w - totalWidth) / 2;

        // RECTANGULO
        engine.getGraphics().setColor(colores.GRIS.getValue());
        int y = pos_intentos[i].y - V_OFFSET / 2;
        engine.getGraphics().drawRoundRectangle(0,y,w,RADIO_CIRCULO*2 + V_OFFSET,50);

        // CIRCULOS
        for (int j = 0; j < NUM_CASILLAS; j++)
        {
            int x = spaceToEachSide + j * RADIO_CIRCULO*2;
            tablero[i].combinacion[j].pos.x = x;
            tablero[i].combinacion[j].pos.y = pos_intentos[i].y;
            tablero[i].combinacion[j].render();
        }

    }

    private void dibujaColoresDisponibles()
    {
        int w = engine.getGraphics().getWidth();
        int totalWidth = N_COLORS * RADIO_CIRCULO*2;
        int spaceToEachSide = (w - totalWidth) / 2;

        // Rectangulo de fondo
        engine.getGraphics().setColor(colores.GRIS.getValue());
        engine.getGraphics().fillRectangle(0, pos_colores.y - V_OFFSET/2, w, RADIO_CIRCULO*2+V_OFFSET);

        // Crear las bolas y establecer sus posiciones
        for (int i = 0; i < N_COLORS; i++) {
            int x = spaceToEachSide + i * (RADIO_CIRCULO*2);
            colores_elegidos[i].pos.x = x;
            colores_elegidos[i].pos.y = pos_colores.y;
            colores_elegidos[i].descubrir(true);
            colores_elegidos[i].render();

        }
    }


}
