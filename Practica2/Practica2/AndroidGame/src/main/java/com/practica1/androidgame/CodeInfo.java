package com.practica1.androidgame;

public class CodeInfo {
    private String thumbnail;
    private String code;

    public CodeInfo(String thumbnail, String background)
    {
        this.thumbnail = thumbnail;
        this.code = background;
    }

    // GETTERS
    public String getThumbnail() {
        return thumbnail;
    }
    public String getCode() { return code; }

    // SETTERS
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public void setCode(String code) { this.code = code; }
}
