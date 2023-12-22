package com.practica1.androidgame;

public class LevelInfo {

    private int codeSize;
    private int codeOpt;
    private boolean repeat;
    private int attempts;

    public LevelInfo(int codeSize, int codeOpt, boolean repeat, int attempts)
    {
        this.codeSize = codeSize;
        this.codeOpt = codeOpt;
        this.repeat = repeat;
        this.attempts = attempts;
    }

    // GETTERS
    public int getCodeOpt() {
        return codeOpt;
    }
    public boolean getRepeat() {
        return repeat;
    }
    public int getAttempts() {
        return attempts;
    }
    public int getCodeSize() {
        return codeSize;
    }

    // SETTERS
    public void setCodeSize(int codeSize) {
        this.codeSize = codeSize;
    }
    public void setCodeOpt(int codeOpt) {
        this.codeOpt = codeOpt;
    }
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
