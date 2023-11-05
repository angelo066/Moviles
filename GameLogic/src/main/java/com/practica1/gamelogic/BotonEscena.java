package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

public class BotonEscena extends Boton {

    // Constructora para boton sin redondeo de bordes
    public BotonEscena(Engine e, int sceneWidth, int sceneHeight, Vector2 size, Color colorBoton, Color colorTexto) {
        super(e, sceneWidth, sceneHeight, size, colorBoton, colorTexto);
    }

    public BotonEscena(Engine e, int sceneWidth, int sceneHeight, Vector2 size, Font font, String text, Color colorBoton, Color colorTexto) {
        super(e, sceneWidth, sceneHeight, size, font, text, colorBoton, colorTexto);
    }

    // Constructora para boton con redondeo de bordes
    public BotonEscena(Engine e, int sceneWidth, int sceneHeight, Vector2 size, float arc, Color colorBoton, Color colorTexto) {
        super(e, sceneWidth, sceneHeight, size, arc, colorBoton, colorTexto);
    }

    public BotonEscena(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, Vector2 size, float arc, Font font, String text, Color colorBoton, Color colorTexto) {
        super(e, sceneWidth, sceneHeight, pos, size, arc, font, text, colorBoton, colorTexto);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public boolean handleInput(TouchEvent event) {
        boolean pulsado = super.handleInput(event);
        if (pulsado) {
            // Esto hay que cambiarlo, pero para probar que funciona
            Scene scene = new MenuDificultad();
            engine.setScene(scene);
        }

        return pulsado;
    }


}
