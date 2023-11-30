package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.GameObject;
import com.practica1.engine.Image;
import com.practica1.engine.Vector2;

/**
 * GameObject Imagen, encapsula las funcionalidades de pintar imagenes dentro de un objeto
 */
public class ImageObject extends GameObject {
    private Image image;
    private Vector2 size;

    /**
     * @param e           Engine de la aplicacion
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     * @param pos         Posicion de la imagen
     * @param size        Tamanio de la imagen
     * @param imageFile   Archivo de la imagen
     */

    public ImageObject(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, Vector2 size, String imageFile) {
        super(e, sceneWidth, sceneHeight, pos);
        this.size = size;
        image = engine.getGraphics().newImage(imageFile);
    }

    @Override
    public void render() {
        engine.getGraphics().drawImage(image, pos.x, pos.y, size.x, size.y);
    }

    /**
     * Centra la imagen en pos
     */
    public void center() {
        pos.x = pos.x - size.x / 2;
        pos.y = pos.y - size.y / 2;
    }


}