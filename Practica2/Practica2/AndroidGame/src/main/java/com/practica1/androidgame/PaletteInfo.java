package com.practica1.androidgame;

public class PaletteInfo {
    private String thumbnail;
    private String background_color;
    private String color_1;
    private String color_2;
    private String color_3;

    public PaletteInfo(String thumbnail, String background_color, String color_1, String color_2, String color_3)
    {
        this.thumbnail = thumbnail;
        this.background_color = background_color;
        this.color_1 = color_1;
        this.color_2 = color_2;
        this.color_3 = color_3;
    }

    // GETTERS
    public String getThumbnail() {
        return thumbnail;
    }
    public String getColor_Background() { return background_color; }
    public String getColor_1() { return color_1; }
    public String getColor_2() { return color_2; }
    public String getColor_3() { return color_3; }

    // SETTERS
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public void setBackground_Color(String background_color) { this.background_color = background_color; }
    public void setColor_1(String color_1) { this.color_1 = color_1; }
    public void setColor_2(String color_2) { this.color_2 = color_2; }
    public void setColor_3(String color_3) { this.color_3 = color_3; }
}
