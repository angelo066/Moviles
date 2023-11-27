package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.TouchEvent;

/**
 * GameObject Boton, crea un boton por defecto, con diferentes formas
 */
public class ButtonObject extends GameObject {
    private Vector2 size;
    private Color color;
    private float arc;
    private TextObject text;
    private ImageObject image;

    /**
     * Constructora para Botones sin imagen, con o sin texto
     *
     * @param e           Engine de la aplicacion
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     * @param pos         Posicion del boton
     * @param size        Tamanio del boton
     * @param arc         Curvatura de las esquinas
     * @param text        Objeto de texto
     * @param colorButton Color del boton
     */
    public ButtonObject(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, Vector2 size, float arc, Color colorButton, TextObject text) {
        super(e, sceneWidth, sceneHeight, pos);
        this.size = size;
        this.arc = arc;
        this.color = colorButton;

        this.text = text;

        this.image = null;
    }

    /**
     * Constructora para Botones con imagen
     *
     * @param e           Engine de la aplicacion
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     * @param pos         Posicion del boton
     * @param size        Tamanio del boton
     * @param imageFile   Nombre del archivo imagen
     */
    public ButtonObject(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, Vector2 size, String imageFile) {
        super(e, sceneWidth, sceneHeight, pos);
        this.size = size;

        this.text = null;

        this.image = new ImageObject(e, sceneWidth, sceneHeight, pos, size, imageFile);
    }

    @Override
    public void render() {

        if (image != null) {
            image.render();
        } else {
            engine.getGraphics().setColor(color.getValue());
            engine.getGraphics().fillRoundRectangle(pos.x, pos.y, size.x, size.y, arc);

            if (text != null)
                text.render();
        }
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        int touchX = event.x;
        int touchY = event.y;

        if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN) {
            if (touchX > pos.x && touchX < pos.x + size.x &&
                    touchY > pos.y && touchY < pos.y + size.y)
                return true;
        }
        return false;
    }

    /**
     * Centra el boton en pos
     */
    public void center() {
        pos.x = iniPos.x - size.x / 2;
        pos.y = iniPos.y - size.y / 2;

        if (text != null)
            text.center();
        if (image != null)
            image.center();
    }

    public void changeText(String text){
        if(this.text != null)
            this.text.setText(text);
    }

    public void changeImage(String image){
        if(this.image != null)
            this.image.changeImage(image);
    }

}
