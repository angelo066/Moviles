package com.practica1.desktopengine;

import com.practica1.engine.Image;

public class ImageDesktop implements Image {

    public ImageDesktop(java.awt.Image i)
    {
        this.image = i;
    }
    private java.awt.Image image;
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    java.awt.Image getImage(){return image;};
}
