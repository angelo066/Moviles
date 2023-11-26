package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Clase que envuelve una fuente en android
 */
public class Font {
    private Typeface font;

    private int size = 50;

    /**
     * @param filename     Nombre del archivo
     * @param assetManager AssetsManager en android
     * @param size         Tamanio de la fuente
     * @param bold         Bold?
     * @param italic       Italic?
     */
    public Font(String filename, AssetManager assetManager, int size, boolean bold, boolean italic) {
        try {
            Typeface newFont = Typeface.createFromAsset(assetManager, filename);

            int flags = 0;
            if (bold) flags += Typeface.BOLD;
            if (italic) flags += Typeface.ITALIC;

            font = Typeface.create(newFont, flags);
        }
        catch (Exception e ){
            throw new RuntimeException("ERROR AL CARGAR FUENTE EN ANDROID");
        }

        this.size = size;
    }

    /**
     * @return La fuente envuelta
     */
    public Typeface getFont() {
        return font;
    }


    public void setSize(int newSize) {
        size = newSize;
    }


    public int getSize() {
        return size;
    }


    public void setBold(boolean bold) {
        int flags = 0;
        if (bold) flags += Typeface.BOLD;
        if (isItalic()) flags += Typeface.ITALIC;

        font = Typeface.create(font, flags);
    }


    public boolean isBold() {
        return font.isBold();
    }


    public void setItalic(boolean italic) {
        int flags = 0;
        if (italic) flags += Typeface.ITALIC;
        if (isBold()) flags += Typeface.BOLD;

        font = Typeface.create(font, flags);
    }

    public boolean isItalic() {
        return font.isItalic();
    }
}
