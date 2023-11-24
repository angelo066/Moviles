package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase del Tablero principal.
 * Esta clase gestiona toda la logica del juego
 * Elige la combinacion ganadora
 * Despliega los colores que puede usar el jugador
 * Gestiona los intentos que tiene el jugador
 * Comprueba en cada intento si ha ganado o perdido
 */
public class TabObject extends GameObject {
    private static final boolean DEBUG = true;
    private final int numWindowDivisions = 12; // Divisiones de la pantalla (para calculos relativos de posiciones)
    boolean colorBlind = false; // Para el modo daltonismo
    private int numColors; // Numero de colores disponibles en la partida
    private int numSquares; // Numero de casillas que tiene disponible el intento
    private int numAttempts; // Numero de inentos disponibles por partida
    private int actualAttempt = 0; // Intento actual del jugador
    Difficulty mode; // Modo actual de dificultad
    private final int vOFFSET = 40;
    private final int circleRadius = 50;
    private Attempt[] tab; // Array de intentos
    private Color[] combinationWin; // Combinacion de colores ganadora
    private CircleObject[] chosenColors; // Array de circulos para los colores disponibles
    private Vector2[] posAttempts; // Posiciones (en Y) que debe tener cada inento
    Vector2 posChosenColors; // Posicion de los colores disponibles
    private CircleObject selectedCircle; //Circulo que elegimos al clickar
    private int currentPos = 0; //Ciruclo al que le toca recibir color
    private Random rand; // Instancia del random
    private boolean finish = false;

    /**
     * @param e           Engine de la aplicacion
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     * @param mode        Modo de dificultad
     */
    public TabObject(Engine e, int sceneWidth, int sceneHeight, Difficulty mode) {
        super(e, sceneWidth, sceneHeight);

        posAttempts = new Vector2[numWindowDivisions - 2];

        int offset = sceneHeight / numWindowDivisions;
        for (int i = 0; i < numWindowDivisions - 2; i++)
            posAttempts[i] = new Vector2(0, (i + 1) * offset);

        posChosenColors = new Vector2(0, offset * (numWindowDivisions - 1));

        // Asignamos la dificultad
        this.mode = mode;
        setConfiguration(mode);

        rand = new Random();
    }

    @Override
    public void init() {
        Font font = engine.getGraphics().newFont("Nexa.ttf", 50, false, false);

        // Variables para posiciones
        int totalWidth = numColors * circleRadius * 2;
        int totalWidthIntent = numSquares * circleRadius * 2;
        int spaceToEachSide = (sceneWidth - totalWidth) / 2;
        int spaceToEachSideIntent = (sceneWidth - totalWidthIntent) / 2;

        // Rellenamos el tablero de intentos
        tab = new Attempt[numAttempts];

        for (int i = 0; i < tab.length; i++) {

            // Inicializamos los parametros y objetos del Intento
            tab[i] = new Attempt();
            tab[i].combination = new CircleObject[numSquares];
            tab[i].checks = new CheckObject(engine, sceneWidth, sceneHeight, numSquares, posAttempts[i]);

            Vector2 posn = new Vector2(sceneWidth / 14, posAttempts[i].y);

            tab[i].textNumber = new TextObject(engine, sceneWidth, sceneHeight, posn, font, String.valueOf((i + 1)), Color.BLACK);

            // Para cada casilla creamos un circulo dentro de la lista de circulos del Intento
            for (int j = 0; j < numSquares; j++) {
                int x = spaceToEachSideIntent + j * (circleRadius * 2);
                Vector2 pos = new Vector2(x, posAttempts[i].y);
                tab[i].combination[j] = new CircleObject(engine, sceneWidth, sceneHeight, pos, i, font);
                tab[i].combination[j].setColor(Color.LIGHT_GRAY);
            }
        }

        // Creamos los circulos de los colores elegidos para la partida (Los que puede usar el jugador)
        chosenColors = new CircleObject[numColors];

        // Elegimos los N primeros colores del enum Color
        for (int i = 0; i < numColors; i++) {
            Vector2 pos = new Vector2(spaceToEachSide + i * (circleRadius * 2), posChosenColors.y);
            chosenColors[i] = new CircleObject(engine, sceneWidth, sceneHeight, pos, i + 1, font);
            chosenColors[i].uncover(true);
            chosenColors[i].setColor(Color.values()[i + 1]);
        }

        // Elegimos una combinacion ganadora aleatoria
        // Dependiendo de la dificultad elegimos combinacion con repeticion o sin repeticion
        if (mode == Difficulty.HARD || mode == Difficulty.IMPOSSIBLE) combinationRep();
        else combinationNoRep();
    }

