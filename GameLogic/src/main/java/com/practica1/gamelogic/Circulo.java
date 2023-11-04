package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;

public class Circulo extends GameObject {

    private Color color;
    private boolean descubierto = false; // para cuando asignamos un color al tablero
    private boolean seleccionado = false; // para cuando pinchamos encima
    private final int RADIO_CIRCULO = 50;
    private final int RADIO_CIRCULO_INTERIOR = 15;
    private final int RADIO_SELECCION = 55;

    public Circulo(Engine e)
    {
        super(e);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void render() {
        // Pintamos un borde amarillo alrededor del Circulo
        if(seleccionado)
        {
            engine.getGraphics().setColor(Color.AMARILLO);
            engine.getGraphics().fillCircle(pos.x - (RADIO_SELECCION - RADIO_CIRCULO), pos.y - (RADIO_SELECCION - RADIO_CIRCULO), RADIO_SELECCION);
        }
        // Si se ha seleccionado pintamos el color normal
        if(descubierto)
        {
            engine.getGraphics().setColor(color);
            engine.getGraphics().fillCircle(pos.x, pos.y, RADIO_CIRCULO);
        }
        // Si no lo pintamos bloqueado
        else
        {
            engine.getGraphics().setColor(Color.GRIS);
            engine.getGraphics().fillCircle(pos.x, pos.y, RADIO_CIRCULO);
            engine.getGraphics().setColor(Color.GRIS_OSCURO);
            engine.getGraphics().fillCircle(pos.x + RADIO_CIRCULO - RADIO_CIRCULO_INTERIOR, pos.y +  RADIO_CIRCULO - RADIO_CIRCULO_INTERIOR, RADIO_CIRCULO_INTERIOR);
        }
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    public boolean handleInput(TouchEvent event){
        int touchX = event.x;
        int touchY = event.y;
        boolean inside = false;


        if(event.type == TouchEvent.TouchEventType.TOUCH_DOWN){
            //Dentro de manera horizontal
            if(touchX > pos.x && touchX < pos.x + RADIO_CIRCULO * 2){
                if(touchY > pos.y && touchY < pos.y + RADIO_CIRCULO * 2){
                    inside = true;

                }
            }
        }

        //Entiendo que esto hay que devolverlo asi
        return inside;
    }

    public Color getColor(){return color;}

    public void setColor(Color c){color = c;};
    public void seleccionar(boolean s){seleccionado=s;};
    public void descubrir(boolean d){descubierto=d;};

    public boolean getDescubierto(){return descubierto;}
}
