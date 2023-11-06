package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.GameObject;
import com.practica1.engine.Vector2;

public class CheckObject extends GameObject {

    private final int RADIO_CIRCULO = 10;
    private int NUM_CASILLAS;

    private Vector2 pos;

    private int num_AciertosColor = 0;
    private int num_AciertosPosicion = 0;

    private int offset = 30;


    public CheckObject(Engine e, int sceneWidth, int sceneHeight, int NUM_CASILLAS, Vector2 pos) {

        super(e, sceneWidth, sceneHeight);
        this.NUM_CASILLAS = NUM_CASILLAS;
        this.pos = pos;
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void render() {

        //Necesito estas dos variables porque si resto a las de la clase se dejan de dibujar los circulos
        int aciertos = num_AciertosPosicion;
        int aciertos_Color = num_AciertosColor;

        for (int i = 0; i < NUM_CASILLAS / 2; i++) {
            int posX = (sceneWidth - 100) + offset * i;

            if (aciertos > 0) {
                engine.getGraphics().setColor(Color.BLACK);
                engine.getGraphics().fillCircle(posX, pos.y, RADIO_CIRCULO);
                aciertos--;
            } else if (aciertos_Color > 0) {
                engine.getGraphics().setColor(Color.BLACK);
                engine.getGraphics().drawCircle(posX, pos.y, RADIO_CIRCULO);
/*                engine.getGraphics().setColor(Color.NEGRO);
                engine.getGraphics().fillCircle(posX, pos.y, RADIO_CIRCULO);*/
                aciertos_Color--;
            } else {
                engine.getGraphics().setColor(Color.GREY);
                engine.getGraphics().fillCircle(posX, pos.y, RADIO_CIRCULO);

            }
        }

        for (int i = NUM_CASILLAS / 2; i < NUM_CASILLAS; i++) {
            //Para que no salgan hacia la derecha
            int posX = (sceneWidth - 100) + offset * (i - NUM_CASILLAS / 2);
            int posY_NextLine = pos.y + RADIO_CIRCULO + offset; //Variable para los circulos de la parte inferior

            if (aciertos > 0) {
                engine.getGraphics().setColor(Color.BLACK);
                engine.getGraphics().fillCircle(posX, posY_NextLine, RADIO_CIRCULO);
                aciertos--;
            } else if (aciertos_Color > 0) {
                engine.getGraphics().setColor(Color.BLACK);
                engine.getGraphics().drawCircle(posX, posY_NextLine, RADIO_CIRCULO);
                aciertos_Color--;
            } else {
                engine.getGraphics().setColor(Color.GREY);
                engine.getGraphics().fillCircle(posX, posY_NextLine, RADIO_CIRCULO);

            }
        }

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    public void setCirculos(Attempt intento) {
        //Las dos cosas
        num_AciertosPosicion = intento.checks_pos;
        //Solo esta bien el color pero no la posicion
        num_AciertosColor = intento.checks_color;
    }

}
