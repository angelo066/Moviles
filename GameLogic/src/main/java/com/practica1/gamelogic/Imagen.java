package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.Image;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

public class Imagen extends GameObject {
    Image image;
    Vector2 size;
    // Constructora para boton sin redondeo de bordes
    public Imagen(Engine e, Vector2 pos, Vector2 size, String ruta)
    {
        super(e, pos);
        this.size = size;
        image = engine.getGraphics().newImage(ruta);
    }

    @Override
    public void render()
    {
        engine.getGraphics().drawImage(image, pos.x, pos.y, size.x, size.y);
    }

    public boolean handleInput(TouchEvent event)
    {
        //....
        return true;
    }

    public void centrar()
    {
        pos.x = pos.x - size.x/2;
        pos.y = pos.y - size.y/2;
    }



}
