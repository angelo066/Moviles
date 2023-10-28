package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

public class Boton extends GameObject {
    Vector2 size;
    colores color;
    String textContent;
    boolean redondeado = false;
    boolean conTexto = false;
    float arc;
    Font font;
    Texto text;

    // Constructora para boton sin redondeo de bordes
    public Boton(Engine e, Vector2 size, colores colorBoton, colores colorTexto)
    {
        super(e);
        this.size = size;
        this.color = colorBoton;
    }
    public Boton(Engine e, Vector2 size, Font font, String text, colores colorBoton, colores colorTexto)
    {
        super(e);
        this.size = size;
        conTexto = true;
        this.font = font;
        this.textContent = text;
        this.color = colorBoton;
        this.text = new Texto(e, pos, size, font, text, colorTexto);
        this.text.centrar();
    }
    // Constructora para boton con redondeo de bordes
    public Boton(Engine e, Vector2 size, float arc, colores colorBoton, colores colorTexto)
    {
        super(e);
        this.size = size;
        this.arc = arc;
        redondeado = true;
        this.color = colorBoton;
    }
    public Boton(Engine e, Vector2 pos, Vector2 size, float arc, Font font, String text, colores colorBoton, colores colorTexto)
    {
        super(e, pos);
        this.size = size;
        this.arc = arc;
        redondeado = true;
        conTexto = true;
        this.font = font;
        this.textContent = text;
        this.color = colorBoton;
        this.text = new Texto(e, new Vector2(pos), size, font, text, colorTexto);
        this.text.centrar();
    }

    @Override
    public void render()
    {
        engine.getGraphics().setColor(color.getValue());

        if(redondeado)
            engine.getGraphics().fillRoundRectangle(pos.x, pos.y, size.x, size.y, arc);
        else
            engine.getGraphics().fillRectangle(pos.x, pos.y, size.x, size.y);

        if(conTexto)
            text.render();
    }

    public boolean handleInput(TouchEvent event)
    {
        //....
        return true;
    }

    // Modifica la posicion iniciar para que sea la centrada
    public void centrar()
    {
        pos.x = pos.x - size.x/2;
        pos.y = pos.y - size.y/2;
    }


}
