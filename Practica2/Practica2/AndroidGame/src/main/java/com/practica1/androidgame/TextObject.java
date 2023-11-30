package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.Graphics;


/**
 * GameObject Texto, encapsula las funcionalidades de pintar texto dentro de un objeto
 */
public class TextObject {
    private Color color;
    private String text;
    private Font font;
    private int textSize;
    private boolean isBold;
    private boolean isItalic;
    private Vector2 pos;
    private Vector2 iniPos;
    private Graphics graphics;

    private int offsetY;

    /**
     * @param graphics Objeto graphics del engine
     * @param pos      Posicion del texto
     * @param font     Fuente del texto
     * @param text     Texto
     * @param color    Color del texto
     * @param textSize Tamaño del texto
     * @param isBold   Bold
     * @param isItalic Italic
     */
    public TextObject(Graphics graphics, Vector2 pos, String font, String text, Color color, int textSize, boolean isBold, boolean isItalic) {
        this.font = ResourceManager.getInstance().getFont(font);
        this.text = text;
        this.color = color;
        this.textSize = textSize;
        this.isItalic = isItalic;
        this.isBold = isBold;
        this.graphics = graphics;
        this.pos = new Vector2(pos);
        this.iniPos = pos;
        this.offsetY = 0;
    }

    /**
     * Render del texto
     */
    public void render() {
        graphics.setColor(color.getValue());

        setFont();

        graphics.drawText(text, pos.x, pos.y + offsetY);
    }

    /**
     * Centra el texto en pos
     */
    public void center() {
        setFont();

        pos.x = (int) (iniPos.x - graphics.getFontMetricWidth(font, text) / 2);
        pos.y = (int) (iniPos.y - graphics.getFontMetricHeight(font) / 1.35);
    }

    /**
     * Centra el texto en pos horizontalmente
     */
    public void centerHorizontal() {
        setFont();

        pos.x = (int) (iniPos.x - graphics.getFontMetricWidth(font, text) / 2);
    }

    /**
     * Cambia el texto del objeto
     *
     * @param text Nuevo texto
     */
    public void setText(String text) {
        this.text = text;
    }

    private void setFont() {
        font.setSize(textSize);
        font.setBold(isBold);
        font.setItalic(isItalic);
        graphics.setFont(font);
    }

    public void setOffsetY(int newOffset){
        offsetY = newOffset;
    }
}
