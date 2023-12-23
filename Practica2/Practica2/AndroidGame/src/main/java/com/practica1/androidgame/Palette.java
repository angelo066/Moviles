package com.practica1.androidgame;

import java.io.Serializable;

public class Palette implements Serializable {

    private String thumbnail;
    private int color_background;
    private int color_1;
    private int color_2;
    private int color_3;

    public Palette(String thumbnail, int color_background, int color_1, int color_2, int color_3)
    {
        this.thumbnail = thumbnail;
        this.color_background = color_background;
        this.color_1 = color_1;
        this.color_2 = color_2;
        this.color_3 = color_3;
    }

    // GETTERS
    public String getThumbnail() {return thumbnail;}
    public int color_background() { return color_background; }
    public int getColor_1() { return color_1; }
    public int getColor_2() { return color_2; }
    public int getColor_3() { return color_3; }

    // SETTERS
    public void setThumbnail(String thumbnail) {this.thumbnail = thumbnail;}
    public void setBackground_Color(int color_background) { this.color_background = color_background; }
    public void setColor_1(int color_1) { this.color_1 = color_1; }
    public void setColor_2(int color_2) { this.color_2 = color_2; }
    public void setColor_3(int color_3) { this.color_3 = color_3; }
}
