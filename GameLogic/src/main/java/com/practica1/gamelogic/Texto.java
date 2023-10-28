package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

public class Boton extends GameObject {
    Vector2 size;
    colores color;
    String text;
    boolean redondeado = false;
    boolean conTexto = false;
    float arc;
    Font font;

    // Constructora para boton sin redondeo de bordes
    public Boton(Engine e, Vector2 size, colores color)
    {
        super(e);
        this.size = size;
        this.color = color;
    }
    public Boton(Engine e, Vector2 size, Font font, String text, colores color)
    {
        super(e);
        this.size = size;
        conTexto = true;
        this.font = font;
        this.text = text;
        this.color = color;
    }
    // Constructora para boton con redondeo de bordes
    public Boton(Engine e, Vector2 size, float arc, colores color)
    {
        super(e);
        this.size = size;
        this.arc = arc;
        redondeado = true;
        this.color = color;
    }
    public Boton(Engine e, Vector2 size, float arc, Font font, String text, colores color)
    {
        super(e);
        this.size = size;
        this.arc = arc;
        redondeado = true;
        conTexto = true;
        this.font = font;
        this.text = text;
        this.color = color;
    }

    @Override
    public void render()
    {
        engine.getGraphics().setColor(color.getValue());

        if(redondeado)
        {
            engine.getGraphics().fillRoundRectangle(pos.x, pos.y, size.x, size.y, arc);
        }
        else
        {
            engine.getGraphics().fillRectangle(pos.x, pos.y, size.x, size.y);
        }

        if(conTexto)
        {
            engine.getGraphics().setColor(colores.NEGRO.getValue());
            engine.getGraphics().setFont(font);
            engine.getGraphics().drawText(text, pos.x+50, pos.y+50);
        }
    }

    public boolean handleInput(TouchEvent event)
    {
        //....
        return true;
    }


}
