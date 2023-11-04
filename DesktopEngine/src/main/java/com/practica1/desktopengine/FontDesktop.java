package com.practica1.desktopengine;

import com.practica1.engine.Font;

import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que envuelve una fuente en desktop
 */
public class FontDesktop implements Font {
    private java.awt.Font font;

    /**
     * @param filename Nombre del archivo
     * @param size     Tamanio de la fuente
     * @param bold     Bold?
     * @param italic   Italic?
     */
    public FontDesktop(String filename, int size, boolean bold, boolean italic) {
        InputStream is = null;
        java.awt.Font newFont = null;
        try {
            is = new FileInputStream(filename);

            newFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);

            int flags = 0;
            if (bold) flags += java.awt.Font.BOLD;
            if (italic) flags += java.awt.Font.ITALIC;

            newFont = newFont.deriveFont(flags, (float) size);

        } catch (FontFormatException | IOException e) {
            throw new RuntimeException("ERROR AL CARGAR FUENTE");
        }

        font = newFont;
    }

    /**
     * @return La fuente envuelta
     */
    public java.awt.Font getFont() {
        return font;
    }

    @Override
    public void setSize(int newSize) {
        font = font.deriveFont((float) newSize);
    }

    @Override
    public int getSize() {
        return font.getSize();
    }

}
