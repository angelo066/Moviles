package com.practica1.desktopengine;

import com.practica1.engine.Font;

import java.io.FileInputStream;
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
        java.awt.Font newFont = null;
        try {
            InputStream is = new FileInputStream(filename);

            newFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);

            int flags = 0;
            if (bold) flags += java.awt.Font.BOLD;
            if (italic) flags += java.awt.Font.ITALIC;

            newFont = newFont.deriveFont(flags, (float) size);

            is.close();

        } catch (Exception e) {
            throw new RuntimeException("ERROR AL CARGAR FUENTE EN DESKTOP");
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

    @Override
    public void setBold(boolean bold) {
        int flags = 0;
        if (bold) flags += java.awt.Font.BOLD;
        if (isItalic()) flags += java.awt.Font.ITALIC;

        font = font.deriveFont(flags, (float) getSize());
    }

    @Override
    public boolean isBold() {
        return font.isBold();
    }

    @Override
    public void setItalic(boolean italic) {
        int flags = 0;
        if (italic) flags += java.awt.Font.ITALIC;
        if (isBold()) flags += java.awt.Font.BOLD;

        font = font.deriveFont(flags, (float) getSize());
    }

    @Override
    public boolean isItalic() {
        return font.isItalic();
    }

}
