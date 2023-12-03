package com.practica1.gamelogic;


import com.practica1.engine.Color;
import com.practica1.engine.Font;
import com.practica1.engine.Graphics;

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
    }

    /**
     * Render del texto
     */
    public void render() {
        graphics.setColor(color.getValue());

        setFont();

        graphics.drawText(text, pos.x, pos.y);
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

    /**
     * Setea la informacion de la fuente
     */
    private void setFont() {
        font.setSize(textSize);
        font.setBold(isBold);
        font.setItalic(isItalic);
        graphics.setFont(font);
    }

}
