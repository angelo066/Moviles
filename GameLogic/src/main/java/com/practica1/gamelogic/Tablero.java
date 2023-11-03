package com.practica1.gamelogic;

import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

import java.util.Random;
import java.util.ArrayList;

import static com.practica1.gamelogic.colores.*;


// LOS COLORES A PARTIR DEL MARRON NO SE USAN PARA EL JUEGO
enum colores {
    NO_COLOR(0xFF333333),
    ROJO(0xFFFF0000),
    NARANJA(0xFFFF8000),
    AMARILLO(0xFFFFFF00),
    VERDE(0xFF00FF00),
    CYAN(0xFF00FFFF),
    AZUL(0XFF0000FF),
    MORADO(0xFFFF00FF),
    ROSA(0xFFFF0080),
    MARRON(0xFF804000),
    BLANCO(0xFFFFFFFF),
    GRIS(0xFFC6C6C6),
    GRIS_OSCURO(0xFF8C8C8C),
    NEGRO(0xFF000000);

    private int value;

    private colores(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }
}

enum Dificultad {
    FACIL,
    MEDIO,
    DIFICIL,
    IMPOSIBLE
}

class IntentoFila {

    public IntentoFila(){};
    Circulo[] combinacion = null;
    int aciertos_pos = 0;
    int aciertos_color = 0;

    Aciertos aciertos = null;

    Texto number;

    int coloredCircles = 0;
}


public class Tablero extends GameObject {
    private int N_COLORS;
    private IntentoFila[] tablero;
    private Circulo[] combinacion_ganadora;
    private Circulo[] colores_elegidos;
    private Random rand;

    private int NUM_CASILLAS;
    private int NUM_INTENTOS;

    private int INTENTO_ACTUAL = 0;
    private boolean repetion = false;

    private Vector2[] pos_intentos;
    Vector2 pos_cabecera;
    Vector2 pos_colores;

    private final int N_DIVISIONES_PANTALLA = 12;
    private final int H_OFFSET = 20;
    private final int V_OFFSET = 40;
    private final int RADIO_CIRCULO = 50;

    private Circulo selectedCircle; //Circulo que elegimos al clickar

    private int currentPos = 0; //Ciruclo al que le toca recibir color

    public Tablero(Engine e) {
        super(e);

        pos_cabecera = new Vector2(0,0);
        pos_intentos = new Vector2[N_DIVISIONES_PANTALLA - 2];
        int height = engine.getGraphics().getHeight(); // esto no dbeeria hacer falta pasar desde la escena
        int separacion = height / N_DIVISIONES_PANTALLA;
        for(int i = 0; i< N_DIVISIONES_PANTALLA - 2; i++)
        {
            pos_intentos[i] = new Vector2(0, (i+1) * separacion);
        }
        pos_colores = new Vector2(0, separacion * (N_DIVISIONES_PANTALLA-1));

        rand = new Random();
        initTablero();
    }

    @Override
    public void init() {

    }

    @Override
    public void render() {
        int h = engine.getGraphics().getSceneHeight();
        int w = engine.getGraphics().getSceneWidth();

        // Calcular el espacio total ocupado por las bolas y el espacio a cada lado
        int offset = 20;


        // Para cada intento
        for(int i = 0; i< NUM_INTENTOS; i++)
        {
            dibujaIntento(i);
        }

        // Los colores disponibles
        dibujaColoresDisponibles();


    }

    @Override
    public void update(double deltaTime) {

    }

    public void initTablero() {
        // Asignamos la dificultad
        configuracion(Dificultad.MEDIO);
        Font fontTitulo = engine.getGraphics().newFont("Nexa.ttf", 50, false, false);

        // Rellenamos el tablero
        tablero = new IntentoFila[NUM_INTENTOS];
        for (int i = 0; i < tablero.length; i++) {
            tablero[i] = new IntentoFila();
            tablero[i].combinacion = new Circulo[NUM_CASILLAS];
            tablero[i].aciertos = new Aciertos(engine,NUM_CASILLAS, pos_intentos[i]);
            int mierdon = i+1;
            tablero[i].number = new Texto(engine,pos_intentos[i], fontTitulo, String.valueOf(mierdon), NEGRO);

            for (int j = 0; j < NUM_CASILLAS; j++) {
                tablero[i].combinacion[j] = new Circulo(engine);
                tablero[i].combinacion[j].setColor(NO_COLOR);
            }
        }

        // Creamos los circulos de los colores elegidos para la partida
        colores_elegidos = new Circulo[N_COLORS];
        for(int i = 0; i< N_COLORS; i++)
        {
            colores_elegidos[i] = new Circulo(engine);
            colores_elegidos[i].setColor(colores.values()[i+1]);
        }

        if(repetion)combinacionConRep();
        else combinacionSinRep();

    }

