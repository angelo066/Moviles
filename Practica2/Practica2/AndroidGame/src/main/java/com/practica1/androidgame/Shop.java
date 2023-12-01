package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.Color;
import com.practica1.androidengine.TouchEvent;


import java.util.ArrayList;

public class Shop extends Scene {

    //Enum que indica que le estamos vendiendo al usuario
    private enum Skin_Type {BACKGROUND, CODE, COLORS}

    //Botones con los que compramos skins
    private BuyObject[] skins;

    //Precios de las skins
    private TextObject[] prices;

    //Imagenes que muestran lo que contiene la skin
    private ImageObject[] skin_Images;

    //Para tener en cuenta que estamos vendiendo
    private Skin_Type type;

    //Mostrar el icono de las monedas
    private ImageObject coins;

    //Escribir la cantidad de monedas que tiene el usuario
    private TextObject coin_cuantity;

    //Texto de la parte superior de la pantalla que nos indica en que tienda estamos
    private TextObject type_Text;

    //Boton para pasar a la siguiente pantalla
    private ButtonObject buttonForward;

    //Boton para volver a la pantalla anterior
    private ButtonObject buttonBack;

    private Vector2 skin_Size = new Vector2(200,200);
    private Vector2 skin_Pos = new Vector2(20,450);

    private int offset = 200;
    private final int N_SKIN_COLUMN = 3;

    public Shop(){
        this.width = 1080;
        this.height = 1920;
    }

    @Override
    public void init(Engine engine){
        super.init(engine);

        skins = new BuyObject[GameManager.getInstance().getN_skins_Background()];
        prices = new TextObject[GameManager.getInstance().getN_skins_Background()];

        Vector2 pos_Coin = new Vector2(900, 200);
        Vector2 size_Coin = new Vector2(100, 100);

        buttonBack = new ButtonObject(graphics, new Vector2(0, 20), new Vector2(100, 100), "volver.png");

        coins = new ImageObject(graphics, pos_Coin, size_Coin, "coins.png");

        coin_cuantity = new TextObject(graphics, new Vector2(pos_Coin.x, pos_Coin.y + size_Coin.y),
                "Nexa.ttf", "1000", Color.BLACK, 60, false, false);

        createButtons();
    }

    public void render(){
        //Fondo de al APP
        graphics.clear(Color.WHITE.getValue());

        // Fondo del juego
        graphics.setColor(Color.BLUE.getValue());
        graphics.fillRectangle(0, 0, width, height);

        //Objetos
        coins.render();

        /// Botones
        buttonBack.render();

        //Texto
        coin_cuantity.render();

        for(int i = 0; i < skins.length; i++){
            skins[i].render();
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
        }

        if (selected) {
            audio.stopSound("botonInterfaz.wav");
            audio.playSound("botonInterfaz.wav", false);
            SceneManager.getInstance().addScene(new MasterMind(mode));
            SceneManager.getInstance().goToNextScene();
        }
    }


    private void createButtons(){
        Vector2 pos = skin_Pos;

        /*for(int i = 0; i < GameManager.getInstance().getN_skins_Background();i++){
            pos.x *= i;

            if(pos.x > width){
                pos.x = 0;

                pos.y += skin_Size.y + offset;
            }

            skins[i] = new BuyObject(graphics, pos, skin_Size);
        }*/

        int aux = width / N_SKIN_COLUMN;
        Vector2 size = new Vector2(aux-100, aux-100);
        int xIndex = 0;
        for(int i = 0; i < GameManager.getInstance().getN_skins_Background();i++)
        {
            int xua = aux*xIndex + aux/2;
            pos.x = xua - size.x/2;

            xIndex++;

            // Si has llenado los huecos disponibles en esta fila pasas a la siguiente
            if(i > 0 && i % N_SKIN_COLUMN == 0){
                xIndex = 0;
                pos.y += size.y + offset;
            }

            skins[i] = new BuyObject(graphics, pos, size);
        }

    }
}
