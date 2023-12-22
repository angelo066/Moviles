package com.practica1.androidgame;

import com.google.gson.Gson;
import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.SensorHandler;
import com.practica1.androidengine.TouchEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldSelectionMenu extends Scene {

    //Botones con los que compramos skins
    private List<List<LevelObject>> levels;

    // Lista de fondos que usamos // -> al iniciar la escena, se cargan los fondos que tenga seleccionado el jugador (estaran los indices en el GM imagino)
    private List<ImageObject> worldBackgrounds;

    //Boton para volver a la pantalla anterior
    private ButtonObject buttonBack;

    //Boton para pasar al siguiente mundo
    private ButtonObject buttonNextWorld;

    //Boton para pasar al anterior mundo
    private ButtonObject buttonLastWorld;
    private TextObject textWorld;

    private Vector2 skin_Size = new Vector2(200,200);
    private Vector2 skin_Pos = new Vector2(20,450);

    private Vector2 banner_Pos;

    private int offset = 200;
    private final int N_LEVELS_COLUMN = 3;
    private int actual_WORLD = 0;
    private int num_WORLDS;

    public WorldSelectionMenu(){
        this.width = 1080;
        this.height = 1920;
        banner_Pos = new Vector2(width/2, 120);
        num_WORLDS = ResourceManager.getInstance().getNumWorlds();
    }

    @Override
    public void init(Engine engine){
        super.init(engine);

        levels = new ArrayList<>();

        buttonBack = new ButtonObject(graphics, new Vector2(banner_Pos.x - 450, banner_Pos.y), new Vector2(100, 100), "volver.png");
        buttonBack.center();
        buttonNextWorld = new ButtonObject(graphics, new Vector2(banner_Pos.x + 270, banner_Pos.y), new Vector2(100, 100), "ArrowNavigators.png");
        buttonNextWorld.center();
        buttonLastWorld = new ButtonObject(graphics, new Vector2(banner_Pos.x - 270, banner_Pos.y), new Vector2(100, 100), "ArrowNavigators_Left.png");
        buttonLastWorld.center();

        int colorText = GameManager.getInstance().getActual_Skin_Palette().getColor_2();
        textWorld = new TextObject(graphics, new Vector2(banner_Pos.x, banner_Pos.y), "Nexa.ttf", "Mundo " + String.valueOf(actual_WORLD+1), colorText, 70, false, false);
        textWorld.center();

        createButtons();
        createBackgrounds();


    }

    public void render(){
        // Fondo de APP
        int backColor = GameManager.getInstance().getActual_Skin_Palette().color_background();
        graphics.clear(backColor);

        // Fondo de Juego
        graphics.setColor(backColor);
        graphics.fillRectangle(0, 0, width, height);

        // Fondo del mundo
        worldBackgrounds.get(actual_WORLD).render();

        // Indicador de mundo
        graphics.setColor(GameManager.getInstance().getActual_Skin_Palette().getColor_1());
        Vector2 rectSize = new Vector2(400, 100);
        graphics.fillRoundRectangle(banner_Pos.x - rectSize.x /2, banner_Pos.y - rectSize.y/2, 400, 100, 20);
        textWorld.render();

        /// Botones
        buttonBack.render();
        buttonNextWorld.render();
        buttonLastWorld.render();

        // Renderizar los niveles del mundo que toque
        for(int i = 0; i < levels.get(actual_WORLD).size(); i++)
        {
            levels.get(actual_WORLD).get(i).render();
        }
    }

    public void handleInput(ArrayList<TouchEvent> events) {


        // Dependiendo del boton, vamos a una escena u otra
        // Si es un boton de juego asignamos la dificultad
        for (int i = 0; i < events.size(); i++) {

            if (buttonBack.handleInput(events.get(i))) {
                SceneManager.getInstance().addScene(new MainMenu());
                SceneManager.getInstance().goToNextScene();
                audio.playSound("botonInterfaz.wav", false);
                audio.stopSound("botonInterfaz.wav");
                break;
            }

            // Botones de avanzar o retroceder mundo
            else if(buttonNextWorld.handleInput(events.get(i)))
            {
                actual_WORLD++;
                if(actual_WORLD > num_WORLDS - 1){
                    actual_WORLD = 0;
                }

                textWorld.setText("Mundo " + String.valueOf(actual_WORLD+1));
                break;

            }
            else if(buttonLastWorld.handleInput(events.get(i)))
            {

                actual_WORLD--;
                if(actual_WORLD < 0){
                    actual_WORLD = num_WORLDS -1;
                }

                textWorld.setText("Mundo " + String.valueOf(actual_WORLD+1));
                break;

            }
            else {
                // Recorrer los botones del mundo en el que estamos
                for(int j = 0; j < levels.get(actual_WORLD).size(); j++)
                {
                    // Hemos pulsado algun boton
                    if(levels.get(actual_WORLD).get(j).isUnlock() && levels.get(actual_WORLD).get(j).handleInput(events.get(i)))
                    {
                        String levelName = ResourceManager.getInstance().getLevel(actual_WORLD, j);

                        GameManager gm = GameManager.getInstance();

                        gm.setActualWorld(actual_WORLD);
                        gm.setActualLvl(j);

                        MasterMind next_Scene = new MasterMind(levelName);
                        next_Scene.setIndexWorld(actual_WORLD);

                        SceneManager.getInstance().addScene(next_Scene);
                        SceneManager.getInstance().goToNextScene();
                        break;
                    }
                }
            }

        }

        //Lo pongo en el inputHandler porque es input al final del día
        SensorHandler sensorHandler = engine.getSensorHandler();
        if(sensorHandler.getAcccelerometerValuesAdded(false) > 40){
            audio.stopSound("silla.wav");
            audio.playSound("silla.wav", false);
        }
    }


    private void createButtons()
    {
        // Recorremos cada mundo
        int N_worlds = ResourceManager.getInstance().getNumWorlds();
        for(int i = 0; i < N_worlds; i++)
        {
            List<LevelObject> levels_of_world = new ArrayList<>();

            // Recorremos los niveles de cada mundo
            Vector2 pos = new Vector2(0, 300);
            int aux = width / N_LEVELS_COLUMN;
            Vector2 size = new Vector2(aux-100, aux-100);
            int xIndex = 0;
            int N_levels = ResourceManager.getInstance().getNumLevels(0);
            for(int j = 0; j < N_levels ; j++)
            {
                int diff = aux * (xIndex)+ aux/2;
                pos.x = diff - size.x/2;

                // Si has llenado los huecos disponibles en esta fila pasas a la siguiente
                if(xIndex >= N_LEVELS_COLUMN){
                    xIndex = 0;
                    pos.y += size.y + offset;
                    diff = aux/2;
                    pos.x = diff - size.x/2;
                }
                xIndex++;

                // Anadimos el nivel en la lista de determinado mundo
                levels_of_world.add(new LevelObject(graphics, pos, size, j));
            }

            // Anadimos la lista de niveles a la lista de mundos
            levels.add(levels_of_world);
        }


        int worlds_Unlocked = GameManager.getInstance().getUnlocked_lvls().first;

        //Desbloqueamos lo que haya del primer mundo
        if(worlds_Unlocked == 0){
            for(int j=0; j <= GameManager.getInstance().getUnlocked_lvls().second;j++){
                //Desbloqueamos los mundos
                levels.get(0).get(j).setUnlock(true);
            }
        }
        else if(worlds_Unlocked == 1){
            //Desbloqueamos el nivel 0 completamente
            for(int j=0; j <= ResourceManager.getInstance().getNumLevels(0) - 1;j++){
                //Desbloqueamos los mundos
                levels.get(0).get(j).setUnlock(true);
            }

            for(int j=0; j <= GameManager.getInstance().getUnlocked_lvls().second;j++){
                //Desbloqueamos los mundos
                levels.get(1).get(j).setUnlock(true);
            }
        }
        else {  //Si están todos desbloqueados
            //Desbloqueamos el nivel 0 completamente
            for(int j=0; j <= ResourceManager.getInstance().getNumLevels(0) - 1;j++){
                //Desbloqueamos los mundos
                levels.get(0).get(j).setUnlock(true);
            }

            //Desbloqueamos el nivel 1 completamente
            for(int j=0; j <= ResourceManager.getInstance().getNumLevels(1) - 1;j++){
                //Desbloqueamos los mundos
                levels.get(1).get(j).setUnlock(true);
            }

            for(int j=0; j <= GameManager.getInstance().getUnlocked_lvls().second;j++){
                //Desbloqueamos los mundos
                levels.get(2).get(j).setUnlock(true);
            }
        }


    }

    private void createBackgrounds()
    {
        // Cargar todos los estilos de los fondos
        // Creamos el parser del json
        Gson gson  = new Gson();
        BufferedReader br = null; // -> esto igual se puede sacar al resource manager o hacer algo en el motor ?

        // Guardamos los nombres de las imagenes en una array
        String[] styleNames = new String[num_WORLDS];
        for(int i = 0; i < num_WORLDS; i++)
        {
            String styleName = ResourceManager.getInstance().getWorldSytle(i); // TODO -> regulin esto

            // Leemos el json
            try {
                br = engine.openAssetFile(styleName);
            }
            catch (IOException ex)
            {
                System.out.println("Error loading world style");
            }

            // Deserializamos el json en un objeto con la info del mundo
            WordlInfo worldStyle = gson.fromJson(br, WordlInfo.class);
            styleNames[i] = worldStyle.getWorld_background();
        }

        // Inicializamos la lista de imagenes de fondos
        worldBackgrounds = new ArrayList<>();

        // Creamos las imagenes
        for(int i = 0; i < num_WORLDS; i++)
        {
            Vector2 pos = new Vector2(0, 200);
            Vector2 size = new Vector2(width, height-200);
            ImageObject background = new ImageObject(graphics, pos, size, "backgrounds/" + styleNames[i] + ".png");
            worldBackgrounds.add(background);
        }
    }

    public void changeWorld(int world){
        actual_WORLD = world;
    }

}
