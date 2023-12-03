package com.practica1.gamelogic;


import com.practica1.engine.Color;
import com.practica1.engine.Graphics;
import com.practica1.engine.TouchEvent;

/**
 * GameObject Boton, crea un boton por defecto, con diferentes formas
 */
public class ButtonObject {
    private Vector2 size;
    private Color color;
    private float arc;
    private TextObject text;
    private ImageObject image;

    private Graphics graphics;
    private Vector2 pos;
    private Vector2 iniPos;

    /**
     * Constructora para Botones sin imagen, con o sin texto
     *
     * @param graphics    Objeto graphics del engine
     * @param pos         Posicion del boton
     * @param size        Tamanio del boton
     * @param arc         Curvatura de las esquinas
     * @param text        Objeto de texto
     * @param colorButton Color del boton
     */
    public ButtonObject(Graphics graphics, Vector2 pos, Vector2 size, float arc, Color colorButton, TextObject text) {
        this.size = size;

        this.arc = arc;
        this.color = colorButton;
        this.text = text;

        this.image = null;

        this.graphics = graphics;
        this.pos = new Vector2(pos);
        this.iniPos = pos;
    }

    /**
     * Constructora para Botones con imagen
     *
     * @param graphics  Objeto graphics del engine
     * @param pos       Posicion del boton
     * @param size      Tamanio del boton
     * @param imageFile Nombre del archivo imagen
     */
    public ButtonObject(Graphics graphics, Vector2 pos, Vector2 size, String imageFile) {
        this.size = size;

        this.text = null;

        this.image = new ImageObject(graphics, pos, size, imageFile);

        this.graphics = graphics;
        this.pos = new Vector2(pos);
        this.iniPos = pos;
    }

    /**
     * Render del boton
     */
    public void render() {

        if (image != null) {
            image.render();
        } else {
            graphics.setColor(color.getValue());
            graphics.fillRoundRectangle(pos.x, pos.y, size.x, size.y, arc);

            if (text != null)
                text.render();
        }
    }

    /**
     * Manejo de input del boton
     *
     * @param event Evento de input
     * @return Si se ha presionado el boton
     */
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

    /**
     * Cambia el texto del boton
     *
     * @param text
     */
    public void changeText(String text) {
        if (this.text != null)
            this.text.setText(text);
    }

    /**
     * Cambia la imagen del boton
     *
     * @param image
     */
    public void changeImage(String image) {
        if (this.image != null)
            this.image.changeImage(image);
    }

}
