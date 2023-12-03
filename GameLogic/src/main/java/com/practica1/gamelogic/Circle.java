package com.practica1.gamelogic;


import com.practica1.engine.Color;
import com.practica1.engine.Graphics;
import com.practica1.engine.TouchEvent;

/**
 * GameObject Circulo, encapsula la info y comportamientos relativos a un circulo del tablero
 */
public class Circle {
    private TextObject id;
    private Color color;
    private boolean uncovered;
    private boolean colorblind;
    private int circleRadius;
    private Vector2 pos;
    private Graphics graphics;

    /**
     * @param graphics     Objecto graphics del motor
     * @param pos          Posicion del circulo
     * @param circleRadius Radio del circulo
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

    /**
     * Renderizado
     */
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

    /**
     * Manejo de input
     *
     * @param event
     * @return Si se ha pulsado el objeto
     */
    public boolean handleInput(TouchEvent event) {
        int touchX = event.x;
        int touchY = event.y;

        if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN) {

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
