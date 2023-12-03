package com.practica1.androidgame;

import java.io.Serializable;

public class PlayerSerializeInfo implements Serializable {

    private int coins;
    private int num_worlds;
    private int unlock_levels;
    private int complete_levels;


    public PlayerSerializeInfo(int coins, int num_worlds, int unlock_levels, int complete_levels)
    {
        this.coins = coins;
        this.num_worlds = num_worlds;
        this.unlock_levels = unlock_levels;
        this.complete_levels = complete_levels;
    }

    /*
    // GETTERS
    public int getCoins() {
        return coins;
    }
    public int getUnlockLevels() {
        return unlock_levels;
    }
    public int getCompleteLevels() {
        return complete_levels;
    }

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
        System.out.println(coins + " " + num_worlds + " " + unlock_levels + " " + complete_levels);
    }
}
