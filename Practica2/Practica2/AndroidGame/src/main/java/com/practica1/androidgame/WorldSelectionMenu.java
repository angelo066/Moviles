package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.TouchEvent;

import java.util.ArrayList;
import java.util.List;

public class WorldSelectionMenu extends Scene {

    //Botones con los que compramos skins
    private List<List<LevelObject>> levels;


    //Boton para volver a la pantalla anterior
    private ButtonObject buttonBack;

    //Boton para pasar al siguiente mundo
    private ButtonObject buttonNextWorld;

    //Boton para pasar al anterior mundo
    private ButtonObject buttonLastWorld;

    private Vector2 skin_Size = new Vector2(200,200);
    private Vector2 skin_Pos = new Vector2(20,450);

    private int offset = 200;
    private final int N_LEVELS_COLUMN = 3;
    private int actual_WORLD = 2;

    public WorldSelectionMenu(){
        this.width = 1080;
        this.height = 1920;
    }

    @Override
    public void init(Engine engine){
        super.init(engine);

        levels = new ArrayList<>();


        buttonBack = new ButtonObject(graphics, new Vector2(0, 20), new Vector2(100, 100), "volver.png");

        createButtons();
    }

    public void render(){
        //Fondo de al APP
        graphics.clear(Color.WHITE.getValue());

        // Fondo del juego
        graphics.setColor(Color.BLUE.getValue());
        graphics.fillRectangle(0, 0, width, height);


        /// Botones
        buttonBack.render();

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

            // Recorrer los botones del mundo en el que estamos
            for(int j = 0; j < levels.get(actual_WORLD).size(); j++)
            {
                // Hemos pulsado algun boton
                if(levels.get(actual_WORLD).get(j).handleInput(events.get(i)))
                {
                    String levelName = ResourceManager.getInstance().getLevel(actual_WORLD, j);
                    SceneManager.getInstance().addScene(new MasterMind(levelName));
                    SceneManager.getInstance().goToNextScene();
                }
            }
        }

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
            Vector2 pos = new Vector2(skin_Pos);
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



    }
}
