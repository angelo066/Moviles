package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.GameObject;
import com.practica1.engine.Graphics;

import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;


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
    MARRON(0xFF804000);

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
    colores[] combinacion;
    int aciertos_pos = 0;
    int aciertos_color = 0;
}


public class Tablero extends GameObject {
    // Esto no es una constante porque nos va a indicar el numero de colores de cada modo
    private int N_COLORS;
    //private[] int colorValues;
    private IntentoFila[] tablero;
    private colores[] combinacion_ganadora;
    private Random rand;

    private int num_casillas;
    private int num_intentos;

    private int INTENTO_ACTUAL = 0;
    private boolean repetion = false;

    //Tamaño de los circulos
    private final int circleRad = 20;
    //Espacio entre circulos
    private final int circleSpace = 10;


    public Tablero(Engine e) {
        super(e);
    }

    @Override
    public void init() {

    }

    @Override
    public void render() {
        int h = engine.getGraphics().getSceneHeight();
        int w = engine.getGraphics().getSceneWidth();


        for (int i = 0; i < N_COLORS; i++) {
            colores color = colores.values()[i + 1]; //Conversion del indice al color
            engine.getGraphics().setColor(color.getValue());   //Cogemos el valor del color
            engine.getGraphics().fillCircle(circleRad + (circleRad * 2 * i) + (circleSpace * 4 * i), h - circleRad * 2, circleRad);
        }

        for (int i = 0; i < num_intentos; i++) {
            for (int j = 0; j < num_casillas; j++) {
                engine.getGraphics().setColor(colores.AZUL.getValue());
                engine.getGraphics().fillCircle(circleRad + (circleRad * 2 * j) + (circleSpace * 4 * j), h - 200 - circleRad * 4 * i, circleRad);
            }
        }
    }

    @Override
    public void update(double deltaTime) {

    }

    public void initTablero() {
        // Asignamos la dificultad
        configuracion(Dificultad.FACIL);

        // Rellenamos el tablero
        tablero = new IntentoFila[num_intentos];
        for (int i = 0; i < tablero.length; i++) {
            tablero[i].combinacion = new colores[num_casillas];
            for (int j = 0; j < num_casillas; j++) {
                tablero[i].combinacion[i] = colores.NO_COLOR;
            }
        }


        // Movidas
        combinacionConRep();
        combinacionJugador();
        checkIntento();
    }

    private void combinacionConRep() {
        for (int i = 0; i < num_casillas; i++) {
            int index = rand.nextInt((N_COLORS - 0) + 1) + 0;

            colores c = colores.values()[index];

            combinacion_ganadora[i] = colores.values()[index];
        }
    }

    private void combinacionSinRep(int cas) {
        // Creamos una lista con todos los colores disponibles
        ArrayList<colores> coloresAux = new ArrayList<>();
        for (int i = 0; i < N_COLORS; i++)
            coloresAux.add(colores.values()[i]);

        // Cogemos colores aleatorios de la lista
        for (int i = 0; i < num_casillas; i++) {
            // Cogemos un color aleatorio de la lista y lo quitamos
            int index = rand.nextInt((coloresAux.size() - 0) + 1) + 0;
            colores c = coloresAux.get(index);
            coloresAux.remove(index);

            // Asignamos el color
            combinacion_ganadora[i] = c;
        }
    }

    // Provisional para rellenar los colores
    private void combinacionJugador() {
        // Volcamos los colores disponibles a una lista y cuando elige un color lo sacamos de la lista
        ArrayList<colores> coloresAux = new ArrayList<>();
        for (int i = 0; i < N_COLORS; i++)
            coloresAux.add(colores.values()[i]);

        // Ahora ponemos los colores aleatorios
        for (int i = 0; i < num_casillas; i++) {
            // Cogemos un color aleatorio de la lista y lo quitamos
            int index = rand.nextInt((coloresAux.size() - 0) + 1) + 0;
            colores c = coloresAux.get(index);
            coloresAux.remove(index);

            // Asignamoos el color a la fila del tablero
            tablero[INTENTO_ACTUAL].combinacion[i] = c;
        }
    }

    public boolean checkIntento() {
        boolean win = false;

        // Volcamos la combinacion ganadora en una lista Auxiliar
        ArrayList<colores> colores_elegidos = new ArrayList<>();
        for (int i = 0; i < num_casillas; i++) {
            colores_elegidos.add(combinacion_ganadora[i]);
        }

        // Tenemos que comprobar la fila del tablero que coincida con el intento actual
        for (int i = 0; i < num_casillas; i++) {
            // Comprobar posicion
            if (tablero[INTENTO_ACTUAL].combinacion[i] == combinacion_ganadora[i])
                tablero[INTENTO_ACTUAL].aciertos_pos++;

            // Comprobar colores -> para cada elemento de la comb del jugador, recorremos la lista hasta encontrar el color que buscamos
            int j = 0;
            boolean encontrado = false;
            while (j < colores_elegidos.size() && !encontrado) {
                if (tablero[INTENTO_ACTUAL].combinacion[i] == colores_elegidos.get(j)) {
                    // Eliminamos el color de la lista
                    colores_elegidos.remove(j);
                    tablero[INTENTO_ACTUAL].aciertos_color++;
                    encontrado = true;
                }
            }

        }

        // Si ha acertado todas ha ganao
        if (tablero[INTENTO_ACTUAL].aciertos_pos == num_casillas && tablero[INTENTO_ACTUAL].aciertos_color == num_casillas)
            win = true;

        return win;
    }

    public void configuracion(Dificultad modo) {
        switch (modo) {
            case FACIL:
                N_COLORS = 4;
                num_casillas = 4;
                num_intentos = 6;
                repetion = false;
                break;
            case MEDIO:
                N_COLORS = 6;
                num_casillas = 4;
                num_intentos = 8;
                repetion = false;
                break;
            case DIFICIL:
                N_COLORS = 8;
                num_casillas = 5;
                num_intentos = 10;
                repetion = true;
                break;
            case IMPOSIBLE:
                N_COLORS = 9;
                num_casillas = 6;
                num_intentos = 10;
                repetion = true;
                break;

        }
    }

    public int getN_COLORS() {
        return N_COLORS;
    }

    public int getNum_casillas() {
        return num_casillas;
    }

    public int getNum_intentos() {
        return num_intentos;
    }


}
