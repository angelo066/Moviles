package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

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
    private boolean DEBUG = true;
    private final int NUM_WINDOW_DIVISIONS = 12; // Divisiones de la pantalla (para calculos relativos de posiciones)
    boolean COLORBLIND = false; // Para el modo daltonismo
    private int NUM_COLORS; // Numero de colores disponibles en la partida
    private int NUM_SQUARES; // Numero de casillas que tiene disponible el intento
    private int NUM_ATTEMPTS; // Numero de inentos disponibles por partida
    private int ACTUAL_ATTEMPT = 0; // Intento actual del jugador
    Difficulty MODE; // Modo actual de dificultad

    private final int V_OFFSET = 40;
    private final int CIRCLE_RADIUS = 50;

    private Attempt[] TAB; // Array de intentos
    private Color[] combination_win; // Combinacion de colores ganadora
    private CircleObject[] chosen_colors; // Array de circulos para los colores disponibles
    private Vector2[] pos_attempts; // Posiciones (en Y) que debe tener cada inento
    Vector2 pos_chosen_colors; // Posicion de los colores disponibles

    private CircleObject selectedCircle; //Circulo que elegimos al clickar
    private int currentPos = 0; //Ciruclo al que le toca recibir color

    private Random rand; // Instancia del random
    boolean win = false;


    public TabObject(Engine e, int sceneWidth, int sceneHeight, Difficulty MODE) {
        super(e, sceneWidth, sceneHeight);

        pos_attempts = new Vector2[NUM_WINDOW_DIVISIONS - 2];

        int separacion = sceneHeight / NUM_WINDOW_DIVISIONS;
        for (int i = 0; i < NUM_WINDOW_DIVISIONS - 2; i++) {
            pos_attempts[i] = new Vector2(0, (i + 1) * separacion);
        }
        pos_chosen_colors = new Vector2(0, separacion * (NUM_WINDOW_DIVISIONS - 1));

        // Asignamos la dificultad
        this.MODE = MODE;
        configuracion(MODE);

        rand = new Random();
        initTablero();
    }

    @Override
    public void init() {

    }

    @Override
    public void render()
    {
        // INTENTOS
        for (int i = 0; i < NUM_ATTEMPTS; i++) {
            drawAttempt(i);
        }

        // COLORES DISPONIBLES
        drawAvaliableColors();


    }


    /**
     * Inicializa todos los parametros y objetos del tablero de juego
     */
    public void initTablero() {

        Font font = engine.getGraphics().newFont("Nexa.ttf", 50, false, false);

        // Variables para posiciones
        int totalWidth = NUM_COLORS * CIRCLE_RADIUS * 2;
        int totalWidthIntent = NUM_SQUARES * CIRCLE_RADIUS * 2;
        int spaceToEachSide = (sceneWidth - totalWidth) / 2;
        int spaceToEachSideIntent = (sceneWidth - totalWidthIntent) / 2;

        // Rellenamos el tablero de intentos
        TAB = new Attempt[NUM_ATTEMPTS];
        for (int i = 0; i < TAB.length; i++)
        {
            // Inicializamos los parametros y objetos del Intento
            TAB[i] = new Attempt();
            TAB[i].combination = new CircleObject[NUM_SQUARES];
            TAB[i].checks = new CheckObject(engine,sceneWidth,sceneHeight, NUM_SQUARES, pos_attempts[i]);
            int num_intento = i + 1;
            Vector2 posn = new Vector2(sceneWidth/14, pos_attempts[i].y);
            TAB[i].textNumber = new TextObject(engine,sceneWidth,sceneHeight, posn, font, String.valueOf(num_intento), Color.BLACK);

            // Para cada casilla creamos un circulo dentro de la lista de circulos del Intento
            for (int j = 0; j < NUM_SQUARES; j++)
            {
                int x = spaceToEachSideIntent + j * (CIRCLE_RADIUS * 2);
                Vector2 pos = new Vector2(x, pos_attempts[i].y);
                TAB[i].combination[j] = new CircleObject(engine,sceneWidth,sceneHeight, pos, i, font);
                TAB[i].combination[j].setColor(Color.NO_COLOR);
            }
        }

        // Creamos los circulos de los colores elegidos para la partida (Los que puede usar el jugador)
        chosen_colors = new CircleObject[NUM_COLORS];
        // Elegimos los N primeros colores del enum Color
        for (int i = 0; i < NUM_COLORS; i++)
        {
            int x = spaceToEachSide + i * (CIRCLE_RADIUS * 2);
            Vector2 pos = new Vector2(x, pos_chosen_colors.y);
            chosen_colors[i] = new CircleObject(engine,sceneWidth,sceneHeight, pos, i + 1, font);
            chosen_colors[i].uncover(true);
            chosen_colors[i].setColor(Color.values()[i + 1]);
        }

        // Elegimos una combinacion ganadora aleatoria
        // Dependiendo de la dificultad elegimos combinacion con repeticion o sin repeticion
        if (MODE == Difficulty.HARD || MODE == Difficulty.IMPOSSIBLE) combinacionConRep();
        else combinacionSinRep();

    }

    /**
     * Elige una combinacion aleatoria de colores con repeticion
     */
    private void combinacionConRep() {
        combination_win = new Color[NUM_SQUARES];

        for (int i = 0; i < combination_win.length; i++) {

            int index = rand.nextInt(NUM_COLORS + 1);
            //Mientras sea NO_COLOR
            while (index == 0) index = rand.nextInt(NUM_COLORS + 1);

            Color c = Color.values()[index];

            combination_win[i] = c;
        }

        //Debug de la combinacion ganadora
        if(this.DEBUG)
        {
            for (int i = 0; i < combination_win.length; i++) {
                System.out.println(combination_win[i]);
            }
        }
    }

    /**
     * Elige una combinacion aleatoria de colores sin repeticion
     */
    private void combinacionSinRep() {
        combination_win = new Color[NUM_SQUARES];

        // Creamos una lista con todos los colores disponibles
        ArrayList<Color> coloresAux = new ArrayList<>();
        for (int i = 0; i < NUM_COLORS + 1; i++)
            coloresAux.add(Color.values()[i]);

        // Cogemos colores aleatorios de la lista
        for (int i = 0; i < NUM_SQUARES; i++) {

            // Cogemos un color aleatorio de la lista y lo quitamos
            int index = rand.nextInt(coloresAux.size());

            //Mientras sea N0_COLOR
            while (index == 0) index = rand.nextInt(coloresAux.size());

            Color c = coloresAux.get(index);
            coloresAux.remove(index);
            //Asignamos el color
            combination_win[i] = c;
        }

        //Debug de la combinacion ganadora
        if(this.DEBUG)
        {
            for (int i = 0; i < combination_win.length; i++) {
                System.out.println(combination_win[i]);
            }
        }
    }

    /**
     * Comprueba si al rellenar un intento por completo, se pasa al siguiente o hemos ganado
     */
    public void checkIntento()
    {
        boolean win = false;

        // Volcamos la combinacion ganadora en una lista Auxiliar
        ArrayList<Color> colores_elegidos = new ArrayList<>();
        for (int i = 0; i < NUM_SQUARES; i++) {
            colores_elegidos.add(combination_win[i]);
        }

        // Tenemos que comprobar la fila del tablero que coincida con el intento actual
        for (int i = 0; i < NUM_SQUARES; i++) {
            // Acierta color y posicion
            if (TAB[ACTUAL_ATTEMPT].combination[i].getColor() == combination_win[i]) {
                TAB[ACTUAL_ATTEMPT].checks_pos++;
            } else {   //Comprobamos si acierta solo color
                // Comprobar colores -> para cada elemento de la comb del jugador, recorremos la lista hasta encontrar el color que buscamos
                int j = 0;
                boolean find = false;
                while (j < colores_elegidos.size() && !find) {

                    if (TAB[ACTUAL_ATTEMPT].combination[i].getColor() == colores_elegidos.get(j)) {
                        // Eliminamos el color de la lista
                        colores_elegidos.remove(j);
                        TAB[ACTUAL_ATTEMPT].checks_color++;
                        find = true;
                    }
                    j++;
                }
            }
        }

        // Pasamos la info de los aciertos al objeto que los renderiza
        TAB[ACTUAL_ATTEMPT].checks.setCirculos(TAB[ACTUAL_ATTEMPT]);

        // Si ha acertado todas ha ganado
        if (TAB[ACTUAL_ATTEMPT].checks_pos == NUM_SQUARES)
            win = true;
        else ACTUAL_ATTEMPT++;

        // Pasamos a la escena final si hemos agotado todos los intentos o hemos ganado
        if (ACTUAL_ATTEMPT == NUM_ATTEMPTS || win) {                                        //+ 1 porque empezamos en 0
            engine.setScene(new EndScreen(combination_win, win, NUM_SQUARES, MODE, ACTUAL_ATTEMPT + 1, COLORBLIND));
        }
    }

    /**
     * Asigna la dificultad al juego
     */
    public void configuracion(Difficulty modo) {
        switch (modo) {
            case EASY:
                NUM_COLORS = 4;
                NUM_SQUARES = 4;
                NUM_ATTEMPTS = 6;
                break;
            case MEDIUM:
                NUM_COLORS = 6;
                NUM_SQUARES = 4;
                NUM_ATTEMPTS = 8;
                break;
            case HARD:
                NUM_COLORS = 8;
                NUM_SQUARES = 5;
                NUM_ATTEMPTS = 10;
                break;
            case IMPOSSIBLE:
                NUM_COLORS = 9;
                NUM_SQUARES = 6;
                NUM_ATTEMPTS = 10;
                break;

        }
    }

    @Override
    public boolean handleInput(TouchEvent touchEvent) {
        handleCombination(touchEvent);

        handleColors(touchEvent);

        return ACTUAL_ATTEMPT == NUM_ATTEMPTS || win;
    }

    /**
     * Procesa el input sobre la combinacion del intento actual
     */
    private void handleCombination(TouchEvent touchEvent) {

        boolean removeColor = false;
        int intentoLong = TAB[ACTUAL_ATTEMPT].combination.length;

        for (int i = 0; i < intentoLong; i++) {

            CircleObject c = TAB[ACTUAL_ATTEMPT].combination[i];

            if (c.handleInput(touchEvent)) {
                c.uncover(false);
                c.setColor(Color.NO_COLOR);
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
        for (int i = 0; i < chosen_colors.length && !selected; i++) {
            selected = chosen_colors[i].handleInput(touchEvent);

            //Si hemos elegido uno distinto al que ya teniamos elegido
            if (selected && chosen_colors[i] != selectedCircle) {

                selectedCircle = chosen_colors[i];
            }
        }

        int attemptSize = TAB[ACTUAL_ATTEMPT].combination.length;
        CircleObject chosenCircle = null;

        chosenCircle = TAB[ACTUAL_ATTEMPT].combination[currentPos];

        if (selectedCircle != null) {
            //Comprobamos si es uno
            if (!chosenCircle.getUncovered()) TAB[ACTUAL_ATTEMPT].coloredCircles++;

            //Cambiamos el color del combinacion al color del circulo seleccionado previamente
            chosenCircle.uncover(true);
            chosenCircle.setColor(selectedCircle.getColor());
            selectedCircle = null;
            currentPos++;

            //Vamos hasta la primera posicion libre
            while (currentPos < NUM_SQUARES && TAB[ACTUAL_ATTEMPT].combination[currentPos].getColor() != Color.NO_COLOR)
                currentPos++;

            //Comprueba si hemos ganado o hay que seguir intentando
            if (currentPos == NUM_SQUARES) {
                currentPos = 0;
                checkIntento();
            }
        }
    }

    /**
     * Renderiza un intento entero (fila)
     */
    private void drawAttempt(int i)
    {
        // RECTANGULO
        engine.getGraphics().setColor(Color.GREY);
        int y = pos_attempts[i].y - V_OFFSET / 2;
        engine.getGraphics().drawRoundRectangle(0, y, sceneWidth-3, CIRCLE_RADIUS * 2 + V_OFFSET, 50);

        // CIRCULOS
        for (int j = 0; j < NUM_SQUARES; j++) {
            TAB[i].combination[j].render();
        }

        // INDICADOR DE ACIERTOS
        TAB[i].checks.render();

        // INDICE DEL INTENTO
        TAB[i].textNumber.render();

        // LINEAS DE DECORACION
        int starX = sceneWidth / 6;
        int finX = (sceneWidth / 6) * 5;
        engine.getGraphics().drawLine(starX, pos_attempts[i].y ,starX, pos_attempts[i].y + 100);
        engine.getGraphics().drawLine(finX, pos_attempts[i].y ,finX, pos_attempts[i].y + 100);

    }

    /**
     * Renderiza los colores que tenemos disponibles para la partida
     */
    private void drawAvaliableColors() {

        // RECTANGULO DE FONDO
        engine.getGraphics().setColor(Color.GREY);
        engine.getGraphics().fillRectangle(0, pos_chosen_colors.y - V_OFFSET / 2, sceneWidth, CIRCLE_RADIUS * 2 + V_OFFSET);

        // CIRCULOS
        for (int i = 0; i < NUM_COLORS; i++) {
            chosen_colors[i].render();
        }
    }

    /**
     * Cambia el modo daltonismo
     */
    public void colorblind(boolean d) {

        COLORBLIND = d;
        // Recorremos todo el tablero cambiando el modo a todos los circulos
        for (int i = 0; i < TAB.length; i++) {
            for (int j = 0; j < NUM_SQUARES; j++) {
                TAB[i].combination[j].colorblind(d);
            }
        }

        // Recorremos los colores de abajo
        for (int i = 0; i < NUM_COLORS; i++) {
            chosen_colors[i].colorblind(d);
        }
    }

    public int getNUM_INTENTOS_RESTANTES(){return NUM_ATTEMPTS - ACTUAL_ATTEMPT;};

}
