package com.practica1.androidgame;

/**
 * Clase que contiene la informacion de un nivel
 */
public class LevelInfo {

    private int codeSize;
    private int codeOpt;
    private boolean repeat;
    private int attempts;

    /**
     * @param codeSize Tamanyo del codigo
     * @param codeOpt  Tamanyo de los colores disponibles
     * @param repeat   Si se puede repetir color
     * @param attempts Numero de intentos
     */
    public LevelInfo(int codeSize, int codeOpt, boolean repeat, int attempts) {
        this.codeSize = codeSize;
        this.codeOpt = codeOpt;
        this.repeat = repeat;
        this.attempts = attempts;
    }

    /**
     * @return Tamanyo de los colores disponibles
     */
    public int getCodeOpt() {
        return codeOpt;
    }

    /**
     * @return Si se puede repetir color
     */
    public boolean getRepeat() {
        return repeat;
    }

    /**
     * @return Numero de intentos
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * @return Tamanyo del codigo
     */
    public int getCodeSize() {
        return codeSize;
    }

    /**
     * Establece el tamanyo del codigo
     *
     * @param codeSize
     */
    public void setCodeSize(int codeSize) {
        this.codeSize = codeSize;
    }

    /**
     * Establece el tamanyo de colores disponibles
     *
     * @param codeOpt
     */
    public void setCodeOpt(int codeOpt) {
        this.codeOpt = codeOpt;
    }

    /**
     * Establece si se pueden repetir colores
     *
     * @param repeat
     */
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    /**
     * Establece el numero de intentos
     *
     * @param attempts
     */
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
