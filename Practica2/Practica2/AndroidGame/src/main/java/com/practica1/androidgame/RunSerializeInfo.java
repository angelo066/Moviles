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

    public RunSerializeInfo(int world, int level, Difficulty difficulty, Color[] colors,
                            Circle[] circles, ArrayList<Attempt> attempts, int currentAttempt, boolean colorBlind)
    {
        this.world = world;
        this.level = level;
        this.difficulty = difficulty;
        this.winningCombination = colors;
        this.availableColors = circles;
        this.attempts = attempts;
        this.currentAttempt = currentAttempt;
        this.colorBlind = colorBlind;
    }

    public void setAttempts(ArrayList<Attempt> n){attempts = n;}
    public void setAvailableColors(Circle[] availableColors) {
        this.availableColors = availableColors;
    }

    public void setWorld(int world) {
        this.world = world;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setWinningCombination(Color[] winningCombination) {
        this.winningCombination = winningCombination;
    }

    public void setCurrentAttempt(int currentAttempt) {
        this.currentAttempt = currentAttempt;
    }

    public void setColorBlind(boolean colorBlind) {
        this.colorBlind = colorBlind;
    }

    public int getWorld() {
        return world;
    }

    public int getLevel() {
        return level;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Color[] getWinningCombination() {
        return winningCombination;
    }

    public Circle[] getAvailableColors() {
        return availableColors;
    }

    public ArrayList<Attempt> getAttempts() {
        return attempts;
    }

    public int getCurrentAttempt() {
        return currentAttempt;
    }

    public boolean isColorBlind() {
        return colorBlind;
    }
}
