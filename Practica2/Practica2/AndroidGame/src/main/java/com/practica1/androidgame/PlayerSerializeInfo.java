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

    private boolean[] unlocked_Backgrounds;
    private boolean[] unlocked_Codes;
    private boolean[] unlocked_Palettes;

    public PlayerSerializeInfo(int coins, int unlock_world ,int unlock_levels, int backgroundSkin, int codeSkin, Palette palette,
                               boolean[] unlocked_Backgrounds, boolean[] unlocked_Codes, boolean[] unlocked_Palettes)
    {
        this.coins = coins;
        this.unlock_world = unlock_world;
        this.unlock_levels = unlock_levels;
        this.backgroundSkin= backgroundSkin;
        this.codeSkin = codeSkin;
        this.paleteSkin = palette;
        this.unlocked_Backgrounds = unlocked_Backgrounds;
        this.unlocked_Codes = unlocked_Codes;
        this.unlocked_Palettes = unlocked_Palettes;
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

    public boolean[] getUnlocked_Backgrounds() {
        return unlocked_Backgrounds;
    }

    public boolean[] getUnlocked_Codes() {
        return unlocked_Codes;
    }

    public boolean[] getUnlocked_Palettes() {
        return unlocked_Palettes;
    }
}
