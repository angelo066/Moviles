package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

public class Texto extends GameObject {
    Vector2 size;
    colores color;
    String text;
    Font font;

    // Constructora para boton sin redondeo de bordes
    public Texto(Engine e, Vector2 pos, Vector2 size, Font font, String text, colores color)
    {
        super(e, pos);
        this.size = size;
        this.font = font;
        this.text = text;
        this.color = color;
    }

    @Override
    public void render()
    {
        engine.getGraphics().setColor(color.getValue());
        engine.getGraphics().setFont(font);
        engine.getGraphics().drawText(text, pos.x+50, pos.y+50);
    }

    public boolean handleInput(TouchEvent event)
    {
        //....
        return true;
    }

    public void centrar()
    {
        int fwidth = (int)engine.getGraphics().getFontMetricWidth(font, text);
        int fheight = (int)engine.getGraphics().getFontMetricHeight(font);
        pos.x = pos.x - fwidth/2 - 50; // el 50 es porque el Metrics funciona mal y en width te devuelve el valor con un pequeño offset por la izquierda
        pos.y = pos.y - fheight/2 + 25;
    }


}
