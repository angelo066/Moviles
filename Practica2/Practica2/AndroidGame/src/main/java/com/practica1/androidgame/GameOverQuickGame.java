package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;

/**
 * Escena final
 */
public class GameOverQuickGame extends Scene {
    private ButtonObject buttonRepeat;
    private ButtonObject buttonShare;
    private ButtonObject buttonBackMenu;
    private ButtonObject buttonMoreAttempts;
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

    private int coins = 0;
    private TextObject coins_Earned;
    private ImageObject coin_Image;

    private ButtonObject buttonNext_Level;

    /**
     * @param combination_win Combinacion elegida por el juego
     * @param win             Indica si el jugador ha ganado
     * @param mode            Modo de dificultad
     * @param numAttempts     Numero de intentos realizados por el jugador
     * @param colorBlind      Indica si esta el modo daltonicos activado
     */
    public GameOverQuickGame(Color[] combination_win, boolean win, Difficulty mode, int numAttempts, boolean colorBlind, int coins) {
        this.width = 1080;
        this.height = 1920;

        this.win = win;
        this.combination_win = combination_win;
        this.mode = mode;
        this.numAttempts = numAttempts;
        this.colorBlind = colorBlind;
        this.coins = coins;

    }

    @Override
    public void init(Engine engine) {
        super.init(engine);

        // Botones
        createButtons();

        // Textos
        createTexts();

        // Colocacion de los circulos
        if (win) setCircles();
    }

    private void createTexts() {
        String mensaje = "";
        String description = "";
        String attempt = "";

        if (win) {
            mensaje = "ENHORABUENA!!";
            description = "Has averiguado el codigo en";
            attempt = numAttempts + " intentos";


            textUsedAttempts = new TextObject(graphics, new Vector2(width / 2, height / 10 * 5 / 2),
                    "Nexa.ttf", attempt, Color.BLACK, 45, false, false);
            textUsedAttempts.center();

            textCode = new TextObject(graphics, new Vector2(width / 2, height / 10 * 7 / 2),
                    "Nexa.ttf", "Código:", Color.BLACK, 50, false, false);
            textCode.center();

            String coins_String = "+" + String.valueOf(coins) + "- Total" + String.valueOf(GameManager.getInstance().getCoins());

            coins_Earned = new TextObject(graphics, new Vector2(width/2, (height / 10 * 7 / 2) + 350),
                    "Nexa.ttf", coins_String, Color.BLACK, 100, false, false);

            coin_Image = new ImageObject(graphics, new Vector2(width/2 - 310, (height / 10 * 7 / 2) + 350), new Vector2(100,100),
                    "coins.png");

            coins_Earned.center();
            coin_Image.center();

            audio.playSound("yuju.wav", false);
        } else {
            mensaje = "GAME OVER";
            description = "Te has quedado sin intentos";
            audio.playSound("douh.wav", false);
        }

        textEndMessage = new TextObject(graphics, new Vector2(width / 2, height / 10),
                "BarlowCondensed-Regular.ttf", mensaje, Color.BLACK, 150, true, false);
        textEndMessage.center();

        textDescriptionMessage = new TextObject(graphics, new Vector2(width / 2, height / 10 * 2),
                "Nexa.ttf", description, Color.BLACK, 50, false, false);
        textDescriptionMessage.center();

    }

    private void createButtons() {
        Vector2 size = new Vector2(width / 4 * 3, height / 10);

        if (!win) {
            Vector2 posAtt = new Vector2(width / 2, height / 14 * 7);
            buttonMoreAttempts = new ButtonObject(graphics, posAtt, size, 40, Color.CYAN,
                    new TextObject(graphics, new Vector2(posAtt), "Nexa.ttf", "+2 intentos", Color.BLACK, 80, false, false));
            buttonMoreAttempts.center();
        } else {
            Vector2 posShare = new Vector2(width / 2, height / 14 * 9);
            buttonShare = new ButtonObject(graphics, posShare, size, 40, Color.CYAN,
                    new TextObject(graphics, new Vector2(posShare), "Nexa.ttf", "Compartir", Color.BLACK, 80, false, false));
            buttonShare.center();

        }

        Vector2 posBack = new Vector2(width / 2, height / 14 * 13);
        buttonBackMenu = new ButtonObject(graphics, posBack, size, 40, Color.CYAN,
                new TextObject(graphics, new Vector2(posBack), "Nexa.ttf", "Menú", Color.BLACK, 80, false, false));
        buttonBackMenu.center();

        Vector2 posRepeat = new Vector2(width / 2, height / 14 * 11);
        buttonRepeat = new ButtonObject(graphics, posRepeat, size, 40, Color.CYAN,
                new TextObject(graphics, new Vector2(posRepeat), "Nexa.ttf", "Repetir", Color.BLACK, 80, false, false));
        buttonRepeat.center();
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

        int skin = GameManager.getInstance().getActual_Skin_Code() + 1;
        // Creacion de cada circulo
        for (int i = 0; i < combination_win.length; i++) {
            int x = spaceToEachSide + i * (circleRadius * 2) + i * offset;

            circles[i] = new Circle(graphics, new Vector2(x, 800), circleRadius);
            circles[i].setColorblind(colorBlind);
            circles[i].setColor(combination_win[i]);
            circles[i].setUncovered(true);

            if(skin != -1){

                int colorIndex = combination_win[i].getId() + 1;
                String image = "packs/pack_" + skin + "/" +colorIndex + ".png";

                circles[i].setImage(ResourceManager.getInstance().getImage(image));
            }
        }
    }

    @Override
    public void render() {
        // Fondo APP
        graphics.clear(Color.WHITE.getValue());

        // Fondo Juego
        graphics.setColor(Color.WHITE.getValue());
        graphics.fillRectangle(0, 0, width, height);

        // Botones
        if (!win)
            buttonMoreAttempts.render();
        else
            buttonShare.render();

        buttonRepeat.render();
        buttonBackMenu.render();

        // Textos
        textEndMessage.render();
        textDescriptionMessage.render();


        if (this.win) {
            textUsedAttempts.render();
            textCode.render();
            // Codigo colores
            for (int i = 0; i < circles.length; i++)
                circles[i].render();
        }

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            if(!win){
                if (buttonMoreAttempts.handleInput(events.get(i))) {
                    audio.stopSound("botonInterfaz.wav");
                    audio.playSound("botonInterfaz.wav", false);
                    engine.getAds().showRewardedAd(() -> {
                        ((MasterMind) SceneManager.getInstance().getScene()).addAttempts(2);
                        SceneManager.getInstance().goToNextScene();
                    });
                    break;
                }
            }

            else{

            }

            if (buttonBackMenu.handleInput(events.get(i))) {
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                SceneManager.getInstance().removeScene();
                SceneManager.getInstance().addScene(new MainMenu());
                SceneManager.getInstance().goToNextScene();
                break;
            }
            else if (buttonRepeat.handleInput(events.get(i))) {
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                SceneManager.getInstance().removeScene();
                SceneManager.getInstance().addScene(new MasterMind(mode));
                SceneManager.getInstance().goToNextScene();
                break;
            }

        }
    }

}
