package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.GameObject;
import com.practica1.androidengine.Vector2;

/**
 * GameObject Texto, encapsula las funcionalidades de pintar texto dentro de un objeto
 */
public class TextObject extends GameObject {
    private Color color;
    private String text;
    private Font font;

    /**
     * @param e           Engine de la aplicacion
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     * @param pos         Posicion del texto
     * @param font        Fuente del texto
     * @param text        Texto
     * @param color       Color del texto
     */
    public TextObject(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, Font font, String text, Color color) {
        super(e, sceneWidth, sceneHeight, pos);
        this.font = font;
        this.text = text;
        this.color = color;

    }

    @Override
    public void render() {
        engine.getGraphics().setColor(color);
        engine.getGraphics().setFont(font);
        engine.getGraphics().drawText(text, pos.x, pos.y);
    }

    /**
     * Centra el texto en pos
     */
    public void center() {
        pos.x = (int) (pos.x - engine.getGraphics().getFontMetricWidth(font, text) / 2);
        pos.y = (int) (pos.y - engine.getGraphics().getFontMetricHeight(font) / 1.4);
    }

    /**
     * Centra el texto en pos horizontalmente
     */
    public void centerHorizontal() {
        pos.x = (int) (pos.x - engine.getGraphics().getFontMetricWidth(font, text) / 2);
    }

    /**
     * Cambia el texto del objeto
     *
     * @param text Nuevo texto
     */
    public void setText(String text) {
        this.text = text;
    }


}
