package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.TouchEvent;

/**
 * GameObject Circulo, encapsula la info y comportamientos relativos a un circulo del tablero
 */
public class CircleObject extends GameObject {

    private TextObject id;
    private Color color;
    private boolean uncovered; // para cuando asignamos un color al tablero
    private boolean colorblind; // para cuando pinchamos encima
    private int circleRadius = 50, internCircleRadius = 15;

    /**
     * @param e           Engine de la aplicacion
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     * @param pos         Posicion del circulo
     * @param id          Numero del circulo, para el modo daltonicos
     * @param font        Fuente para el id
     */
    public CircleObject(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, int id, Font font) {
        super(e, sceneWidth, sceneHeight, pos);
        this.id = new TextObject(e, sceneWidth, sceneHeight,
                new Vector2(this.pos.x + circleRadius, this.pos.y + circleRadius),
                font, String.valueOf(id), Color.BLACK);

        this.id.center();

        uncovered = false;
        colorblind = false;
    }

    @Override
    public void render() {

        // Si se ha descubierto pintamos el color normal
        if (uncovered) {
            engine.getGraphics().setColor(color.getValue());
            engine.getGraphics().fillCircle(pos.x, pos.y, circleRadius);

            // Si el modo daltonico esta activado pintamos el numero
            if (colorblind)
                id.render();

        }
        // Si no lo pintamos bloqueado
        else {
            engine.getGraphics().setColor(Color.GREY.getValue());
            engine.getGraphics().fillCircle(pos.x, pos.y, circleRadius);
            engine.getGraphics().setColor(Color.DARK_GREY.getValue());
            engine.getGraphics().fillCircle(pos.x + circleRadius - internCircleRadius, pos.y + circleRadius - internCircleRadius, internCircleRadius);
        }


    }

    @Override
    public boolean handleInput(TouchEvent event) {
        int touchX = event.x;
        int touchY = event.y;

        if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN) {

            //Dentro de manera horizontal
            if (touchX > pos.x && touchX < pos.x + circleRadius * 2) {
                if (touchY > pos.y && touchY < pos.y + circleRadius * 2)
                    return true;
            }
        }
        return false;
    }

    /**
     * @return El color del circulo
     */
    public Color getColor() {
        return color;
    }

    /**
     * Asignar color e id (para el texto)
     */
    public void setColor(Color c) {
        this.color = c;
        this.id.setText(String.valueOf(color.getId()));
    }

    /**
     * Descubrir el color
     *
     * @param setUncover
     */
    public void uncover(boolean setUncover) {
        uncovered = setUncover;
    }

    /**
     * Activar modo daltonico
     *
     * @param setColorblind
     */
    public void colorblind(boolean setColorblind) {
        this.colorblind = setColorblind;
    }

    /**
     * @return Si esta descubierto el color
     */
    public boolean getUncovered() {
        return this.uncovered;
    }

}