    /**
     * Elige una combinacion aleatoria de colores con repeticion
     */
    private void combinationRep() {
        combinationWin = new Color[numSquares];

        for (int i = 0; i < combinationWin.length; i++) {

            int index = rand.nextInt(numColors + 1);

            //Mientras sea NO_COLOR
            while (index == 0) index = rand.nextInt(numColors + 1);

            Color c = Color.values()[index];

            combinationWin[i] = c;
        }

        //Debug de la combinacion ganadora
        if (this.DEBUG) {
            for (int i = 0; i < combinationWin.length; i++)
                System.out.println(combinationWin[i]);
        }
    }

    /**
     * Elige una combinacion aleatoria de colores sin repeticion
     */
    private void combinationNoRep() {
        combinationWin = new Color[numSquares];

        // Creamos una lista con todos los colores disponibles
        ArrayList<Color> colorsAux = new ArrayList<>();
        for (int i = 0; i < numColors + 1; i++)
            colorsAux.add(Color.values()[i]);

        // Cogemos colores aleatorios de la lista
        for (int i = 0; i < numSquares; i++) {

            // Cogemos un color aleatorio de la lista y lo quitamos
            int index = rand.nextInt(colorsAux.size());

            //Mientras sea N0_COLOR
            while (index == 0) index = rand.nextInt(colorsAux.size());

            Color c = colorsAux.get(index);
            colorsAux.remove(index);

            //Asignamos el color
            combinationWin[i] = c;
        }

        //Debug de la combinacion ganadora
        if (this.DEBUG) {
            for (int i = 0; i < combinationWin.length; i++) {
                System.out.println(combinationWin[i]);
            }
        }
    }

    @Override
    public boolean handleInput(TouchEvent touchEvent) {
        handleCombination(touchEvent);
        handleColors(touchEvent);
        return finish;
    }

    /**
     * Procesa el input sobre la combinacion del intento actual
     */
    private void handleCombination(TouchEvent touchEvent) {

        int attemptLong = tab[actualAttempt].combination.length;

        for (int i = 0; i < attemptLong; i++) {

            CircleObject c = tab[actualAttempt].combination[i];

            if (c.handleInput(touchEvent)) {
                c.uncover(false);
                c.setColor(Color.LIGHT_GRAY);
                if (i < currentPos) currentPos = i;
            }
        }
    }

    /**
     * Procesa el input sobre los colores disponibles a elegir
     */
    private void handleColors(TouchEvent touchEvent) {

        boolean selected = false;

        //Comprobacion del input dentro de un circulo
        for (int i = 0; i < chosenColors.length && !selected; i++) {

            selected = chosenColors[i].handleInput(touchEvent);

            //Si hemos elegido uno distinto al que ya teniamos elegido
            if (selected && chosenColors[i] != selectedCircle)
                selectedCircle = chosenColors[i];

        }

        CircleObject chosenCircle = tab[actualAttempt].combination[currentPos];

        if (selectedCircle != null) {

            //Comprobamos si es uno
            if (!chosenCircle.getUncovered()) tab[actualAttempt].coloredCircles++;

            //Cambiamos el color del combinacion al color del circulo seleccionado previamente
            chosenCircle.uncover(true);
            chosenCircle.setColor(selectedCircle.getColor());
            selectedCircle = null;
            currentPos++;

            //Vamos hasta la primera posicion libre
            while (currentPos < numSquares && tab[actualAttempt].combination[currentPos].getColor() != Color.LIGHT_GRAY)
                currentPos++;

            //Comprueba si hemos ganado o hay que seguir intentando
            if (currentPos == numSquares) {
                currentPos = 0;
                checkAttempt();
            }
        }

    }

