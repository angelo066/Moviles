package com.practica1.androidgame;

public class WordlInfo {
    private String world_background;
    private String gameplay_background;
    private String pack;

    public WordlInfo(String world_background)
    {
        this.world_background = world_background;
    }

    // GETTERS
    public String getWorld_background() {
        return world_background;
    }
    public String getGameplay_background() {
        return gameplay_background;
    }
    public String getPack() {
        return pack;
    }

    // SETTERS
    public void setWorld_background(String world_background) {
        this.world_background = world_background;
    }
    public void setGameplay_background(String gameplay_background) {
        this.gameplay_background = gameplay_background;
    }
    public void setPack(String pack) {
        this.pack = pack;
    }
}
