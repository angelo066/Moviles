package com.example.mastermind;

import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;


enum colores {
    NO_COLOR(0x333333),
    ROJO(0xFF0000),
    NARANJA(0xFF8000),
    AMARILLO(0xFFFF00),
    VERDE(0x00FF00),
    CYAN(0x00FFFF),
    AZUL(0X0000FF),
    MORADO(0xFF00FF),
    ROSA(0xFF0080),
    MARRON(0x804000);

    private int value;

    private colores(int v){
        this.value = v;
    }

    public int getValue(){return value;}
}

enum Dificultad
{
    FACIL,
    MEDIO,
    DIFICIL,
    IMPOSIBLE
}

class IntentoFila
{
    colores[] combinacion;
    int aciertos = 0;
}


public class Tablero
{
    // Esto no es una constante porque nos va a indicar el numero de colores de cada modo
    private int N_COLORS;
    //private[] int colorValues;
    private IntentoFila[] tablero;
    private colores [] combinacion_ganadora;

    private Random rand;

    private int num_casillas;
    private int num_intentos;

    private int INTENTO_ACTUAL = 0;
    private boolean repetion = false;

    //Metodo al que llamar al elegir la dificultad para inicializar el tablero con X casillas

    public void initTablero()
    {
        // Asignamos la dificultad
        configuracion(Dificultad.FACIL);

        // Rellenamos el tablero
        tablero = new IntentoFila[num_intentos];
        for(int i = 0; i < tablero.length; i++)
        {
            tablero[i].combinacion = new colores[num_casillas];
            for(int j = 0; j < num_casillas; j++)
            {
                tablero[i].combinacion[i] = colores.NO_COLOR;
            }
        }

        // Movidas
        combinacionConRep();
        combinacionJugador();
        checkTablero();
    }

    private void combinacionConRep()
    {
        for(int i = 0; i < num_casillas; i++)
        {
            int index = rand.nextInt((N_COLORS - 0) + 1) + 0;

            colores c = colores.values()[index];

            combinacion_ganadora[i] = colores.values()[index];
        }
    }

    private void combinacionSinRep(int cas)
    {
        // Creamos una lista con todos los colores disponibles
        ArrayList<colores> coloresAux = new ArrayList<>();
        for(int i = 0; i < N_COLORS; i++)
            coloresAux.add(colores.values()[i]);

        // Cogemos colores aleatorios de la lista
        for(int i = 0; i< num_casillas; i++)
        {
            // Cogemos un color aleatorio de la lista y lo quitamos
            int index = rand.nextInt((coloresAux.size() - 0) + 1) + 0;
            colores c = coloresAux.get(index);
            coloresAux.remove(index);

            // Asignamos el color
            combinacion_ganadora[i] = c;
        }
    }

    // Provisional para rellenar los colores
    private void combinacionJugador()
    {
        // Volcamos los colores disponibles a una lista y cuando elige un color lo sacamos de la lista
        ArrayList<colores> coloresAux = new ArrayList<>();
        for(int i = 0; i < N_COLORS; i++)
            coloresAux.add(colores.values()[i]);

        // Ahora ponemos los colores aleatorios
        for(int i = 0; i< num_casillas; i++)
        {
            // Cogemos un color aleatorio de la lista y lo quitamos
            int index = rand.nextInt((coloresAux.size() - 0) + 1) + 0;
            colores c = coloresAux.get(index);
            coloresAux.remove(index);

            // Asignamoos el color a la fila del tablero
            tablero[INTENTO_ACTUAL].combinacion[i] = c;
        }
    }

    public boolean checkTablero()
    {
        boolean win = false;

        // Tenemos que comprobar la fila del tablero que coincida con el intento actual
        for(int i = 0; i < num_casillas; i++)
        {
            if(tablero[INTENTO_ACTUAL].combinacion[i] == combinacion_ganadora[i])
                tablero[INTENTO_ACTUAL].aciertos++;
        }

        // Si ha acertado todas ha ganao
        if(tablero[INTENTO_ACTUAL].aciertos == num_casillas)
            win = true;

        return win;
    }

    public void configuracion(Dificultad modo)
    {
        switch (modo){
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

    public int getN_COLORS(){return N_COLORS;}

    public int getNum_casillas(){return num_casillas;}
    public int getNum_intentos(){return num_intentos;}
}
