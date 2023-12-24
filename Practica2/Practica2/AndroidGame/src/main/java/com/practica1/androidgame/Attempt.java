package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.Image;
import com.practica1.androidengine.TouchEvent;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que representa un intento en el juego
 */
public class Attempt  implements Serializable {
    private Circle[] combination;
    private TextObject attemptNumber;
    private Clue clue;
    private transient Graphics graphics;
    private Vector2 pos;
    private Vector2 size;
    private int numDivisions;
    private int widthPerDivision;
    private int uncoveredCircles;
    private int currentIndex;
    private boolean correctCombination;

    /**
     * @param graphics            Objeto graphics del motor
     * @param numColorsPerAttempt Numero de casillas del intento
     * @param id                  Numero del intento
     * @param pos                 Posicion del intento
     * @param size                Tamanio del intento
     */
    public Attempt(Graphics graphics, int numColorsPerAttempt, int id, Vector2 pos, Vector2 size) {
        this.graphics = graphics;
        this.pos = pos;
        this.size = size;
        this.uncoveredCircles = 0;
        this.currentIndex = 0;
        this.correctCombination = false;
        this.numDivisions = 6;
        this.widthPerDivision = size.x / numDivisions;


        //Crea los circulos
        int circleRadius = 50;
        int offsetBetweenCircle = circleRadius / 3;
        int widthCombination = (2 * circleRadius * numColorsPerAttempt) + ((numColorsPerAttempt - 1) * offsetBetweenCircle);
        int startPosition = (pos.x + widthPerDivision) + ((size.x - (widthPerDivision * 2) - widthCombination) / 2);

        this.combination = new Circle[numColorsPerAttempt];
        for (int i = 0; i < numColorsPerAttempt; i++) {
            int x = startPosition + (offsetBetweenCircle * i) + (circleRadius * 2 * i);
            int y = pos.y + ((size.y - (circleRadius * 2)) / 2);
            this.combination[i] = new Circle(graphics, new Vector2(x, y), circleRadius);
        }


        //Texto para el numero de intento
        int color = GameManager.getInstance().getCurrentSkinPalette().getColor2();
        this.attemptNumber = new TextObject(graphics, new Vector2(pos.x + widthPerDivision / 2, pos.y + size.y / 2), "Nexa.ttf",
                String.valueOf(id), color, 50, false, false);
        this.attemptNumber.center();


        //Objeto clue para las pistas
        clue = new Clue(graphics, new Vector2(pos.x + (widthPerDivision * (numDivisions - 1)), pos.y), new Vector2(widthPerDivision, size.y), numColorsPerAttempt);
    }

    /**
     * Render del intento
     */
    public void render() {

        //Recuadro
        graphics.setColor(Color.OPACITY_GREY.getValue());
        graphics.fillRoundRectangle(pos.x, pos.y, size.x, size.y, 20);
        graphics.setColor(Color.GREY.getValue());
        graphics.drawRoundRectangle(pos.x, pos.y, size.x, size.y, 20);

        //Numero de intento
        attemptNumber.render();

        //Lineas separadoras
        graphics.setColor(Color.BLACK.getValue());
        graphics.drawLine(pos.x + widthPerDivision, pos.y + 10, pos.x + widthPerDivision, pos.y + size.y - 10);
        graphics.drawLine(pos.x + widthPerDivision * 5, pos.y + 10, pos.x + widthPerDivision * 5, pos.y + size.y - 10);

        //Combinacion
        for (int i = 0; i < combination.length; i++)
            combination[i].render();

        //Si se ha descubierto toda la combinacion, renderizar pistas
        if (uncoveredCircles == combination.length)
            clue.render();
    }

    /**
     * Manejo de input del intento
     *
     * @param touchEvent
     */
    public void handleInput(TouchEvent touchEvent) {
        for (int i = 0; i < combination.length; i++) {
            //Si no se ha descubierto ese circulo
            if (!combination[i].getUncovered()) continue;

            //Si se toca un circulo del intento
            if (combination[i].handleInput(touchEvent)) {
                combination[i].setUncovered(false);
                uncoveredCircles--;
                if (i < currentIndex) currentIndex = i;
            }
        }
    }

    /**
     * Establece el siguiente circulo del intento a "color"
     * Establece el objeto pista si se han rellenado todos los circulos del intento
     *
     * @param color
     * @param winningCombination Combinacion ganadora
     */
    public void setCircle(Color color, Color[] winningCombination, Image imageRoute) {
        uncoveredCircles++;
        combination[currentIndex].setUncovered(true);
        combination[currentIndex].setColor(color);

        if(imageRoute != null)
            combination[currentIndex].setImage(imageRoute);

        for (int i = currentIndex + 1; i < combination.length; i++) {
            if (!combination[i].getUncovered()) {
                currentIndex = i;
                break;
            }
        }

        setClue(winningCombination);
    }

    /**
     * Establece el objeto pista
     *
     * @param winningCombination Combinacion ganadora
     */
    private void setClue(Color[] winningCombination) {
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

    /**
     * @return Numero de circulos descubiertos
     */
    public int getUncoveredCircles() {
        return uncoveredCircles;
    }

    /**
     * @return Si la combinacion del intento es la ganadora
     */
    public boolean isCorrectCombination() {
        return correctCombination;
    }

    /**
     * Establece el modo daltonicos
     *
     * @param set
     */
    public void setColorblind(boolean set) {
        for (int i = 0; i < combination.length; i++)
            combination[i].setColorblind(set);
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

        attemptNumber.translate(translateX, translateY);

        clue.translate(translateX, translateY);

        for (int i = 0; i < combination.length; i++)
            combination[i].translate(translateX, translateY);
    }

    /**
     * @return Posicion del objeto
     */
    public Vector2 getPos() {
        return pos;
    }

    public void load(Graphics graphics) {
        this.graphics = graphics;

        attemptNumber.load(graphics);

        for(int i=0; i < combination.length; i++){
            combination[i].load(graphics);
        }

        clue.setGraphics(graphics);

    }
}


