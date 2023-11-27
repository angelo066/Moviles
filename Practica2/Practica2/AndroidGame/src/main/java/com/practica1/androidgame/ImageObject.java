package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.Image;
import com.practica1.androidengine.Scene;

/**
 * GameObject Imagen, encapsula las funcionalidades de pintar imagenes dentro de un objeto
 */
public class ImageObject {
    private Image image;
    private Vector2 size;
    private Vector2 pos;
    private Vector2 iniPos;
    private Graphics graphics;

    /**
     * @param graphics  Objeto graphics del engine
     * @param pos       Posicion de la imagen
     * @param size      Tamanio de la imagen
     * @param imageFile Archivo de la imagen
     */
    public ImageObject(Graphics graphics, Vector2 pos, Vector2 size, String imageFile) {
        this.size = size;
        this.image = ResourceManager.getInstance().getImage(imageFile);
        this.graphics = graphics;
        this.pos = new Vector2(pos);
        this.iniPos = pos;
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
    }
}
