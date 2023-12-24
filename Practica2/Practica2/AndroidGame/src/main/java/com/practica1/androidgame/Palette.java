package com.practica1.androidgame;

/**
 * Clase que representa un objeto palette de la tienda
 */
public class Palette {

    private String thumbnail;
    private int backgroundColor;
    private int color1;
    private int color2;
    private int color3;

    /**
     * @param thumbnail       Path del thumbnail de la palette
     * @param backgroundColor Color de fondo
     * @param color1          Color 1
     * @param color2          Color 2
     * @param color3          Color 3
     */
    public Palette(String thumbnail, int backgroundColor, int color1, int color2, int color3) {
        this.thumbnail = thumbnail;
        this.backgroundColor = backgroundColor;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }

    /**
     * @return Path del thumbnail de la palette
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @return El color de fondo
     */
    public int getColorBackground() {
        return backgroundColor;
    }

    /**
     * @return El color 1
     */
    public int getColor1() {
        return color1;
    }

    /**
     * @return El color 2
     */
    public int getColor2() {
        return color2;
    }

    /**
     * @return El color 3
     */
    public int getColor3() {
        return color3;
    }

    /**
     * Establece el thumbnail de la palette
     *
     * @param thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Establece el color de fondo
     *
     * @param backgroundColor
     */
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Establece el color 1
     *
     * @param color1
     */
    public void setColor1(int color1) {
        this.color1 = color1;
    }

    /**
     * Establece el color 2
     *
     * @param color2
     */
    public void setColor2(int color2) {
        this.color2 = color2;
    }

    /**
     * Establece el color 3
     *
     * @param color3
     */
    public void setColor3(int color3) {
        this.color3 = color3;
    }
}