    private void combinacionConRep() {
        combinacion_ganadora =  new Circulo[NUM_CASILLAS];

        for (int i = 0; i < combinacion_ganadora.length; i++) {
            combinacion_ganadora[i] = new Circulo(engine);
            int index = rand.nextInt(N_COLORS + 1);
            //Mientras sea NO_COLOR
            while (index == 0) index = rand.nextInt(N_COLORS + 1);

            colores c = colores.values()[index];

            combinacion_ganadora[i].setColor(c);
        }
    }

    private void combinacionSinRep() {
        combinacion_ganadora =  new Circulo[NUM_CASILLAS];

        // Creamos una lista con todos los colores disponibles
        ArrayList<colores> coloresAux = new ArrayList<>();
        for (int i = 0; i < N_COLORS + 1; i++)
            coloresAux.add(colores.values()[i]);

        // Cogemos colores aleatorios de la lista
        for (int i = 0; i < NUM_CASILLAS; i++) {
            combinacion_ganadora[i] = new Circulo(engine);

            // Cogemos un color aleatorio de la lista y lo quitamos
            int index = rand.nextInt(coloresAux.size());

            //Mientras sea N0_COLOR
            while (index == 0)index = rand.nextInt(coloresAux.size());

            colores c = coloresAux.get(index);
            coloresAux.remove(index);

            // Asignamos el color
            combinacion_ganadora[i].setColor(c);
        }

        //Debug
        for(int i = 0; i < combinacion_ganadora.length; i++){
            System.out.println(combinacion_ganadora[i].getColor());
        }
    }

    // Provisional para rellenar los colores (Debugging)
    private void combinacionJugador() {
        // Volcamos los colores disponibles a una lista y cuando elige un color lo sacamos de la lista
        ArrayList<colores> coloresAux = new ArrayList<>();
        for (int i = 0; i < N_COLORS; i++)
            coloresAux.add(colores.values()[i]);

        // Ahora ponemos los colores aleatorios
        for (int i = 0; i < NUM_CASILLAS; i++) {
            // Cogemos un color aleatorio de la lista y lo quitamos
            //int index = rand.nextInt((coloresAux.size() - 0) + 1) + 0;
            int index = rand.nextInt(coloresAux.size());
            colores c = coloresAux.get(index);
            coloresAux.remove(index);

            // Asignamoos el color a la fila del tablero
            tablero[INTENTO_ACTUAL].combinacion[i].setColor(c);
        }
    }

    public void checkIntento() {
        boolean win = false;

        // Volcamos la combinacion ganadora en una lista Auxiliar
        ArrayList<colores> colores_elegidos = new ArrayList<>();
        for (int i = 0; i < NUM_CASILLAS; i++) {
            colores_elegidos.add(combinacion_ganadora[i].getColor());
        }

        // Tenemos que comprobar la fila del tablero que coincida con el intento actual
        for (int i = 0; i < NUM_CASILLAS; i++) {
            // Comprobar posicion
            if (tablero[INTENTO_ACTUAL].combinacion[i].getColor() == combinacion_ganadora[i].getColor()){
                tablero[INTENTO_ACTUAL].aciertos_pos++;
            }

            // Comprobar colores -> para cada elemento de la comb del jugador, recorremos la lista hasta encontrar el color que buscamos
            int j = 0;
            boolean encontrado = false;
            while (j < colores_elegidos.size() && !encontrado) {

                if (tablero[INTENTO_ACTUAL].combinacion[i].getColor() == colores_elegidos.get(j)) {
                    // Eliminamos el color de la lista
                    colores_elegidos.remove(j);
                    tablero[INTENTO_ACTUAL].aciertos_color++;
                    encontrado = true;
                }
                j++;
            }

        }

        tablero[INTENTO_ACTUAL].aciertos.setCirculos(tablero[INTENTO_ACTUAL]);

        // Si ha acertado todas ha ganao
        if (tablero[INTENTO_ACTUAL].aciertos_pos == NUM_CASILLAS && tablero[INTENTO_ACTUAL].aciertos_color == NUM_CASILLAS)
            win = true;
        else INTENTO_ACTUAL++;

        if(INTENTO_ACTUAL == NUM_INTENTOS || win){
            engine.setScene(new Final(combinacion_ganadora, win, NUM_CASILLAS));
        }
    }

    public void configuracion(Dificultad modo) {
        switch (modo) {
            case FACIL:
                N_COLORS = 4;
                NUM_CASILLAS = 4;
                NUM_INTENTOS = 6;
                repetion = false;
                break;
            case MEDIO:
                N_COLORS = 6;
                NUM_CASILLAS = 4;
                NUM_INTENTOS = 8;
                repetion = false;
                break;
            case DIFICIL:
                N_COLORS = 8;
                NUM_CASILLAS = 5;
                NUM_INTENTOS = 10;
                repetion = true;
                break;
            case IMPOSIBLE:
                N_COLORS = 9;
                NUM_CASILLAS = 6;
                NUM_INTENTOS = 10;
                repetion = true;
                break;

        }
    }

