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
    private BuyObject[] skins_Back;
    private BuyObject[] skins_Code;
    private BuyObject[] skins_Color;

    //Para tener en cuenta que estamos vendiendo
    private int type;

    //Mostrar el icono de las monedas
    private ImageObject coins;

    //Escribir la cantidad de monedas que tiene el usuario
    private TextObject coin_cuantity;

    //Texto de la parte superior de la pantalla que nos indica en que tienda estamos
    private TextObject type_Text;

    //Boton para pasar a la siguiente pantalla
    private ButtonObject buttonForward;
    private ButtonObject buttonBackward;

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

        //Provisional, hay que cambiar numeros y demás
        skins_Back = new BuyObject[GameManager.getInstance().getN_skins_Background()];
        skins_Code = new BuyObject[GameManager.getInstance().getN_skins_Background()];
        skins_Color = new BuyObject[GameManager.getInstance().getN_skins_Background()];

        Vector2 pos_Coin = new Vector2(900, 200);
        Vector2 size_Coin = new Vector2(100, 100);

        buttonBack = new ButtonObject(graphics, new Vector2(0, 20), new Vector2(100, 100), "volver.png");

        coins = new ImageObject(graphics, pos_Coin, size_Coin, "coins.png");

        coin_cuantity = new TextObject(graphics, new Vector2(pos_Coin.x, pos_Coin.y + size_Coin.y),
                "Nexa.ttf", "1000", Color.BLACK, 60, false, false);

        type_Text = new TextObject(graphics, new Vector2(width/2, 140),
                "Nexa.ttf", "Fondos", Color.BLACK, 60, false, false);

        type_Text.center();

        createButtons();
        createNavigators();
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
        buttonForward.render();
        buttonBackward.render();

        //Texto
        coin_cuantity.render();
        type_Text.render();

        //Revisable
        if(Skin_Type.values()[type] == Skin_Type.BACKGROUND){
            for(int i = 0; i < skins_Back.length; i++){
                skins_Back[i].render();
            }
        }
        else if(Skin_Type.values()[type] == Skin_Type.CODE){
            for(int i = 0; i < skins_Code.length; i++){
                skins_Code[i].render();
            }
        }
        else{
            for(int i = 0; i < skins_Color.length; i++){
                skins_Color[i].render();
            }
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

            if(buttonBackward.handleInput(events.get(i))){
                type--;
                if(type < 0) type = Skin_Type.values().length;
                break;
            }

            if(buttonForward.handleInput(events.get(i))){
                type++;
                if(type > Skin_Type.values().length) type = 0;
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
        createBuyObjects(skins_Back, "central.png");
        createBuyObjects(skins_Code, "autobus.png");
        createBuyObjects(skins_Color, "taberna.png");

    }

    private void createBuyObjects(BuyObject[] skins, String image) {
        //Vector2 pos = skin_Pos;   (Esto hace que si cambias pos cambien los valores de skin_Pos)

        Vector2 pos = new Vector2(skin_Pos.x, skin_Pos.y);

        int aux = width / N_SKIN_COLUMN;
        Vector2 size = new Vector2(aux - 100, aux - 100);
        int xIndex = 0;

        for (int i = 0; i < GameManager.getInstance().getN_skins_Background(); i++) {
            int diff = aux * (xIndex) + aux / 2;
            pos.x = diff - size.x / 2;

            // Si has llenado los huecos disponibles en esta fila pasas a la siguiente
            if (xIndex >= N_SKIN_COLUMN) {
                xIndex = 0;
                pos.y += size.y + offset;
                diff = aux / 2;
                pos.x = diff - size.x / 2;
            }
            xIndex++;


            skins[i] = new BuyObject(graphics, pos, size, image);
            skins[i].setPrice(100 * i);
        }
    }

    private void createNavigators(){
        buttonForward = new ButtonObject(graphics, new Vector2(width/2 + 100, 100), new Vector2(80,80), "ArrowNavigators.png");
        buttonBackward = new ButtonObject(graphics, new Vector2(width/2 - 200, 100), new Vector2(80,80), "ArrowNavigators_Left.png");
    }
}
