package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;

/**
 * Escena final
 */
public class EndScreen extends Scene {
    private ButtonObject buttonRepeat;
    private ButtonObject buttonBackMenu;
    private TextObject textEndMessage;
    private TextObject textDescriptionMessage;
    private TextObject textCode;
    private TextObject textUsedAttempts;

    private Color[] combination_win;
    private int numAttempts;

    private boolean win;
    private int numSquares;
    private int circleRadius = 50;

    private CircleObject[] circles;
    private Difficulty mode;

    private boolean colorBlind;

    /**
     * @param combination_win Combinacion elegida por el juego
     * @param win             Indica si el jugador ha ganado
     * @param numSquares      Numero de casillas de un intento (tamanio de la combinacion)
     * @param mode            Modo de dificultad
     * @param numAttempts     Numero de intentos realizados por el jugador
     * @param colorBlind      Indica si esta el modo daltonicos activado
     */
    public EndScreen(Color[] combination_win, boolean win, int numSquares, Difficulty mode, int numAttempts, boolean colorBlind) {
        this.win = win;
        this.combination_win = combination_win;
        this.numSquares = numSquares;
        this.mode = mode;
        this.numAttempts = numAttempts;
        this.colorBlind = colorBlind;

        width = 1080;
        height = 1920;
    }

    @Override
    public void init(Engine engine) {
        super.init(engine);


        // Creacion de las fuentes que utilizamos
        Font fontButton = ResourceManager.getInstance().getFont("Nexa.ttf");
        Font fontMessage = ResourceManager.getInstance().getFont("BarlowCondensed-Regular.ttf");
        Font fontDescription = ResourceManager.getInstance().getFont("Nexa.ttf");


        // Botones
        Vector2 size = new Vector2(700, 150);
        Vector2 posRep = new Vector2(width / 2, height / 10 * 6);
        buttonRepeat = new ButtonObject(engine, width, height, posRep, new Vector2(size), 40, Color.CYAN,
                new TextObject(engine, width, height, new Vector2(posRep), fontButton, "Volver a jugar", Color.BLACK, 80, false, false));
        buttonRepeat.centrar();

        Vector2 posBack = new Vector2(width / 2, height / 10 * 7);
        buttonBackMenu = new ButtonObject(engine, width, height, posBack, new Vector2(size), 40, Color.CYAN,
                new TextObject(engine, width, height, new Vector2(posBack), fontButton, "Elegir Dificultad", Color.BLACK, 80, false, false));
        buttonBackMenu.centrar();

        // Textos
        String mensaje = "";
        String description = "";
        String attempt = "";

        if (win) {
            mensaje = "ENHORABUENA!!";
            description = "Has averiguado el codigo en";
            attempt = numAttempts + " intentos";
            engine.getAudio().playSound("yuju.wav", false);
        } else {
            mensaje = "GAME OVER";
            description = "Te has quedado sin intentos";
            engine.getAudio().playSound("douh.wav", false);
        }

        textEndMessage = new TextObject(engine, width, height, new Vector2(width / 2, height / 10),
                fontMessage, mensaje, Color.BLACK, 150, true, false);
        textEndMessage.center();

        textDescriptionMessage = new TextObject(engine, width, height, new Vector2(width / 2, height / 10 * 2),
                fontDescription, description, Color.BLACK, 50, false, false);
        textDescriptionMessage.center();

        textUsedAttempts = new TextObject(engine, width, height, new Vector2(width / 2, height / 10 * 5 / 2),
                fontButton, attempt, Color.BLACK, 45, false, false);
        textUsedAttempts.center();

        textCode = new TextObject(engine, width, height, new Vector2(width / 2, height / 10 * 7 / 2),
                fontDescription, "Código:", Color.BLACK, 50, false, false);
        textCode.center();

        // Colocacion de los circulos
        setCircles();
    }

    /**
     * Prepara la combinacion de colores ganadora para su representacion
     */
    private void setCircles() {

        // Creamos el array de circulos para colocarlos
        this.circles = new CircleObject[numSquares];
        Font font = engine.getGraphics().newFont("Nexa.ttf", 50, false, false);

        // Calculos de posicion
        int totalWidth = numSquares * circleRadius * 2;
        int spaceToEachSide = (width - totalWidth) / 2;

        // Creacion de cada circulo
        for (int i = 0; i < combination_win.length; i++) {
            int x = spaceToEachSide + i * (circleRadius * 2);

            circles[i] = new CircleObject(engine, width, height,
                    new Vector2(x, 800), combination_win[i].getId(), font);

            circles[i].colorblind(colorBlind);
            circles[i].setColor(combination_win[i]);
            circles[i].uncover(true);
        }
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        // Fondo APP
        engine.getGraphics().clear(Color.WHITE.getValue());

        // Fondo Juego
        engine.getGraphics().setColor(Color.WHITE.getValue());
        engine.getGraphics().fillRectangle(0, 0, width, height);

        // Botones
        buttonRepeat.render();
        buttonBackMenu.render();

        // Textos
        textEndMessage.render();
        textDescriptionMessage.render();
        textUsedAttempts.render();
        textCode.render();

        // Codigo colores
        for (int i = 0; i < numSquares; i++)
            circles[i].render();

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            if (buttonRepeat.handleInput(events.get(i))) {
                engine.getAudio().stopSound("botonInterfaz.wav");
                engine.getAudio().playSound("botonInterfaz.wav", false);
                engine.setScene(new MasterMind(mode));
                break;
            } else if (buttonBackMenu.handleInput(events.get(i))) {
                engine.getAudio().stopSound("botonInterfaz.wav");
                engine.getAudio().playSound("botonInterfaz.wav", false);
                engine.setScene(new SelectionMenu());
                break;
            }
        }
    }

}
