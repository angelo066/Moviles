package com.practica1.androidgame;

/**
 * Clase que representa la informacion de un mundo
 */
public class WordlInfo {
    private String worldBackground;
    private String gameplayBackground;
    private String pack;

    /**
     * @param worldBackground Path del fondo del mundo
     */
    public WordlInfo(String worldBackground) {
        this.worldBackground = worldBackground;
    }

    /**
     * @return Path del fondo del mundo
     */
    public String getWorldBackground() {
        return worldBackground;
    }

    /**
     * @return Path del fondo de los niveles del mundo
     */
    public String getGameplayBackground() {
        return gameplayBackground;
    }

    /**
     * @return Path del pack del mundo
     */
    public String getPack() {
        return pack;
    }

    /**
     * Establece Path del fondo del mundo
     *
     * @param worldBackground
     */
    public void setWorldBackground(String worldBackground) {
        this.worldBackground = worldBackground;
    }

    /**
     * Establece Path del fondo de los niveles del mundo
     *
     * @param gameplayBackground
     */
    public void setGameplayBackground(String gameplayBackground) {
        this.gameplayBackground = gameplayBackground;
    }

    /**
     * Establece Path del pack del mundo
     *
     * @param pack
     */
    public void setPack(String pack) {
        this.pack = pack;
    }
}
