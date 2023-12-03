package com.practica1.desktopengine;

import com.practica1.engine.Image;

import java.io.File;

import javax.imageio.ImageIO;

/**
 * Clase que envuelve una imagen en la aplicacion en desktop
 */
public class ImageDesktop implements Image {

    private java.awt.Image image;

    /**
     * @param filename Nombre del archivo
     */
    public ImageDesktop(String filename) {
        java.awt.Image newImage = null;
        try {
            newImage = ImageIO.read(new File(filename));
        } catch (Exception e) {
            throw new RuntimeException("ERROR AL CARGAR IMAGEN POR DESKTOP");
        }
        image = newImage;
    }

    @Override
    public int getWidth() {
        return image.getWidth(null);
    }

    @Override
    public int getHeight() {
        return image.getWidth(null);
    }

    /**
     * @return La imagen envuelta
     */
    java.awt.Image getImage() {
        return image;
    }

}
