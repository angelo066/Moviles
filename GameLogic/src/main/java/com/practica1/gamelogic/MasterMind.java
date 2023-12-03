package com.practica1.gamelogic;


import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Scene;
import com.practica1.engine.TouchEvent;

import java.util.ArrayList;
import java.util.Random;

public class MasterMind extends Scene {

    private Difficulty difficultyMode;
    private int numColors;
    private int numColorsPerAttempt;
    private int numAttempts;
    private int numDivisions;
    private boolean repeatColors;


    private Color[] winningCombination;
    private Circle[] availableColors;
    private ArrayList<Attempt> attempts;


    private Random random;
    private int currentAttempt;
    private boolean colorBlind;


    private ButtonObject buttonColorBlind;
    private ButtonObject buttonBack;
    private TextObject textAttempts;
    private TextObject title;

    private int attemptHeight;
    private int heightOffset;

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

        selectConfiguration();

        createAttempts();

        createAvailableColors();

        selectWinningCombination();

        createButtons();

        createTexts();
    }

    private void selectConfiguration() {
        switch (difficultyMode) {
            case EASY:
                this.numColors = 4;
                this.numColorsPerAttempt = 4;
                this.numAttempts = 6;
                this.repeatColors = false;
                break;
            case MEDIUM:
                this.numColors = 6;
                this.numColorsPerAttempt = 4;
                this.numAttempts = 8;
                this.repeatColors = false;
                break;
            case HARD:
                this.numColors = 8;
                this.numColorsPerAttempt = 5;
                this.numAttempts = 10;
                this.repeatColors = true;
                break;
            case IMPOSSIBLE:
                this.numColors = 9;
                this.numColorsPerAttempt = 6;
                this.numAttempts = 10;
                this.repeatColors = true;
                break;

        }
    }

    private void createAttempts() {
        this.attempts = new ArrayList<>();

        this.attemptHeight = height / this.numDivisions;
        this.heightOffset = attemptHeight / (this.numDivisions - 2);

        for (int i = 0; i < this.numAttempts; i++) {
            this.attempts.add(new Attempt(graphics, numColorsPerAttempt, i + 1,
                    new Vector2(3, (attemptHeight * (i + 1))), new Vector2(width - 6, attemptHeight - heightOffset)));
        }

    }


    private void createAvailableColors() {
        int circleRadius = 50;
        int offsetBetweenCircle = circleRadius / 3;
        int widthCombination = (2 * circleRadius * numColors) + ((numColors - 1) * offsetBetweenCircle);
        int startPosition = (width - widthCombination) / 2;
        int tamDivision = height / numDivisions;

        this.availableColors = new Circle[numColors];
        for (int i = 0; i < this.numColors; i++) {
            int x = startPosition + (offsetBetweenCircle * i) + (circleRadius * 2 * i);
            int y = (tamDivision * (numDivisions - 1)) + ((tamDivision - 2 * circleRadius) / 2);
            this.availableColors[i] = new Circle(graphics, new Vector2(x, y), circleRadius);
            this.availableColors[i].setColor(Color.values()[i]);
            this.availableColors[i].setUncovered(true);
        }
    }

    private void selectWinningCombination() {
        winningCombination = new Color[numColorsPerAttempt];
        if (repeatColors) {
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

        for (int i = 0; i < winningCombination.length; i++)
            System.out.println(winningCombination[i]);
    }

    private void createTexts() {
        textAttempts = new TextObject(graphics, new Vector2(width / 2, height / numDivisions / 2),
                "Nexa.ttf", "Te quedan " + (numAttempts - currentAttempt) + " intentos", Color.BLACK, 45, false, false);
        textAttempts.centerHorizontal();

        title = new TextObject(graphics, new Vector2(width / 2, height / numDivisions / 3),
                "BarlowCondensed-Regular.ttf", "Averigua el código", Color.BLACK, 60, true, false);
        title.center();
    }

    private void createButtons() {
        buttonColorBlind = new ButtonObject(graphics, new Vector2(width - 120, 20), new Vector2(100, 100), "ojo.png");

        buttonBack = new ButtonObject(graphics, new Vector2(20, 20), new Vector2(100, 100), "volver.png");
    }

    @Override
    public void render() {

        // Fondo de APP
        graphics.clear(Color.WHITE.getValue());

        // Fondo de Juego
        graphics.setColor(Color.WHITE.getValue());
        graphics.fillRectangle(0, 0, width, height);

        // Intentos
        for (int i = 0; i < this.numAttempts; i++)
            attempts.get(i).render();


        // Colores disponibles
        graphics.setColor(Color.GREY.getValue());
        graphics.fillRectangle(0, (numDivisions - 1) * attemptHeight, width, attemptHeight);

        for (int i = 0; i < availableColors.length; i++)
            availableColors[i].render();

        // Texto de los intentos restantes
        graphics.setColor(Color.WHITE.getValue());
        graphics.fillRectangle(0, 0, width, attemptHeight);
        textAttempts.setText("Te quedan " + (numAttempts - currentAttempt) + " intentos");
        textAttempts.render();

        title.render();

        // Botones
        buttonColorBlind.render();

        buttonBack.render();
    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        outerloop:
        for (int i = 0; i < events.size(); i++) {

            attempts.get(currentAttempt).handleInput(events.get(i));

            for (int j = 0; j < availableColors.length; j++) {
                if (availableColors[j].handleInput(events.get(i))) {

                    attempts.get(currentAttempt).setCircle(availableColors[j].getColor(), winningCombination);

                    if (attempts.get(currentAttempt).getUncoveredCircles() == numColorsPerAttempt) {

                        if (attempts.get(currentAttempt).isCorrectCombination()) {
                            engine.setScene(new GameOver(winningCombination, true, difficultyMode, currentAttempt + 1, colorBlind));
                            break outerloop;
                        } else {
                            currentAttempt++;

                            if (currentAttempt == numAttempts) {
                                engine.setScene(new GameOver(winningCombination, false, difficultyMode, currentAttempt + 1, colorBlind));
                                break outerloop;
                            }
                        }
                    }

                }
            }

            if (buttonColorBlind.handleInput(events.get(i))) {
                colorBlind = !colorBlind;

                audio.stopSound("clickboton.wav");
                audio.playSound("clickboton.wav", false);

                for (int j = 0; j < numAttempts; j++)
                    attempts.get(j).setColorblind(colorBlind);

                for (int j = 0; j < availableColors.length; j++)
                    availableColors[j].setColorblind(colorBlind);

                if (colorBlind)
                    buttonColorBlind.changeImage("ojo2.png");
                else
                    buttonColorBlind.changeImage("ojo.png");
            }

            if (buttonBack.handleInput(events.get(i))) {
                audio.stopSound("clickboton.wav");
                audio.playSound("clickboton.wav", false);
                engine.setScene(new SelectionMenu());
                break;
            }
        }
    }
}
