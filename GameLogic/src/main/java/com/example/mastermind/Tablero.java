package com.example.mastermind;

import java.util.Random;
import java.util.ArrayList;

public class Tablero {
    private final int N_COLORS = 9;
    public enum colores {ROJO,
    NARANJA,
    AMARILLO,
    VERDE,
    CYAN,
    AZUL,
    MORADO,
    ROSA,
    MARRON
    }
    boolean[] casillas;
    colores [] combinacion;
    Random rand;

    //Metodo al que llamar al elegir la dificultad para inicializar el tablero con X casillas
    public void initTablero(int cas){
        casillas = new boolean[cas];
        combinacion = new colores[cas]; //Esto hay que leerlo para mostrar los colores de la combinacion
        rand = new Random();


        combinacionSinRep(cas);
        combinacionConRep(cas);
    }

    private void combinacionConRep(int cas) {
        for(int i = 0; i< cas; i++){

            int numeroAleatorio = rand.nextInt((N_COLORS - 0) + 1) + 0;

            colores c = colores.values()[numeroAleatorio];

            combinacion[i] = colores.values()[numeroAleatorio];
        }
    }

    private void combinacionSinRep(int cas) {

        ArrayList<colores> coloresAux = new ArrayList<>();

        //Array con todoos los colores
        for(int i = 0; i < N_COLORS; i++){
            coloresAux.add(colores.values()[i]);
        }

        for(int i = 0; i< cas; i++){

            int numeroAleatorio = rand.nextInt((coloresAux.size() - 0) + 1) + 0;

            colores c = coloresAux.get(numeroAleatorio);

            combinacion[i] = c;

            coloresAux.remove(numeroAleatorio);
        }
    }

    public boolean checkTablero(){
        boolean win = false;



        return win;
    }
}