    public int getN_COLORS() {
        return N_COLORS;
    }

    public int getNUM_CASILLAS() {
        return NUM_CASILLAS;
    }

    public int getNUM_INTENTOS() {
        return NUM_INTENTOS;
    }

    public void handleInput(TouchEvent touchEvent){
        handleColors(touchEvent);

        handleCombination(touchEvent);

    }

    //Metodo encarga de procesar el input sobre la combinacion actual
    private void handleCombination(TouchEvent touchEvent) {
        boolean removeColor = false;
        int intentoLong = tablero[INTENTO_ACTUAL].combinacion.length;
        for(int i=0; i < intentoLong && !removeColor; i++){

            Circulo c = tablero[INTENTO_ACTUAL].combinacion[i];

            if(c.handleInput(touchEvent)){
                c.descubrir(false);
                c.setColor(NO_COLOR);
                if(i < currentPos) currentPos = i;
            }
        }
    }

    //Método encargado de procesar el input sobre los colores
    private void handleColors(TouchEvent touchEvent) {
        boolean selected = false;
        //Comprobacion del input dentro de un circulo
        for(int i=0; i < colores_elegidos.length && !selected ;i++){
            selected = colores_elegidos[i].handleInput(touchEvent);

                //Si hemos elegido uno distinto al que ya teniamos elegido
            if(selected && colores_elegidos[i] != selectedCircle) {
                //Si cambiamos de eleccion deselecionamos el circulo anterior
                if(selectedCircle != null) selectedCircle.seleccionar(false);

                selectedCircle = colores_elegidos[i];
                colores_elegidos[i].seleccionar(true);
            }
        }

        int longIntento = tablero[INTENTO_ACTUAL].combinacion.length;
        Circulo seleccionado = null;

        seleccionado = tablero[INTENTO_ACTUAL].combinacion[currentPos];

        if(selectedCircle != null){
            //Comprobamos si es uno
            if(!seleccionado.getDescubierto())tablero[INTENTO_ACTUAL].coloredCircles++;

            //Cambiamos el color del combinacion al color del circulo seleccionado previamente
            seleccionado.descubrir(true);
            seleccionado.setColor(selectedCircle.getColor());
            selectedCircle.seleccionar(false);
            selectedCircle = null;
            currentPos++;

            //Vamos hasta la primera posicion libre
            while (currentPos< NUM_CASILLAS && tablero[INTENTO_ACTUAL].combinacion[currentPos].getColor() != NO_COLOR) currentPos++;

            //Comprueba si hemos ganado o hay que seguir intentando
            if(currentPos == NUM_CASILLAS){
                currentPos = 0;
                checkIntento();
            }
        }
    }

    private void dibujaIntento(int i)
    {
        int w = engine.getGraphics().getWidth();
        int totalWidth = combinacion_ganadora.length * RADIO_CIRCULO*2;
        int spaceToEachSide = (w - totalWidth) / 2;

        // RECTANGULO
        engine.getGraphics().setColor(GRIS.getValue());
        int y = pos_intentos[i].y - V_OFFSET / 2;
        engine.getGraphics().drawRoundRectangle(0,y,w,RADIO_CIRCULO*2 + V_OFFSET,50);

        // CIRCULOS
        for (int j = 0; j < NUM_CASILLAS; j++)
        {
            int x = spaceToEachSide + j * RADIO_CIRCULO*2;
            tablero[i].combinacion[j].pos.x = x;
            tablero[i].combinacion[j].pos.y = pos_intentos[i].y;
            tablero[i].combinacion[j].render();
        }
        tablero[i].aciertos.render();

        tablero[i].number.render();

    }

    private void dibujaColoresDisponibles()
    {
        int w = engine.getGraphics().getWidth();
        int totalWidth = N_COLORS * RADIO_CIRCULO*2;
        int spaceToEachSide = (w - totalWidth) / 2;

        // Rectangulo de fondo
        engine.getGraphics().setColor(GRIS.getValue());
        engine.getGraphics().fillRectangle(0, pos_colores.y - V_OFFSET/2, w, RADIO_CIRCULO*2+V_OFFSET);

        // Crear las bolas y establecer sus posiciones
        for (int i = 0; i < N_COLORS; i++) {
            int x = spaceToEachSide + i * (RADIO_CIRCULO*2);
            colores_elegidos[i].pos.x = x;
            colores_elegidos[i].pos.y = pos_colores.y;
            colores_elegidos[i].descubrir(true);
            colores_elegidos[i].render();

        }
    }


}
