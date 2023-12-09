package com.practica1.androidgame;

import com.google.gson.Gson;
import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
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

        textWorld = new TextObject(graphics, new Vector2(banner_Pos.x, banner_Pos.y), "Nexa.ttf", "Mundo " + String.valueOf(actual_WORLD+1), Color.BLACK, 70, false, false);
        textWorld.center();

        createButtons();
        createBackgrounds();
    }

    public void render(){
        //Fondo de al APP
        graphics.clear(Color.WHITE.getValue());

        // Fondo del juego
        graphics.setColor(Color.WHITE.getValue());
        graphics.fillRectangle(0, 0, width, height);

        // Fondo del mundo
        worldBackgrounds.get(actual_WORLD).render();

        // Indicador de mundo
        graphics.setColor(Color.CYAN.getValue());
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

        boolean selected = false;
        Difficulty mode = Difficulty.EASY;

        // Dependiendo del boton, vamos a una escena u otra
        // Si es un boton de juego asignamos la dificultad
        for (int i = 0; i < events.size() && !selected; i++) {

            if (buttonBack.handleInput(events.get(i))) {
                SceneManager.getInstance().addScene(new MainMenu());
                SceneManager.getInstance().goToNextScene();
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
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
                    if(levels.get(actual_WORLD).get(j).handleInput(events.get(i)))
                    {
                        String levelName = ResourceManager.getInstance().getLevel(actual_WORLD, j);

                        MasterMind next_Scene = new MasterMind(levelName);
                        next_Scene.setIndexWorld(actual_WORLD);

                        SceneManager.getInstance().addScene(next_Scene);
                        SceneManager.getInstance().goToNextScene();
                        break;
                    }
                }
            }

        }

        //Que hace esto (Ahora mismo selected siempre es false)
        if (selected) {
            audio.stopSound("botonInterfaz.wav");
            audio.playSound("botonInterfaz.wav", false);

            SceneManager.getInstance().addScene(new MasterMind(mode));
            SceneManager.getInstance().goToNextScene();
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


        for(int i = 0; i < GameManager.getInstance().getUnlocked_lvls();i++){
            //Desbloqueamos los mundos
            levels.get(0).get(i).setUnlock(true);
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
            String styleName = ResourceManager.getInstance().getWorldSytle(i);

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
            styleNames[i] = worldStyle.getStyle();
        }

        // Inicializamos la lista de imagenes de fondos
        worldBackgrounds = new ArrayList<>();

        // Creamos las imagenes
        for(int i = 0; i < num_WORLDS; i++)
        {
            Vector2 pos = new Vector2(0, 200);
            Vector2 size = new Vector2(width, height-200);
            ImageObject background = new ImageObject(graphics, pos, size, styleNames[i] + ".png");
            worldBackgrounds.add(background);
            int a = 0;
        }
    }

    public void changeWorld(int world){
        actual_WORLD = world;
    }

}
