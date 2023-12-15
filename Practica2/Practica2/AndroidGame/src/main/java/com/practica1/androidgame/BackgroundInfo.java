package com.practica1.androidgame;

public class BackgroundInfo {
    private String thumbnail;
    private String background;

    public BackgroundInfo(String thumbnail, String background)
    {
        this.thumbnail = thumbnail;
        this.background = background;
    }

    // GETTERS
    public String getThumbnail() {
        return thumbnail;
    }
    public String getBackground() { return background; }

    // SETTERS
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public void setBackground(String background) { this.background = background; }
}
