package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Graphics;

import java.io.Serializable;

public class Clue  implements Serializable {
    private int numFoundCircles;
    private int numFoundColors;
    private transient Graphics graphics;
    private Vector2 pos;
    private int circleRadius;
    private int numColorsPerAttempt;


    private int startPositionX;
    private int startPositionY;
    private int offsetBetweenCirclesX;
    private int offsetBetweenCirclesY;

    /**
     * @param graphics            Objeto graphics del motor
     * @param pos                 Posicion del objeto
     * @param size                Tamanio del objeto
     * @param numColorsPerAttempt Numero de casillas del intento
     */
    public Clue(Graphics graphics, Vector2 pos, Vector2 size, int numColorsPerAttempt) {
        this.numFoundCircles = 0;
        this.numFoundColors = 0;
        this.graphics = graphics;
        this.pos = pos;
        this.circleRadius = 15;
        this.numColorsPerAttempt = numColorsPerAttempt;

        int numCirclesPerRow = (int) Math.ceil(numColorsPerAttempt / 2.0);

        this.offsetBetweenCirclesX = circleRadius * 2;
        int widthClues = (numCirclesPerRow * circleRadius * 2) + ((numCirclesPerRow - 1) * offsetBetweenCirclesX);
        this.startPositionX = (size.x - widthClues) / 2;

        this.offsetBetweenCirclesY = circleRadius;
        int heightClues = (2 * circleRadius * 2) + offsetBetweenCirclesY;
        this.startPositionY = (size.y - heightClues) / 2;
    }

    /**
     * Render del objeto
     */
    public void render() {
        int numRightCir = numFoundCircles;
        int numRightCol = numFoundColors;

        int x = startPositionX + pos.x;
        int y = startPositionY + pos.y;

        int aux = (int) Math.ceil(numColorsPerAttempt / 2.0);
        for (int i = 0; i < numColorsPerAttempt; i++) {
            if (i == aux) { //segunda fila
                y += (circleRadius * 2) + offsetBetweenCirclesY;
                x = startPositionX + pos.x;
            }

            if (numRightCir > 0) {
                graphics.setColor(Color.BLACK.getValue());
                graphics.fillCircle(x, y, circleRadius);
                numRightCir--;
            } else if (numRightCol > 0) {
                graphics.setColor(Color.BLACK.getValue());
                graphics.drawCircle(x, y, circleRadius);
                numRightCol--;
            } else {
                graphics.setColor(Color.GREY.getValue());
                graphics.fillCircle(x, y, circleRadius);
            }

            x += (circleRadius * 2) + offsetBetweenCirclesX;
        }

    }

    /**
     * Establece el numero de aciertos del intento para su representacion
     *
     * @param numFoundCircles Numero de aciertos en posicion y color
     * @param numFoundColors  Numero de aciertos en color pero no en posicion
     */
    public void setClue(int numFoundCircles, int numFoundColors) {
        this.numFoundCircles = numFoundCircles;
        this.numFoundColors = numFoundColors;
    }

    /**
     * Traslada el objeto
     *
     * @param translateX
     * @param translateY
     */
    public void translate(int translateX, int translateY) {
        pos.x += translateX;
        pos.y += translateY;
    }

    /**
     * @return Posicion del objeto
     */
    public Vector2 getPos() {
        return pos;
    }

    public void setGraphics(Graphics graphics) {
        this.graphics = graphics;
    }
}