    /**
     * Comprueba si al rellenar un intento por completo, se pasa al siguiente o hemos ganado
     */
    public void checkAttempt() {

        ArrayList<Integer> indexesComWin = new ArrayList<>();
        ArrayList<Integer> indexesCom = new ArrayList<>();

        //Recorremos el intento viendo el numero de colores acertados en posicion
        for (int i = 0; i < numSquares; i++) {
            // Se acierta color y posicion
            if (tab[actualAttempt].combination[i].getColor() == combinationWin[i])
                tab[actualAttempt].checksPos++;
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
                if (tab[actualAttempt].combination[indexesCom.get(j)].getColor() == combinationWin[indexesComWin.get(i)]) {
                    tab[actualAttempt].checksColor++;
                    indexesComWin.remove(i);
                    indexesCom.remove(j);
                    found = true;
                    i--;
                    j--;
                }
            }
        }

        // Pasamos la info de los aciertos al objeto que los renderiza
        tab[actualAttempt].checks.setCirculos(tab[actualAttempt]);

        boolean win = false;

        // Si ha acertado todas ha ganado
        if (tab[actualAttempt].checksPos == numSquares)
            win = true;
        else
            actualAttempt++;

        // Pasamos a la escena final si hemos agotado todos los intentos o hemos ganado
        if (actualAttempt == numAttempts || win) {
            //+ 1 porque empezamos en 0
            engine.setScene(new EndScreen(combinationWin, win, numSquares, mode, actualAttempt + 1, colorBlind));
            finish = true;
        }

    }

    @Override
    public void render() {
        // INTENTOS
        for (int i = 0; i < numAttempts; i++)
            drawAttempt(i);

        // COLORES DISPONIBLES
        drawAvaliableColors();
    }

    /**
     * Renderiza un intento entero (fila)
     */
    private void drawAttempt(int i) {
        // RECTANGULO
        engine.getGraphics().setColor(Color.GREY.getValue());
        int y = posAttempts[i].y - vOFFSET / 2;
        engine.getGraphics().drawRoundRectangle(0, y, sceneWidth - 3, circleRadius * 2 + vOFFSET, 20);

        // CIRCULOS
        for (int j = 0; j < numSquares; j++)
            tab[i].combination[j].render();


        // INDICADOR DE ACIERTOS
        tab[i].checks.render();

        // INDICE DEL INTENTO
        tab[i].textNumber.render();

        // LINEAS DE DECORACION
        int starX = sceneWidth / 6;
        int finX = (sceneWidth / 6) * 5;
        engine.getGraphics().setColor(Color.BLACK.getValue());
        engine.getGraphics().drawLine(starX, posAttempts[i].y, starX, posAttempts[i].y + 100);
        engine.getGraphics().drawLine(finX, posAttempts[i].y, finX, posAttempts[i].y + 100);

    }

    /**
     * Renderiza los colores que tenemos disponibles para la partida
     */
    private void drawAvaliableColors() {

        // RECTANGULO DE FONDO
        engine.getGraphics().setColor(Color.GREY.getValue());
        engine.getGraphics().fillRectangle(0, posChosenColors.y - vOFFSET / 2, sceneWidth, circleRadius * 2 + vOFFSET);

        // CIRCULOS
        for (int i = 0; i < numColors; i++) {
            chosenColors[i].render();
        }
    }

    /**
     * Cambia el modo daltonismo
     */
    public void colorblind(boolean d) {

        colorBlind = d;
        // Recorremos todo el tablero cambiando el modo a todos los circulos
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < numSquares; j++) {
                tab[i].combination[j].colorblind(d);
            }
        }

        // Recorremos los colores de abajo
        for (int i = 0; i < numColors; i++) {
            chosenColors[i].colorblind(d);
        }
    }

    /**
     * @return Devuelve los intentos restantes
     */
    public int getRemainingAttempts() {
        return numAttempts - actualAttempt;
    }

    /**
     * Asigna la dificultad al juego
     */
    public void setConfiguration(Difficulty modo) {
        switch (modo) {
            case EASY:
                numColors = 4;
                numSquares = 4;
                numAttempts = 6;
                break;
            case MEDIUM:
                numColors = 6;
                numSquares = 4;
                numAttempts = 8;
                break;
            case HARD:
                numColors = 8;
                numSquares = 5;
                numAttempts = 10;
                break;
            case IMPOSSIBLE:
                numColors = 9;
                numSquares = 6;
                numAttempts = 10;
                break;

        }
    }

}
