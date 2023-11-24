package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.TouchEvent;

/**
 * GameObject Boton, crea un boton por defecto, con diferentes formas
 */
public class ButtonObject extends GameObject {
    private Vector2 size;
    private Color color;
    private float arc;
    private Font font;
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
     * @param font        Fuente del texto
     * @param text        Texto
     * @param colorButton Color del boton
     * @param colorText   Color del texto
     */
    public ButtonObject(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, Vector2 size, float arc, Font font, String text, Color colorButton, Color colorText) {
        super(e, sceneWidth, sceneHeight, pos);
        this.size = size;
        this.arc = arc;
        this.color = colorButton;

        this.font = font;
        this.text = new TextObject(e, sceneWidth, sceneHeight, new Vector2(pos), font, text, colorText);

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
     * @param imageFile   Nombre delm archivo imagen
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
    public void centrar() {
        pos.x = pos.x - size.x / 2;
        pos.y = pos.y - size.y / 2;

        if (text != null)
            text.center();
        else if (image != null)
            image.center();
    }


}
