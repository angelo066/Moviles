package com.practica1.androidgame;

/**
 * Clase que representa un objeto code de la tienda
 */
public class CodeInfo {
    private String thumbnail;
    private String code;

    /**
     * @param thumbnail Path del thumbnail del code
     * @param code      Path del code
     */
    public CodeInfo(String thumbnail, String code) {
        this.thumbnail = thumbnail;
        this.code = code;
    }

    /**
     * @return Path del thumbnail del code
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @return Path del code
     */
    public String getCode() {
        return code;
    }

    /**
     * Establece el path del thumbnail del code
     *
     * @param thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @param code Establece el path del code
     */
    public void setCode(String code) {
        this.code = code;
    }
}
