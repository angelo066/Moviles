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

    private Circulo[] circles;
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
        this.win = win;
        this.combination_win = combination_win;
        this.mode = mode;
        this.numAttempts = numAttempts;
        this.colorBlind = colorBlind;

        width = 1080;
        height = 1920;
    }

    @Override
    public void init(Engine engine) {
        super.init(engine);

        Graphics graphics = engine.getGraphics();

        // Botones
        Vector2 size = new Vector2(700, 150);
        Vector2 posRep = new Vector2(width / 2, height / 10 * 6);
        buttonRepeat = new ButtonObject(graphics, posRep, new Vector2(size), 40, Color.CYAN,
                new TextObject(graphics, new Vector2(posRep), "Nexa.ttf", "Volver a jugar", Color.BLACK, 80, false, false));
        buttonRepeat.center();

        Vector2 posBack = new Vector2(width / 2, height / 10 * 7);
        buttonBackMenu = new ButtonObject(graphics, posBack, new Vector2(size), 40, Color.CYAN,
                new TextObject(graphics, new Vector2(posBack), "Nexa.ttf", "Elegir Dificultad", Color.BLACK, 80, false, false));
        buttonBackMenu.center();

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

        textEndMessage = new TextObject(graphics, new Vector2(width / 2, height / 10),
                "BarlowCondensed-Regular.ttf", mensaje, Color.BLACK, 150, true, false);
        textEndMessage.center();

        textDescriptionMessage = new TextObject(graphics, new Vector2(width / 2, height / 10 * 2),
                "Nexa.ttf", description, Color.BLACK, 50, false, false);
        textDescriptionMessage.center();

        textUsedAttempts = new TextObject(graphics, new Vector2(width / 2, height / 10 * 5 / 2),
                "Nexa.ttf", attempt, Color.BLACK, 45, false, false);
        textUsedAttempts.center();

        textCode = new TextObject(graphics, new Vector2(width / 2, height / 10 * 7 / 2),
                "Nexa.ttf", "Código:", Color.BLACK, 50, false, false);
        textCode.center();

        // Colocacion de los circulos
        setCircles();
    }

    /**
     * Prepara la combinacion de colores ganadora para su representacion
     */
    private void setCircles() {

        // Creamos el array de circulos para colocarlos
        this.circles = new Circulo[combination_win.length];
        Font font = engine.getGraphics().newFont("Nexa.ttf", 50, false, false);

        // Calculos de posicion
        int totalWidth = combination_win.length * circleRadius * 2;
        int spaceToEachSide = (width - totalWidth) / 2;

        // Creacion de cada circulo
        for (int i = 0; i < combination_win.length; i++) {
            int x = spaceToEachSide + i * (circleRadius * 2);

            circles[i] = new Circulo(engine.getGraphics(), new Vector2(x, 800), circleRadius);
            circles[i].setColorblind(colorBlind);
            circles[i].setColor(combination_win[i]);
            circles[i].setUncovered(true);
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
        for (int i = 0; i < circles.length; i++)
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
