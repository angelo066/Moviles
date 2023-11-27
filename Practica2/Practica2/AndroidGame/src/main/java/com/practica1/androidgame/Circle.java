package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.TouchEvent;

/**
 * GameObject Circulo, encapsula la info y comportamientos relativos a un circulo del tablero
 */
public class Circle {
    private TextObject id;
    private Color color;
    private boolean uncovered; // para cuando asignamos un color al tablero
    private boolean colorblind; // para cuando pinchamos encima
    private int circleRadius;
    private Vector2 pos;
    private Graphics graphics;

    /**
     * @param pos          Posicion del circulo
     * @param circleRadius
     */
    public Circle(Graphics graphics, Vector2 pos, int circleRadius) {
        this.graphics = graphics;
        this.circleRadius = circleRadius;
        this.pos = pos;
        this.color = Color.NO_COLOR;

        this.id = new TextObject(graphics, new Vector2(pos.x + circleRadius, pos.y + circleRadius),
                "Nexa.ttf", String.valueOf(this.color.getId()), Color.BLACK, 50, false, false);
        this.id.center();

        this.uncovered = false;
        this.colorblind = false;
    }

    public void render() {
        // Si se ha descubierto pintamos el color normal
        if (uncovered) {
            graphics.setColor(color.getValue());
            graphics.fillCircle(pos.x, pos.y, circleRadius);

            // Si el modo daltonico esta activado pintamos el numero
            if (colorblind)
                id.render();

        }
        // Si no lo pintamos bloqueado
        else {
            int internCircleRadius = circleRadius / 4;
            graphics.setColor(Color.GREY.getValue());
            graphics.fillCircle(pos.x, pos.y, circleRadius);
            graphics.setColor(Color.DARK_GREY.getValue());
            graphics.fillCircle(pos.x + circleRadius - internCircleRadius, pos.y + circleRadius - internCircleRadius, internCircleRadius);
        }

    }

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
        this.id.center();
    }

    /**
     * Descubrir el color
     *
     * @param setUncover
     */
    public void setUncovered(boolean setUncover) {
        uncovered = setUncover;
    }

    /**
     * Activar modo daltonico
     *
     * @param setColorblind
     */
    public void setColorblind(boolean setColorblind) {
        this.colorblind = setColorblind;
    }

    /**
     * @return Si esta descubierto el color
     */
    public boolean getUncovered() {
        return this.uncovered;
    }

}
