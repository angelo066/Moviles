package com.practica1.desktopengine;

import com.practica1.engine.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageDesktop implements Image {

    public ImageDesktop(String filename)
    {
        java.awt.Image image = null;
        try{
            image = ImageIO.read(new File(filename));
        }
        catch (IOException e)
        {
            throw new RuntimeException("ERROR AL CARGAR IMAGEN POR DESKTOP");
        }
        this.image = image;
    }
    private java.awt.Image image;
    @Override
    public int getWidth() {
        return image.getWidth(null);
    }

    @Override
    public int getHeight() {
        return image.getWidth(null);
    }

    java.awt.Image getImage(){return image;};
}
