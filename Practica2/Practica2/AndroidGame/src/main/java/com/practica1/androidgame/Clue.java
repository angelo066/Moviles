package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.Scene;

public class Clue {

    private int numFoundCircles;
    private int numFoundColors;
    private Scene scene;
    private Vector2 pos;
    private Vector2 size;
    private int circleRadius;
    private int numColorsPerAttempt;
    private int startPositionX;
    private int startPositionY;
    private int offsetBetweenCirclesX;
    private int offsetBetweenCirclesY;

    public Clue(Scene scene, Vector2 pos, Vector2 size, int numColorsPerAttempt) {
        this.numFoundCircles = 0;
        this.numFoundColors = 0;
        this.scene = scene;
        this.pos = pos;
        this.size = size;
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

    public void render() {
        Graphics graphics = scene.getEngine().getGraphics();

        int numRightCir = numFoundCircles;
        int numRightCol = numFoundColors;
        int x = startPositionX + pos.x;
        int y = startPositionY + pos.y;

        int aux = (int) Math.ceil(numColorsPerAttempt / 2.0);
        for (int i = 0; i < aux; i++) {

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

        x = startPositionX + pos.x;
        y += (circleRadius * 2) + offsetBetweenCirclesY;

        for (int i = aux; i < numColorsPerAttempt; i++) {
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

    public void setClue(int numFoundCircles, int numFoundColors) {
        this.numFoundCircles = numFoundCircles;
        this.numFoundColors = numFoundColors;
    }
}
