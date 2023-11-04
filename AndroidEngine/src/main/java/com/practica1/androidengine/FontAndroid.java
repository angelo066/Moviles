package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.practica1.engine.Font;

/**
 * Clase que envuelve una fuente en android
 */
public class FontAndroid implements Font {
    private Typeface font;

    private int size = 50;

    /**
     * @param filename     Nombre del archivo
     * @param assetManager AssetsManager en android
     * @param size         Tamanio de la fuente
     * @param bold         Bold?
     * @param italic       Italic?
     */
    public FontAndroid(String filename, AssetManager assetManager, int size, boolean bold, boolean italic) {
        Typeface newFont = Typeface.createFromAsset(assetManager, filename);

        int flags = 0;
        if (bold) flags += Typeface.BOLD;
        if (italic) flags += Typeface.ITALIC;

        font = Typeface.create(newFont, flags);
        this.size = size;

    }

    /**
     * @return La fuente envuelta
     */
    public Typeface getFont() {
        return font;
    }

    @Override
    public void setSize(int newSize) {
        size = newSize;
    }

    @Override
    public int getSize() {
        return size;
    }
}
