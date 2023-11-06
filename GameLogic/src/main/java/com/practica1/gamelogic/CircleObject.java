package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

/**
 * GameObject Circulo, encapsula la info y comportamientos relativos a un circulo del tablero
 */
public class CircleObject extends GameObject {

    private TextObject id;
    private Color color;
    private boolean uncovered = false; // para cuando asignamos un color al tablero
    private boolean selected = false; // para cuando pinchamos encima
    private boolean colorblind = false; // para cuando pinchamos encima
    private int CIRCLE_RADIUS = 50;     //Default Value
    private final int INTERN_CIRCLE_RADIUS = 15;


    public CircleObject(Engine e, int sceneWidth, int sceneHeight, int id, Font font, int RADIO_CIRCULO)
    {
        super(e,sceneWidth,sceneHeight);

        this.id = new TextObject(e,sceneWidth,sceneHeight,
                new Vector2(this.pos.x + RADIO_CIRCULO, this.pos.y + RADIO_CIRCULO),
                font, String.valueOf(id), Color.BLACK);

        this.id.center();
        this.CIRCLE_RADIUS = RADIO_CIRCULO;
    }

    public CircleObject(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, int id, Font font)
    {
        super(e,sceneWidth,sceneHeight, pos);
        this.id = new TextObject(e,sceneWidth,sceneHeight,
                new Vector2(this.pos.x + CIRCLE_RADIUS, this.pos.y + CIRCLE_RADIUS),
                font, String.valueOf(id), Color.BLACK);

        this.id.center();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void render() {

        // Si se ha descubierto pintamos el color normal
        if(uncovered)
        {
            engine.getGraphics().setColor(color);
            engine.getGraphics().fillCircle(pos.x, pos.y, CIRCLE_RADIUS);
            // Si el modo daltonico esta activado pintamos el numero
            if(colorblind)
            {
                id.render();
            }
        }
        // Si no lo pintamos bloqueado
        else
        {
            engine.getGraphics().setColor(Color.GREY);
            engine.getGraphics().fillCircle(pos.x, pos.y, CIRCLE_RADIUS);
            engine.getGraphics().setColor(Color.DARK_GREY);
            engine.getGraphics().fillCircle(pos.x + CIRCLE_RADIUS - INTERN_CIRCLE_RADIUS, pos.y + CIRCLE_RADIUS - INTERN_CIRCLE_RADIUS, INTERN_CIRCLE_RADIUS);
        }


    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public boolean handleInput(TouchEvent event){
        int touchX = event.x;
        int touchY = event.y;
        boolean inside = false;


        if(event.type == TouchEvent.TouchEventType.TOUCH_DOWN){
            //Dentro de manera horizontal
            if(touchX > pos.x && touchX < pos.x + CIRCLE_RADIUS * 2){
                if(touchY > pos.y && touchY < pos.y + CIRCLE_RADIUS * 2){
                    inside = true;

                }
            }
        }

        return inside;
    }

    public Color getColor(){return color;}

    // Asignar color e id (para el texto)
    public void setColor(Color c){
        this.color = c;
        this.id.setText(String.valueOf(color.getId()));
    }
    // Descubrir el color
    public void uncover(boolean d){
        uncovered =d;};
    // Activar modo daltonico
    public void colorblind(boolean d){
        this.colorblind =d;};

    public boolean getUncovered(){return this.uncovered;}



}
