package com.practica1.androidgame;

/**
 * Clase que representa un objeto background de la tienda
 */
public class BackgroundInfo {
    private String thumbnail;
    private String background;

    /**
     * @param thumbnail  Path del thumbnail del background
     * @param background Path del background
     */
    public BackgroundInfo(String thumbnail, String background) {
        this.thumbnail = thumbnail;
        this.background = background;
    }

    /**
     * @return Path del thumbnail del background
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @return Path del background
     */
    public String getBackground() {
        return background;
    }

    /**
     * Establece el path del thumbnail del background
     *
     * @param thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Establece el path del background
     *
     * @param background
     */
    public void setBackground(String background) {
        this.background = background;
    }
}
