package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.Image;
import com.practica1.androidengine.Scene;

import java.io.Serializable;

/**
 * GameObject Imagen, encapsula las funcionalidades de pintar imagenes dentro de un objeto
 */
public class ImageObject implements Serializable {
    private transient Image image;
    private Vector2 size;
    private Vector2 pos;
    private Vector2 iniPos;
    private transient Graphics graphics;
    private String imageFile;


    /**
     * @param graphics  Objeto graphics del engine
     * @param pos       Posicion de la imagen
     * @param size      Tamanio de la imagen
     * @param imageFile Archivo de la imagen
     */
    public ImageObject(Graphics graphics, Vector2 pos, Vector2 size, String imageFile) {
        this.size = size;
        this.image = ResourceManager.getInstance().getImage(imageFile);
        this.imageFile = imageFile;
        this.graphics = graphics;
        this.pos = new Vector2(pos);
        this.iniPos = pos;
    }

    public ImageObject(Graphics graphics, Vector2 pos, Vector2 size, Image image) {
        this.size = size;
        this.image = image;
        this.graphics = graphics;
        this.pos = new Vector2(pos);
        this.iniPos = pos;
        this.imageFile = image.getFile();
    }

    /**
     * Render de la imagen
     */
    public void render() {
        graphics.drawImage(image, pos.x, pos.y, size.x, size.y);
    }

    /**
     * Centra la imagen en pos
     */
    public void center() {
        pos.x = iniPos.x - size.x / 2;
        pos.y = iniPos.y - size.y / 2;
    }

    /**
     * Cambia la imagen
     *
     * @param imageFile
     */
    public void changeImage(String imageFile) {
        image = ResourceManager.getInstance().getImage(imageFile);
        this.imageFile = imageFile;
    }

    /**
     * Traslada el objeto
     *
     * @param translateX
     * @param translateY
     */
    public void translate(int translateX, int translateY) {
        pos.x += translateX;
        pos.y += translateY;

        iniPos.x += translateX;
        iniPos.y += translateY;
    }

    /**
     * @return Posicion del objeto
     */
    public Vector2 getPos() {
        return pos;
    }

    /**
     * @return Imagen del objeto
     */
    public Image getImage() {
        return image;
    }

}
