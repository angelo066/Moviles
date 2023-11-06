package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

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

    // Constructora para Botones sin imagen
    public ButtonObject(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, Vector2 size, float arc, Font font, String text, Color colorButton, Color colorText) {
        super(e, sceneWidth, sceneHeight, pos);
        this.size = size;
        this.arc = arc;
        this.color = colorButton;

        this.font = font;
        this.text = new TextObject(e, sceneWidth, sceneHeight, new Vector2(pos), font, text, colorText);

        this.image = null;
    }

    // Constructora para Botones con imagen
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
        }
        else {
            engine.getGraphics().setColor(color);
            engine.getGraphics().fillRoundRectangle(pos.x, pos.y, size.x, size.y, arc);

            if (text != null)
                text.render();
        }
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        int touchX = event.x;
        int touchY = event.y;
        boolean inside = false;

        if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN) {
            if (touchX > pos.x && touchX < pos.x + size.x &&
                    touchY > pos.y && touchY < pos.y + size.y)
                return true;

        }
        return false;
    }

    public void centrar() {
        pos.x = pos.x - size.x / 2;
        pos.y = pos.y - size.y / 2;

        if (text != null)
            text.center();
        else if (image != null)
            image.center();
    }


}
