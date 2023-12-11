package com.practica1.androidgame;

import java.io.Serializable;

public class PlayerSerializeInfo implements Serializable {

    private int coins; // Dinero
    private int unlock_levels; // Numero de niveles desbloqueados // van en orden por mundos


    public PlayerSerializeInfo(int coins, int unlock_levels)
    {
        this.coins = coins;
        this.unlock_levels = unlock_levels;
    }

    // GETTERS
    public int getCoins() {
        return coins;
    }
    public int getUnlockLevels() {
        return unlock_levels;
    }
    /*

    // SETTERS
    public void setCoins(int coins) {
        this.coins = coins;
    }
    public void setUnlockLevels(int unlock_levels) {
        this.unlock_levels = unlock_levels;
    }
    public void setCompletelevels(int complete_levels) {
        this.complete_levels = complete_levels;
    }
     */

    public void print()
    {
        System.out.println(coins + " " + unlock_levels);
    }
}
