package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

public class BotonEscena extends Boton {

    // Constructora para boton sin redondeo de bordes
    public BotonEscena(Engine e, Vector2 size, colores colorBoton, colores colorTexto) {super(e, size, colorBoton, colorTexto);}
    public BotonEscena(Engine e, Vector2 size, Font font, String text, colores colorBoton, colores colorTexto) {super(e, size, font, text, colorBoton, colorTexto);}
    // Constructora para boton con redondeo de bordes
    public BotonEscena(Engine e, Vector2 size, float arc, colores colorBoton, colores colorTexto) {super(e, size, arc, colorBoton, colorTexto);}
    public BotonEscena(Engine e, Vector2 pos, Vector2 size, float arc, Font font, String text, colores colorBoton, colores colorTexto) {super(e, pos, size, arc, font, text, colorBoton, colorTexto);}

    @Override
    public void render()
    {
       super.render();
    }

    public boolean handleInput(TouchEvent event)
    {
        boolean pulsado = super.handleInput(event);
        if(pulsado)
        {
            // Esto hay que cambiarlo, pero para probar que funciona
            Scene scene = new MenuDificultad();
            engine.setScene(scene);
            scene.init(engine);
        }

        return pulsado;
    }


}
