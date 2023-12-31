package com.practica1.androidgame;

import android.content.Context;

import com.google.gson.Gson;
import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private int lastYPosition;
    private String levelName = "";
    private int indexWorld = -1;
    private int indexLevel;

    //Booleano para controlar a donde nos envía el boton de hacia atras.
    private boolean world_Level = false;

    private int lvl_coins = 0; //Inicializado a 0 para los niveles de partida rapida

    private ImageObject imageBackground;
    private String pack_file;   //Combinacion de color

    private int colorText;

    private boolean loadedData = false;

    public MasterMind(Difficulty mode) {
        this.width = 1080;
        this.height = 1920;

        this.numDivisions = 12;
        this.difficultyMode = mode;

        this.random = new Random();

        this.currentAttempt = 0;

        this.colorBlind = false;
    }

    public MasterMind(String levelName) {
        this.width = 1080;
        this.height = 1920;

        this.numDivisions = 12;

        this.random = new Random();

        this.currentAttempt = 0;

        this.colorBlind = false;

        this.levelName = levelName;

        //Por ser la constructora que usan los mundos
        this.world_Level = true;

    }


    @Override
    public void init(Engine engine) {
        super.init(engine);

        if (levelName == "" && indexWorld == -1)
            selectConfiguration();
        else{
            createLevel();
            lvl_coins = this.random.nextInt(6) + 1;    //Seteamos las monedas que va a ganar si se pasa el nivel
            //selectPack();
        }

        if(loadedData) loadData();

        createPalette();

        createStyle();

        createAvailableColors();

        createButtons();

        createTexts();

        createAttempts();

        if(!loadedData){
            selectWinningCombination();
        }
    }

    @Override
    public void saveData() {
        super.saveData();
        RunSerializeInfo runSerializeInfo = new RunSerializeInfo(indexWorld, indexLevel, difficultyMode,
                winningCombination, availableColors, attempts, currentAttempt, colorBlind, world_Level);
        try{

            FileOutputStream fout = GameManager.getInstance().getContext().openFileOutput("game.txt", Context.MODE_PRIVATE);
            ObjectOutputStream out =  new ObjectOutputStream(fout);

            out.writeObject(runSerializeInfo);
            out.flush();
            out.close();

            /// SECURIZAR /// ---------------------------------------------------------------------------
            String data = runSerializeInfo.toString();
            NDKManager.getInstance().secure(data, "hashgame.txt"); // lo queremos guardar en archivo
            /// SECURIZAR /// ---------------------------------------------------------------------------
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al persistir datos del jugador");
            e.printStackTrace();
        }

    }

    @Override
    public void loadData(){
        super.loadData();

        RunSerializeInfo run;
        try{
            FileInputStream file = GameManager.getInstance().getContext().openFileInput("game.txt");
            ObjectInputStream in = new ObjectInputStream(file);

            run = (RunSerializeInfo)in.readObject();

            /// COMPROBAR SECURIZACION /// ---------------------------------------------------------------------------
            String data = run.toString(); // cogemos el archivo de guardado
            if(!NDKManager.getInstance().checkHash(data, "hashgame.txt"))
            {
                System.out.println("ERES UN BANDIDO, CAMBIASTE LA PARTIDA");
            }
            else {
                System.out.println("ERES LEGAL, NO CAMBIASTE LA PARTIDA");
            }
            /// COMPROBAR SECURIZACION /// ---------------------------------------------------------------------------
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //
        String hashgenerator = NDKManager.getInstance().getKey() + run.toString();
        String hash = NDKManager.getInstance().createHash(hashgenerator);
        System.out.println(hash);

        this.attempts = new ArrayList<>();
        this.attempts = run.getAttempts();

        this.attemptHeight = height / this.numDivisions;
        this.heightOffset = attemptHeight / (this.numDivisions - 2);
        this.lastYPosition = 0;

        for(int i = 0; i < this.attempts.size();i++){
            this.attempts.get(i).load(graphics);
        }

        this.numAttempts = this.attempts.size();

        this.indexLevel = run.getLevel();
        this.indexWorld = run.getWorld();
        this.difficultyMode = run.getDifficulty();
        this.availableColors = run.getAvailableColors();

       /*for(int i = 0; i < this.availableColors.length; i++){
            this.availableColors[i].load(graphics);
        }*/

        this.winningCombination = run.getWinningCombination();
        this.currentAttempt = run.getCurrentAttempt();
        this.world_Level = run.isWorldLevel();
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

        this.attemptHeight = height / this.numDivisions;
        this.heightOffset = attemptHeight / (this.numDivisions - 2);
        this.lastYPosition = 0;

        if(!loadedData){
            this.attempts = new ArrayList<>();
            for (int i = 0; i < this.numAttempts; i++) {
                this.attempts.add(new Attempt(graphics, numColorsPerAttempt, i + 1,
                        new Vector2(3, (attemptHeight * (i + 1))), new Vector2(width - 6, attemptHeight - heightOffset)));
            }
        }

    }

    public void addAttempts(int newAttempts) {
        this.numAttempts += newAttempts;

        for (int i = this.attempts.size(); i < this.numAttempts; i++) {
            int y = this.attempts.get(i - 1).getPos().y;
            this.attempts.add(new Attempt(graphics, numColorsPerAttempt, i + 1,
                    new Vector2(3, y + attemptHeight), new Vector2(width - 6, attemptHeight - heightOffset)));
            if (colorBlind) this.attempts.get(i).setColorblind(true);
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
            if(world_Level || GameManager.getInstance().getCurrentSkinCode() != -1)
            {
                String fileRoute= "packs/" + pack_file + "/" + (i+1) +".png";
                this.availableColors[i] = new Circle(graphics, new Vector2(x, y), circleRadius,fileRoute);
            }
            else
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
                "Nexa.ttf", "Te quedan " + (numAttempts - currentAttempt) + " intentos", colorText, 45, false, false);
        textAttempts.centerHorizontal();

        title = new TextObject(graphics, new Vector2(width / 2, height / numDivisions / 3),
                "BarlowCondensed-Regular.ttf", "Averigua el código", colorText, 60, true, false);
        title.center();
    }

    private void createButtons() {
        buttonColorBlind = new ButtonObject(graphics, new Vector2(width - 120, 20), new Vector2(100, 100), "ojo.png");

        buttonBack = new ButtonObject(graphics, new Vector2(20, 20), new Vector2(100, 100), "volver.png");
    }

    private void createStyle()
    {
        // Si es un nivel de mundo cargamos el style.json del mundo
        if(world_Level)
        {
            // Creamos el parser del json
            Gson gson = new Gson();
            BufferedReader br = null;

            // Leemos el json
            try {
                br = engine.openAssetFile("levels/world" + (GameManager.getInstance().getCurrentWorld()+1) + "/style.json");
            } catch (IOException ex) {
                System.out.println("Error loading World Style");
            }

            // Deserializamos el json en un objeto con la info del nivel
            WordlInfo levelInfo = gson.fromJson(br, WordlInfo.class);

            // Asignamos los valores que hemos recogido a nuestra partida
            String background = levelInfo.getGameplayBackground();
            pack_file = levelInfo.getPack();
            imageBackground = new ImageObject(graphics, new Vector2(0,0), new Vector2(width, height), "backgrounds/" + background + ".png");
        }else{
            int index = GameManager.getInstance().getCurrentSkinBackground();

            if(index != -1){
                String route = ResourceManager.getInstance().getShopBackground(GameManager.getInstance().getCurrentSkinBackground()).second;
                imageBackground = new ImageObject(graphics, new Vector2(0,0), new Vector2(width, height), route);
            }

            int code_index = GameManager.getInstance().getCurrentSkinCode();

            if(code_index != -1){
                pack_file = ResourceManager.getInstance().getShopCode(GameManager.getInstance().getCurrentSkinCode()).second;
            }

        }
    }

    private void createPalette()
    {
        colorText = GameManager.getInstance().getCurrentSkinPalette().getColor2();
    }

    @Override
    public void render() {

        // Fondo de APP
        int backColor = GameManager.getInstance().getCurrentSkinPalette().getColorBackground();
        graphics.clear(backColor);

        // Fondo de Juego
        graphics.setColor(backColor);
        graphics.fillRectangle(0, 0, width, height);

        // Background si hay
        if(imageBackground != null)
            imageBackground.render();

        // Intentos
        for (int i = 0; i < this.numAttempts; i++) {

            if ((attempts.get(i).getPos().y > 0)
                    && ((attempts.get(i).getPos().y < height - attemptHeight))) // solo renderizar el attempt si esta dentro del area de juego
                attempts.get(i).render();
        }


        // Colores disponibles
        graphics.setColor(GameManager.getInstance().getCurrentSkinPalette().getColor1());
        graphics.fillRectangle(0, (numDivisions - 1) * attemptHeight, width, attemptHeight);

        for (int i = 0; i < availableColors.length; i++)
            availableColors[i].render();

        // Texto de los intentos restantes
        graphics.setColor(GameManager.getInstance().getCurrentSkinPalette().getColor1());
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
            //Manejo de input dentro del area de juego
            if (events.get(i).y >= attemptHeight && events.get(i).y <= height - attemptHeight) {

                //Manejo de input para el scroll del tablero
                if (events.get(i).type == TouchEvent.TouchEventType.TOUCH_DOWN)
                    lastYPosition = events.get(i).y;

                //Manejo de input para el scroll del tablero
                if (events.get(i).type == TouchEvent.TouchEventType.TOUCH_DRAG) {
                    int offset = events.get(i).y - lastYPosition;

                    int posLastAttempt = attempts.get(numAttempts - 1).getPos().y;
                    int posFirstAttempt = attempts.get(0).getPos().y;

                    if (posLastAttempt + offset < attemptHeight)
                        offset = attemptHeight - posLastAttempt;
                    else if (posFirstAttempt + offset > attemptHeight)
                        offset = attemptHeight - posFirstAttempt;

                    for (int j = 0; j < numAttempts; j++)
                        attempts.get(j).translate(0, offset);

                    lastYPosition = events.get(i).y;
                }

                attempts.get(currentAttempt).handleInput(events.get(i));
            }


            for (int j = 0; j < availableColors.length; j++) {
                if (availableColors[j].handleInput(events.get(i))) {

                    if(world_Level || GameManager.getInstance().getCurrentSkinCode() != -1)
                        attempts.get(currentAttempt).setCircle(availableColors[j].getColor(), winningCombination, availableColors[j].getImage());
                    else
                        attempts.get(currentAttempt).setCircle(availableColors[j].getColor(), winningCombination, null);

                    if (attempts.get(currentAttempt).getUncoveredCircles() == numColorsPerAttempt) {

                        if (attempts.get(currentAttempt).isCorrectCombination()) {

                            if(world_Level){
                                //Si es el último nivel por el que vamos
                                if(GameManager.getInstance().isLastLevel()){
                                    GameManager.getInstance().levelCompleted();
                                }
                                GameManager.getInstance().addCoins(lvl_coins);
                                SceneManager.getInstance().addScene(new GameOverWorld(winningCombination, true, difficultyMode, currentAttempt + 1, colorBlind, lvl_coins, levelName, pack_file));
                            }
                            else{
                                SceneManager.getInstance().addScene(new GameOverQuickGame(winningCombination, true, difficultyMode, currentAttempt + 1, colorBlind, lvl_coins));
                            }
                            SceneManager.getInstance().addScene(this);
                            SceneManager.getInstance().goToNextScene();
                            break outerloop;
                        } else {
                            currentAttempt++;

                            if (currentAttempt == numAttempts) {
                                if(world_Level)SceneManager.getInstance().addScene(new GameOverWorld(winningCombination, false, difficultyMode, currentAttempt + 1, colorBlind, lvl_coins, levelName, pack_file));
                                else SceneManager.getInstance().addScene(new GameOverQuickGame(winningCombination, false, difficultyMode, currentAttempt + 1, colorBlind, lvl_coins));
                                SceneManager.getInstance().addScene(this);
                                SceneManager.getInstance().goToNextScene();
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
                if(!world_Level)SceneManager.getInstance().addScene(new SelectionMenu());
                else{
                    WorldSelectionMenu next_Scene = new WorldSelectionMenu();
                    //next_Scene.changeWorld(indexWorld);
                    SceneManager.getInstance().addScene(next_Scene);
                }
                SceneManager.getInstance().goToNextScene();
                break;
            }
        }
    }

    private void createLevel() {
        // Creamos el parser del json
        Gson gson = new Gson();
        BufferedReader br = null;

        // Leemos el json
        try {
            br = engine.openAssetFile(levelName);
        } catch (IOException ex) {
            System.out.println("Error creating level");
        }

        // Deserializamos el json en un objeto con la info del nivel
        LevelInfo levelInfo = gson.fromJson(br, LevelInfo.class);

        // Asignamos los valores que hemos recogido a nuestra partida
        this.numColors = levelInfo.getCodeOpt();
        this.numColorsPerAttempt = levelInfo.getCodeSize();
        this.numAttempts = levelInfo.getAttempts();
        this.repeatColors = levelInfo.getRepeat();
    }

    public void setIndexWorld(int world){
        indexWorld = world;
    }

    public void savedGame(){loadedData = true;}
}
