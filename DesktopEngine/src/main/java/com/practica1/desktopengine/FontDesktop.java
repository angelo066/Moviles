package com.practica1.desktopengine;

import com.practica1.engine.Font;

import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FontDesktop implements Font {

    private int size = 50;
    private java.awt.Font font;
    public FontDesktop(String filename, int size, boolean bold, boolean italic)
    {
        InputStream is = null;
        java.awt.Font newFont = null;
        try{
            is = new FileInputStream(filename);
            newFont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, is);
            newFont = newFont.deriveFont((float)size);
            if(bold)
                newFont = newFont.deriveFont(java.awt.Font.BOLD, (float)size);
            else if(italic)
                newFont = newFont.deriveFont(java.awt.Font.ITALIC, (float)size);
        }
        catch(FontFormatException | IOException e)
        {
            throw new RuntimeException("ERROR AL CARGAR FUENTE");
        }

        font = newFont;
    }

    public java.awt.Font getFont(){
        return font;
    }

    public void setSize(int f){size = f;}
    public int getSize(){return size;}
}
