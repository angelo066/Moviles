package com.practica1.androidgame;

import static java.lang.Integer.parseInt;

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
    private long aux_color_prueba_cambiar_men = 0;

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

        if(GameManager.getInstance().getActual_Skin_Palette() != null)
        {
            String a = GameManager.getInstance().getActual_Skin_Palette().getColor_2();
            aux_color_prueba_cambiar_men = Long.parseLong(a, 16);
        }
    }

    /**
     * Render del texto
     */
    public void render() {


        if(GameManager.getInstance().getActual_Skin_Palette() != null)
            graphics.setColor((int)aux_color_prueba_cambiar_men);
        else
            graphics.setColor(color.getValue());
        //graphics.setColor(color.getValue());


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
}
