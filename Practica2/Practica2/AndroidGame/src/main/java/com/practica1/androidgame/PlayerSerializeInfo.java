package com.practica1.androidgame;

import android.util.Pair;

import java.io.Serializable;

public class PlayerSerializeInfo implements Serializable {

    private int coins; // Dinero
    private int unlock_levels; // Numero de niveles desbloqueados // van en orden por mundos
    private int unlock_world; // Numero de niveles desbloqueados // van en orden por mundos

    private int backgroundSkin;
    private int codeSkin;
    private Palette paleteSkin;

    public PlayerSerializeInfo(int coins, int unlock_world ,int unlock_levels, int backgroundSkin, int codeSkin, Palette palette)
    {
        this.coins = coins;
        this.unlock_world = unlock_world;
        this.unlock_levels = unlock_levels;
        this.backgroundSkin= backgroundSkin;
        this.codeSkin = codeSkin;
        this.paleteSkin = palette;
    }

    // GETTERS
    public int getCoins() {
        return coins;
    }
    public int getUnlockLevels() {
        return unlock_levels;
    }

    public void print()
    {
        System.out.println(coins + " " + unlock_levels);
    }

    public int getBackgroundSkin() {
        return backgroundSkin;
    }

    public int getCodeSkin() {
        return codeSkin;
    }

    public Palette getPaleteSkin() {
        return paleteSkin;
    }

    public int getUnlock_world() {
        return unlock_world;
    }
}
