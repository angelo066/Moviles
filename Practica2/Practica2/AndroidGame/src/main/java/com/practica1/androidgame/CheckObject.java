package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;

/**
 * Clase que se encarga de dar pistas al jugador
 */
public class CheckObject extends GameObject {

    private int numBoxes;
    private Vector2 pos;
    private int numRightColors = 0, numRightPositions = 0;
    private final int radius = 15;


    /**
     * @param e           Engine de la aplicacion
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     * @param numBoxes    Numero de casillas de un intento
     * @param pos         Posicion
     */
    public CheckObject(Engine e, int sceneWidth, int sceneHeight, int numBoxes, Vector2 pos) {

        super(e, sceneWidth, sceneHeight);
        this.numBoxes = numBoxes;
        this.pos = pos;
    }

    @Override
    public void render() {

        int numRightPos = numRightPositions;
        int numRightCol = numRightColors;

        int offsetX = 30, offsetY = 50;

        int iniX = sceneWidth - (int) (Math.ceil((numBoxes / 2.0)) * (offsetX + radius)) - offsetX / 2;

        for (int i = 0; i < numBoxes / 2; i++) {

            int posX = iniX + (offsetX + radius) * i;

            if (numRightPos > 0) {
                engine.getGraphics().setColor(Color.BLACK.getValue());
                engine.getGraphics().fillCircle(posX, pos.y, radius);
                numRightPos--;
            } else if (numRightCol > 0) {
                engine.getGraphics().setColor(Color.BLACK.getValue());
                engine.getGraphics().drawCircle(posX, pos.y, radius);
                numRightCol--;
            } else {
                engine.getGraphics().setColor(Color.GREY.getValue());
                engine.getGraphics().fillCircle(posX, pos.y, radius);

            }
        }

        for (int i = numBoxes / 2; i < numBoxes; i++) {

            int posX = iniX + (offsetX + radius) * (i - numBoxes / 2);
            int posYNextLine = pos.y + radius + offsetY; //Variable para los circulos de la parte inferior

            if (numRightPos > 0) {
                engine.getGraphics().setColor(Color.BLACK.getValue());
                engine.getGraphics().fillCircle(posX, posYNextLine, radius);
                numRightPos--;
            } else if (numRightCol > 0) {
                engine.getGraphics().setColor(Color.BLACK.getValue());
                engine.getGraphics().drawCircle(posX, posYNextLine, radius);
                numRightCol--;
            } else {
                engine.getGraphics().setColor(Color.GREY.getValue());
                engine.getGraphics().fillCircle(posX, posYNextLine, radius);

            }
        }

    }

    /**
     * Establece la informacion de los aciertos del intento dado
     *
     * @param attempt
     */
    public void setCirculos(Attempt attempt) {
        //Las dos cosas
        numRightPositions = attempt.checksPos;
        //Solo esta bien el color pero no la posicion
        numRightColors = attempt.checksColor;
    }

}
