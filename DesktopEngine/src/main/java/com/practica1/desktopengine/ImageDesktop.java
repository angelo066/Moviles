package com.practica1.desktopengine;

import com.practica1.engine.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Clase que envuelve una imagen en la aplicacion en desktop
 */
public class ImageDesktop implements Image {

    /**
     * @param filename Nombre del archivo
     * @throws IOException
     */
    public ImageDesktop(String filename) throws IOException {
        java.awt.Image newImage = null;
        try {
            newImage = ImageIO.read(new File(filename));
        } catch (IOException e) {
            throw new RuntimeException("ERROR AL CARGAR IMAGEN POR DESKTOP");
        }
        image = newImage;
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

    /**
     * @return La imagen envuelta
     */
    java.awt.Image getImage() {
        return image;
    }

    ;
}
