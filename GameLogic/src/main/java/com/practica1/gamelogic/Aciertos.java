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


        for(int i = 0; i < NUM_CASILLAS / 2; i++){
            int posX = (engine.getGraphics().getWidth() - 100) + offset * i;

            if(num_AciertosPosicion > 0){
                engine.getGraphics().setColor(colores.NEGRO.getValue());
                engine.getGraphics().fillCircle(posX, pos.y, RADIO_CIRCULO);
                num_AciertosPosicion--;
            }
            else if(num_AciertosColor > 0){
                engine.getGraphics().setColor(colores.AZUL.getValue());
                engine.getGraphics().fillCircle(posX, pos.y , RADIO_CIRCULO);
            }
            else{
                engine.getGraphics().setColor(colores.GRIS.getValue());
                engine.getGraphics().fillCircle(posX, pos.y, RADIO_CIRCULO);

            }
        }

        for(int i = NUM_CASILLAS/2; i < NUM_CASILLAS;i++){
                                                                                //Para que no salgan hacia la derecha
            int posX = (engine.getGraphics().getWidth() - 100) + offset * (i - NUM_CASILLAS/2);
            int posY_NextLine = pos.y + RADIO_CIRCULO + offset; //Variable para los circulos de la parte inferior

            if(num_AciertosPosicion > 0){
                engine.getGraphics().setColor(colores.NEGRO.getValue());
                engine.getGraphics().fillCircle(posX, posY_NextLine, RADIO_CIRCULO);
                num_AciertosPosicion--;
            }
            else if(num_AciertosColor > 0){
                engine.getGraphics().setColor(colores.AZUL.getValue());
                engine.getGraphics().fillCircle(posX, posY_NextLine , RADIO_CIRCULO);
            }
            else{
                engine.getGraphics().setColor(colores.GRIS.getValue());
                engine.getGraphics().fillCircle(posX, posY_NextLine, RADIO_CIRCULO);

            }
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
