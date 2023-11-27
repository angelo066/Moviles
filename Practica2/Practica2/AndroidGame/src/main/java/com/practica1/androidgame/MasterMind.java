package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;
import java.util.Random;

public class MasterMind extends Scene {

    private Difficulty difficultyMode;
    private int numColors;
    private int numColorsPerAttempt;
    private int numAttempts;
    private int numDivisions;
    private Color[] winningCombination;
    private Circulo[] availableColors;
    private Attempt[] attempts;
    private Random random;
    private int currentAttempt;
    private boolean colorBlind;


    private ButtonObject buttonColorBlind;
    private ButtonObject buttonBack;
    private TextObject textAttempts;
    private TextObject title;

    public MasterMind(Difficulty mode) {
        this.width = 1080;
        this.height = 1920;

        this.numDivisions = 12;
        this.difficultyMode = mode;

        this.random = new Random();

        this.currentAttempt = 0;

        this.colorBlind = false;
    }

    @Override
    public void init(Engine engine) {
        super.init(engine);

        //Configuracion
        switch (difficultyMode) {
            case EASY:
                this.numColors = 4;
                this.numColorsPerAttempt = 4;
                this.numAttempts = 6;
                break;
            case MEDIUM:
                this.numColors = 6;
                this.numColorsPerAttempt = 4;
                this.numAttempts = 8;
                break;
            case HARD:
                this.numColors = 8;
                this.numColorsPerAttempt = 5;
                this.numAttempts = 10;
                break;
            case IMPOSSIBLE:
                this.numColors = 9;
                this.numColorsPerAttempt = 6;
                this.numAttempts = 10;
                break;

        }

        //Creacion de intentos
        this.attempts = new Attempt[numAttempts];

        int attemptHeight = height / this.numDivisions;
        int heightOffset = attemptHeight / 10;

        for (int i = 0; i < this.numAttempts; i++) {
            this.attempts[i] = new Attempt(this, numColorsPerAttempt, i + 1,
                    new Vector2(3, (attemptHeight * (i + 1))), new Vector2(width - 6, attemptHeight - heightOffset));
        }

        int circleRadius = 50;
        int offsetBetweenCircle = circleRadius / 3;
        int widthCombination = (2 * circleRadius * numColors) + ((numColors - 1) * offsetBetweenCircle);
        int startPosition = (width - widthCombination) / 2;
        int tamDivision = height / numDivisions;


        //Creacion de colores disponibles
        this.availableColors = new Circulo[numColors];
        for (int i = 0; i < this.numColors; i++) {
            int x = startPosition + (offsetBetweenCircle * i) + (circleRadius * 2 * i);
            int y = (tamDivision * (numDivisions - 1)) + ((tamDivision - 2 * circleRadius) / 2);
            this.availableColors[i] = new Circulo(this, new Vector2(x, y), circleRadius);
            this.availableColors[i].setColor(Color.values()[i]);
            this.availableColors[i].setUncovered(true);
        }

        //Seleccion de combinacion ganadora
        winningCombination = new Color[numColorsPerAttempt];
        if (difficultyMode == Difficulty.HARD || difficultyMode == Difficulty.IMPOSSIBLE) {
            for (int i = 0; i < numColorsPerAttempt; i++) {
                int index = random.nextInt(numColors);
                winningCombination[i] = Color.values()[index];
            }
        } else {
            ArrayList<Color> colorsAux = new ArrayList<>();
            for (int i = 0; i < numColors; i++) {
                colorsAux.add(Color.values()[i]);
            }

            for (int i = 0; i < numColorsPerAttempt; i++) {
                int index = random.nextInt(colorsAux.size());
                winningCombination[i] = colorsAux.get(index);
                colorsAux.remove(index);
            }
        }

        for(int i = 0;i<winningCombination.length;i++)
            System.out.println(winningCombination[i]);

        // Botones
        buttonColorBlind = new ButtonObject(engine, width, height, new Vector2(width - 120, 20), new Vector2(100, 100), "ojo.png");

        buttonBack = new ButtonObject(engine, width, height, new Vector2(20, 20), new Vector2(100, 100), "volver.png");

        // Texto de indicacion de intentos restantes
        textAttempts = new TextObject(engine, width, height, new Vector2(width / 2, 72),
                "Nexa.ttf", "Te quedan " + (attempts.length - currentAttempt) + " intentos", Color.BLACK, 45, false, false);
        textAttempts.centerHorizontal();

        title = new TextObject(engine, width, height, new Vector2(width / 2, 50),
                "BarlowCondensed-Regular.ttf", "Averigua el código", Color.BLACK, 60, true, false);
        title.center();
    }

    @Override
    public void update(double deltaTime) {
    }

    @Override
    public void render() {
        // Fondo de APP
        engine.getGraphics().clear(Color.WHITE.getValue());

        // Fondo de Juego
        engine.getGraphics().setColor(Color.WHITE.getValue());
        engine.getGraphics().fillRectangle(0, 0, width, height);

        // Intentos
        for (int i = 0; i < this.numAttempts; i++)
            attempts[i].render();

        // Colores disponibles
        int tamDivision = height / numDivisions;

        engine.getGraphics().setColor(Color.GREY.getValue());
        engine.getGraphics().fillRectangle(0, (numDivisions - 1) * tamDivision, width, tamDivision);

        for (int i = 0; i < availableColors.length; i++)
            availableColors[i].render();

        // Botones
        buttonColorBlind.render();

        buttonBack.render();

        // Texto de los intentos restantes
        String text = "Te quedan " + (attempts.length - currentAttempt) + " intentos";
        textAttempts.setText(text);
        textAttempts.render();

        title.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (int i = 0; i < events.size(); i++) {

            attempts[currentAttempt].handleInput(events.get(i));

            for (int j = 0; j < availableColors.length; j++) {
                if (availableColors[j].handleInput(events.get(i))) {

                    attempts[currentAttempt].setCircle(availableColors[j].getColor(), winningCombination);

                    if (attempts[currentAttempt].getUncoveredCircles() == numColorsPerAttempt) {

                        if (attempts[currentAttempt].isCorrectCombination()) {
                            engine.setScene(new GameOver(winningCombination, true, difficultyMode, currentAttempt + 1, colorBlind));
                        } else {
                            currentAttempt++;

                            if (currentAttempt == numAttempts) {
                                engine.setScene(new GameOver(winningCombination, false, difficultyMode, currentAttempt + 1, colorBlind));
                            }
                        }
                    }
                }
            }


            if (buttonColorBlind.handleInput(events.get(i))) {
                colorBlind = !colorBlind;

                engine.getAudio().stopSound("clickboton.wav");
                engine.getAudio().playSound("clickboton.wav", false);

                for(int j=0;j<attempts.length;j++)
                    attempts[j].setColorblind(colorBlind);

                for (int j = 0;j<availableColors.length;j++)
                    availableColors[j].setColorblind(colorBlind);

                if (colorBlind)
                    buttonColorBlind.changeImage("ojo2.png");
                else
                    buttonColorBlind.changeImage("ojo.png");
            }

            if (buttonBack.handleInput(events.get(i))) {
                engine.getAudio().stopSound("clickboton.wav");
                engine.getAudio().playSound("clickboton.wav", false);
                engine.setScene(new SelectionMenu());
                break;
            }
        }
    }
}
