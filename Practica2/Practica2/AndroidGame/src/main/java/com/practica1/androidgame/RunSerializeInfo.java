package com.practica1.androidgame;

import com.practica1.androidengine.Color;

import java.io.Serializable;
import java.util.ArrayList;

/*
* Clase que guarda el estado de una partida
 */
public class RunSerializeInfo implements Serializable {

    // Si es un nivel de mundo, cargamos el nivel directamente
    private int world;
    private int level;

    // Si no lo es lo cargamos con la dificultad
    private Difficulty difficulty;

    private Color[] winningCombination; // Codigo a adivinar
    private Circle[] availableColors; // Colores disponibles
    private ArrayList<Attempt> attempts; // La lista de intentos

    private int currentAttempt;
    private boolean colorBlind;

    public RunSerializeInfo()
    {

    }

}
