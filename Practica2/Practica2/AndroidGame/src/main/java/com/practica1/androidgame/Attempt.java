package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;

public class Attempt {
    private Circulo[] combination;
    private TextObject attemptNumber;
    private Clue clue;
    private Scene scene;
    private Vector2 pos;
    private Vector2 size;
    private int numDivisions;
    private int widthPerDivision;
    private int uncoveredCircles;
    private int currentIndex;
    private boolean correctCombination;

    public Attempt(Scene scene, int numColorsPerAttempt, int id, Vector2 pos, Vector2 size) {
        this.scene = scene;
        this.pos = pos;
        this.size = size;
        this.uncoveredCircles = 0;
        this.currentIndex = 0;
        this.correctCombination = false;

        this.numDivisions = 6;
        this.widthPerDivision = size.x / numDivisions;

        int circleRadius = 50;
        int offsetBetweenCircle = circleRadius / 3;
        int widthCombination = (2 * circleRadius * numColorsPerAttempt) + ((numColorsPerAttempt - 1) * offsetBetweenCircle);
        int startPosition = (pos.x + widthPerDivision) + ((size.x - (widthPerDivision * 2) - widthCombination) / 2);

        this.combination = new Circulo[numColorsPerAttempt];
        for (int i = 0; i < numColorsPerAttempt; i++) {
            int x = startPosition + (offsetBetweenCircle * i) + (circleRadius * 2 * i);
            int y = pos.y + ((size.y - (circleRadius * 2)) / 2);
            this.combination[i] = new Circulo(scene, new Vector2(x, y), circleRadius);
        }

        this.attemptNumber = new TextObject(scene.getEngine(), scene.getWidth(), scene.getHeight(),
                new Vector2(pos.x + widthPerDivision / 2, pos.y + size.y / 2), "Nexa.ttf",
                String.valueOf(id), Color.BLACK, 50, false, false);
        this.attemptNumber.center();

        clue = new Clue(this.scene, new Vector2(pos.x + (widthPerDivision * (numDivisions - 1)), pos.y), new Vector2(widthPerDivision, size.y), numColorsPerAttempt);

    }

    public void render() {
        Graphics graphics = scene.getEngine().getGraphics();

        graphics.setColor(Color.GREY.getValue());
        graphics.drawRoundRectangle(pos.x, pos.y, size.x, size.y, 20);

        attemptNumber.render();

        graphics.setColor(Color.BLACK.getValue());
        graphics.drawLine(pos.x + widthPerDivision, pos.y + 10, pos.x + widthPerDivision, pos.y + size.y - 10);
        graphics.drawLine(pos.x + widthPerDivision * 5, pos.y + 10, pos.x + widthPerDivision * 5, pos.y + size.y - 10);


        for (int i = 0; i < combination.length; i++)
            combination[i].render();

        if (uncoveredCircles == combination.length)
            clue.render();
    }

    public void handleInput(TouchEvent touchEvent) {
        for (int i = 0; i < combination.length; i++) {
            if (!combination[i].getUncovered()) continue;

            if (combination[i].handleInput(touchEvent)) {
                combination[i].setUncovered(false);
                uncoveredCircles--;
                if (i < currentIndex) currentIndex = i;
            }
        }
    }

    public void setCircle(Color color, Color[] winningCombination) {
        combination[currentIndex].setUncovered(true);
        combination[currentIndex].setColor(color);
        uncoveredCircles++;

        for (int i = currentIndex + 1; i < combination.length; i++) {
            if (!combination[i].getUncovered()) {
                currentIndex = i;
                break;
            }
        }

        //Set Clue
        if (uncoveredCircles == winningCombination.length) {
            int numFoundCircles = 0, numFoundColors = 0;

            ArrayList<Integer> indexesComWin = new ArrayList<>();
            ArrayList<Integer> indexesCom = new ArrayList<>();

            //Recorremos el intento viendo el numero de colores acertados en posicion
            for (int i = 0; i < winningCombination.length; i++) {
                // Se acierta color y posicion
                if (combination[i].getColor() == winningCombination[i])
                    numFoundCircles++;

                else {
                    indexesComWin.add(i);
                    indexesCom.add(i);
                }
            }

            //Recorremos los colores de la combinacion ganadora que no han sido acertados
            //para ver si el intento contiene dichos colores
            for (int i = 0; i < indexesComWin.size(); i++) {
                boolean found = false;
                for (int j = 0; j < indexesCom.size() && !found; j++) {
                    if (combination[indexesCom.get(j)].getColor() == winningCombination[indexesComWin.get(i)]) {
                        numFoundColors++;
                        indexesComWin.remove(i);
                        indexesCom.remove(j);
                        found = true;
                        i--;
                        j--;
                    }
                }
            }

            clue.setClue(numFoundCircles, numFoundColors);

            if (numFoundCircles == winningCombination.length)
                correctCombination = true;
        }
    }

    public int getUncoveredCircles() {
        return uncoveredCircles;
    }

    public boolean isCorrectCombination() {
        return correctCombination;
    }

    public void setColorblind(boolean set){
        for(int i = 0;i<combination.length;i++)
            combination[i].setColorblind(set);
    }
}


