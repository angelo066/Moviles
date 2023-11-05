package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

public class Texto extends GameObject {
    private Color color;
    private String text;
    private Font font;

    private float textWidth;

    private float textHeight;

    // Constructora para boton sin redondeo de bordes
    public Texto(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, Font font, String text, Color color) {
        super(e, sceneWidth, sceneHeight, pos);
        this.font = font;
        this.text = text;
        this.color = color;
        textWidth = engine.getGraphics().getFontMetricWidth(font, text);
        textHeight = engine.getGraphics().getFontMetricHeight(font);
    }

    @Override
    public void render() {
        engine.getGraphics().setColor(color);
        engine.getGraphics().setFont(font);
        engine.getGraphics().drawText(text, pos.x, pos.y);
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        //....
        return true;
    }

    public void centrar() {
        pos.x = (int) (pos.x - textWidth / 2);
        pos.y = (int) (pos.y - textHeight / 1.3);
    }

    public void setText(String text) {
        this.text = text;
    }


}
