package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

public class Aciertos extends GameObject {

    private final int RADIO_CIRCULO = 10;
    private int NUM_CASILLAS;

    private Vector2 pos;

    private int num_AciertosColor = 0;
    private int num_AciertosPosicion = 0;

    private int offset = 30;


    public Aciertos(Engine e, int NUM_CASILLAS, Vector2 pos)
    {

        super(e);
        this.NUM_CASILLAS = NUM_CASILLAS;
        this.pos = pos;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void render() {

        for(int i = 0; i < num_AciertosPosicion; i++){
            engine.getGraphics().setColor(colores.NEGRO.getValue());
            engine.getGraphics().fillCircle(engine.getGraphics().getWidth() - 400, pos.y, RADIO_CIRCULO);
        }

        for(int i = 0; i < num_AciertosColor; i++){
            engine.getGraphics().setColor(colores.BLANCO.getValue());
            engine.getGraphics().fillCircle(engine.getGraphics().getWidth() - 300, pos.y, RADIO_CIRCULO);
        }

        for(int i = 0; i < Math.abs(num_AciertosColor - num_AciertosPosicion); i++){                  //Distancia entre circulos
            engine.getGraphics().fillCircle(engine.getGraphics().getWidth() - 200, pos.y, RADIO_CIRCULO);
        }


    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    public void setCirculos(IntentoFila intento){
        //Las dos cosas
        num_AciertosPosicion = intento.aciertos_pos;
        //Solo esta bien el color pero no la posicion
        num_AciertosColor = intento.aciertos_color;
    }

}
