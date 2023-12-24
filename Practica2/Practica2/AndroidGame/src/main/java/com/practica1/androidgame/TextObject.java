package com.practica1.androidgame;

import static java.lang.Integer.parseInt;

import com.practica1.androidengine.Font;
import com.practica1.androidengine.Graphics;

import java.io.Serializable;


/**
 * GameObject Texto, encapsula las funcionalidades de pintar texto dentro de un objeto
 */
public class TextObject implements Serializable {
    private int color;
    private String text;
    private transient Font font;
    private int textSize;
    private boolean isBold;
    private boolean isItalic;
    private Vector2 pos;
    private Vector2 iniPos;
    private transient Graphics graphics;
    private String fontName;

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
    public TextObject(Graphics graphics, Vector2 pos, String font, String text, int color, int textSize, boolean isBold, boolean isItalic) {
        this.font = ResourceManager.getInstance().getFont(font);
        this.fontName = font;
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
        graphics.setColor(color);

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

    /**
     * Traslada el objeto
     *
     * @param translateX
     * @param translateY
     */
    public void translate(int translateX, int translateY) {
        pos.x += translateX;
        pos.y += translateY;

        iniPos.x += translateX;
        iniPos.y += translateY;
    }

    /**
     * @return Posicion del objeto
     */
    public Vector2 getPos() {
        return pos;
    }

    /**
     * Resetea el color. Se usa para resetear en la tienda al seleccionar color
     */
    public void resetColor() {
        color = GameManager.getInstance().getCurrentSkinPalette().getColor2();
    }

    /**
     * Se usa para cargar informacion desde el archivo de guardado
     *
     * @param graphics Objeto graphics del motor
     */
    public void load(Graphics graphics) {
        this.graphics = graphics;
        this.font = ResourceManager.getInstance().getFont(fontName);
    }
}
