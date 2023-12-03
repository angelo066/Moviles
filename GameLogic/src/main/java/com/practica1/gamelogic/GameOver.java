package com.practica1.gamelogic;


import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;

import java.util.ArrayList;

/**
 * Escena final
 */
public class GameOver extends Scene {
    private ButtonObject buttonRepeat;
    private ButtonObject buttonBackMenu;
    private TextObject textEndMessage;
    private TextObject textDescriptionMessage;
    private TextObject textCode;
    private TextObject textUsedAttempts;
    private Color[] combination_win;
    private int numAttempts;
    private boolean win;
    private int circleRadius = 50;
    private Circle[] circles;
    private Difficulty mode;
    private boolean colorBlind;

    /**
     * @param combination_win Combinacion elegida por el juego
     * @param win             Indica si el jugador ha ganado
     * @param mode            Modo de dificultad
     * @param numAttempts     Numero de intentos realizados por el jugador
     * @param colorBlind      Indica si esta el modo daltonicos activado
     */
    public GameOver(Color[] combination_win, boolean win, Difficulty mode, int numAttempts, boolean colorBlind) {
        this.width = 1080;
        this.height = 1920;

        this.win = win;
        this.combination_win = combination_win;
        this.mode = mode;
        this.numAttempts = numAttempts;
        this.colorBlind = colorBlind;
    }

    @Override
    public void init(Engine engine) {
        super.init(engine);

        // Botones
        createButtons();

        // Textos
        createTexts();

        // Colocacion de los circulos
        setCircles();

        if (win)
            audio.playSound("yuju.wav", false);
        else
            audio.playSound("douh.wav", false);

    }

    private void createTexts() {
        String mensaje = "";
        String description = "";
        String attempt = "";

        if (win) {
            mensaje = "ENHORABUENA!!";
            description = "Has averiguado el codigo en";
            attempt = numAttempts + " intentos";
        } else {
            mensaje = "GAME OVER";
            description = "Te has quedado sin intentos";
        }

        textUsedAttempts = new TextObject(graphics, new Vector2(width / 2, height / 10 * 5 / 2),
                "Nexa.ttf", attempt, Color.BLACK, 45, false, false);
        textUsedAttempts.center();

        textCode = new TextObject(graphics, new Vector2(width / 2, height / 10 * 7 / 2),
                "Nexa.ttf", "Código:", Color.BLACK, 50, false, false);
        textCode.center();

        textEndMessage = new TextObject(graphics, new Vector2(width / 2, height / 10),
                "BarlowCondensed-Regular.ttf", mensaje, Color.BLACK, 150, true, false);
        textEndMessage.center();

        textDescriptionMessage = new TextObject(graphics, new Vector2(width / 2, height / 10 * 2),
                "Nexa.ttf", description, Color.BLACK, 50, false, false);
        textDescriptionMessage.center();

    }

    private void createButtons() {
        Vector2 size = new Vector2(width / 4 * 3, height / 10);

        Vector2 posRep = new Vector2(width / 2, height / 14 * 9);
        buttonRepeat = new ButtonObject(graphics, posRep, size, 40, Color.CYAN,
                new TextObject(graphics, new Vector2(posRep), "Nexa.ttf", "Volver a jugar", Color.BLACK, 80, false, false));
        buttonRepeat.center();

        Vector2 posBack = new Vector2(width / 2, height / 14 * 11);
        buttonBackMenu = new ButtonObject(graphics, posBack, size, 40, Color.CYAN,
                new TextObject(graphics, new Vector2(posBack), "Nexa.ttf", "Elegir Dificultad", Color.BLACK, 80, false, false));
        buttonBackMenu.center();
    }

    /**
     * Prepara la combinacion de colores ganadora para su representacion
     */
    private void setCircles() {

        // Creamos el array de circulos para colocarlos
        this.circles = new Circle[combination_win.length];

        // Calculos de posicion
        int offset = circleRadius / 2;
        int totalWidth = (combination_win.length * circleRadius * 2) + ((combination_win.length - 1) * offset);
        int spaceToEachSide = (width - totalWidth) / 2;

        // Creacion de cada circulo
        for (int i = 0; i < combination_win.length; i++) {
            int x = spaceToEachSide + i * (circleRadius * 2) + i * offset;

            circles[i] = new Circle(graphics, new Vector2(x, 800), circleRadius);
            circles[i].setColorblind(colorBlind);
            circles[i].setColor(combination_win[i]);
            circles[i].setUncovered(true);
        }
    }

    @Override
    public void render() {
        // Fondo APP
        graphics.clear(Color.WHITE.getValue());

        // Fondo Juego
        graphics.setColor(Color.WHITE.getValue());
        graphics.fillRectangle(0, 0, width, height);

        //Botones
        buttonRepeat.render();
        buttonBackMenu.render();

        // Textos
        textEndMessage.render();
        textDescriptionMessage.render();

        textUsedAttempts.render();
        textCode.render();

        // Codigo colores
        for (int i = 0; i < circles.length; i++)
            circles[i].render();

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            if (buttonRepeat.handleInput(events.get(i))) {
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                engine.setScene(new MasterMind(mode));
                break;
            } else if (buttonBackMenu.handleInput(events.get(i))) {
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                engine.setScene(new SelectionMenu());
                break;
            }

        }
    }

}
