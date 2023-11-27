package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;


/**
 * GameObject Texto, encapsula las funcionalidades de pintar texto dentro de un objeto
 */
public class TextObject extends GameObject {
    private Color color;
    private String text;
    private Font font;
    private int textSize;
    private boolean isBold;
    private boolean isItalic;

    /**
     * @param e           Engine de la aplicacion
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     * @param pos         Posicion del texto
     * @param font        Fuente del texto
     * @param text        Texto
     * @param color       Color del texto
     * @param textSize    Tamaño del texto
     * @param isBold      Bold
     * @param isItalic    Italic
     */
    public TextObject(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, String font, String text, Color color, int textSize, boolean isBold, boolean isItalic) {
        super(e, sceneWidth, sceneHeight, pos);
        this.font = ResourceManager.getInstance().getFont(font);
        this.text = text;
        this.color = color;
        this.textSize = textSize;
        this.isItalic = isItalic;
        this.isBold = isBold;
    }

    @Override
    public void render() {
        engine.getGraphics().setColor(color.getValue());

        font.setSize(textSize);
        font.setBold(isBold);
        font.setItalic(isItalic);
        engine.getGraphics().setFont(font);

        engine.getGraphics().drawText(text, pos.x, pos.y);
    }

    /**
     * Centra el texto en pos
     */
    public void center() {
        font.setSize(textSize);
        font.setBold(isBold);
        font.setItalic(isItalic);
        engine.getGraphics().setFont(font);

        pos.x = (int) (iniPos.x - engine.getGraphics().getFontMetricWidth(font, text) / 2);
        pos.y = (int) (iniPos.y - engine.getGraphics().getFontMetricHeight(font) / 1.35);
    }

    /**
     * Centra el texto en pos horizontalmente
     */
    public void centerHorizontal() {
        font.setSize(textSize);
        font.setBold(isBold);
        font.setItalic(isItalic);
        engine.getGraphics().setFont(font);

        pos.x = (int) (iniPos.x - engine.getGraphics().getFontMetricWidth(font, text) / 2);
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
