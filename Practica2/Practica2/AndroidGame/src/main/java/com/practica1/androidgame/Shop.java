package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
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

    private ButtonObject buttonBackToDefault;

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
        skins_Back = new BuyObject[ResourceManager.getInstance().shop_backgrounds.size()];
        skins_Code = new BuyObject[ResourceManager.getInstance().shop_codes.size()];
        skins_Color = new BuyObject[ResourceManager.getInstance().shop_palettes.size()];

        Vector2 pos_Coin = new Vector2(900, 200);
        Vector2 size_Coin = new Vector2(100, 100);

        buttonBack = new ButtonObject(graphics, new Vector2(0, 20), new Vector2(100, 100), "volver.png");

        coins = new ImageObject(graphics, pos_Coin, size_Coin, "coins.png");

        int colorText = GameManager.getInstance().getActual_Skin_Palette().getColor_2();
        coin_cuantity = new TextObject(graphics, new Vector2(pos_Coin.x, pos_Coin.y + size_Coin.y),
                "Nexa.ttf", String.valueOf(GameManager.getInstance().getCoins()), colorText, 60, false, false);

        type_Text = new TextObject(graphics, new Vector2(width/2, 140),
                "Nexa.ttf", "Fondos", colorText, 60, false, false);

        type_Text.center();

        createButtons();
        createNavigators();
    }

    public void render(){
        // Fondo de APP
        GameManager gm = GameManager.getInstance();
        int backColor = GameManager.getInstance().getActual_Skin_Palette().color_background();
        graphics.clear(backColor);

        // Fondo de Juego
        graphics.setColor(backColor);
        graphics.fillRectangle(0, 0, width, height);

        //Objetos
        coins.render();

        /// Botones
        buttonBack.render();
        buttonForward.render();
        buttonBackward.render();
        buttonBackToDefault.render();

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


        // Dependiendo del boton, vamos a una escena u otra
        // Si es un boton de juego asignamos la dificultad
        for (int i = 0; i < events.size(); i++) {

            if (buttonBack.handleInput(events.get(i))) {
                SceneManager.getInstance().addScene(new MainMenu());
                SceneManager.getInstance().goToNextScene();
                audio.stopSound("botonInterfaz.wav");
                audio.playSound("botonInterfaz.wav", false);
                break;
            }

            if(buttonBackward.handleInput(events.get(i))){
                type--;
                if(type < 0){
                    type = Skin_Type.values().length - 1;
                }

                changeText();

                break;
            }

            if(buttonForward.handleInput(events.get(i))){
                type++;
                if(type > Skin_Type.values().length - 1){
                    type = 0;
                }

                changeText();
                break;
            }

            if(Skin_Type.values()[type] == Skin_Type.BACKGROUND) {
                for (int b = 0; b < skins_Back.length; b++) {
                    GameManager gm = GameManager.getInstance();
                    BuyObject skin = skins_Back[b];
                    int coins = gm.getCoins();
                    int price = skin.getPrice();

                    if (skin.getButton().handleInput(events.get(i)) && coins >= price && !skin.isUnlocked()) {
                        skin.setUnlock(true);
                        gm.unlockedSkin(0, b);
                        gm.buyObject(price);
                        gm.equipBackgroundSkin(b);
                    }
                }
            }

            if(Skin_Type.values()[type] == Skin_Type.CODE){
                for(int c = 0; c < skins_Code.length;c++){
                    GameManager gm = GameManager.getInstance();
                    BuyObject skin = skins_Code[c];
                    int coins = gm.getCoins();
                    int price = skin.getPrice();

                    if(skin.getButton().handleInput(events.get(i)) && coins >= price && !skin.isUnlocked() ){
                        skin.setUnlock(true);
                        gm.unlockedSkin(1, c);
                        gm.buyObject(price);
                        gm.equipCode(c);
                    }
                }
            }

            if(Skin_Type.values()[type] == Skin_Type.COLORS){
                for(int c = 0; c < skins_Color.length;c++){
                    GameManager gm = GameManager.getInstance();
                    BuyObject skin = skins_Color[c];
                    int coins = gm.getCoins();
                    int price = skin.getPrice();

                    if(skin.getButton().handleInput(events.get(i)) && coins >= price && !skin.isUnlocked() ){
                        skin.setUnlock(true);
                        gm.buyObject(price);
                        gm.unlockedSkin(2, c);
                        gm.equipPalette(ResourceManager.getInstance().shop_palettes.get(c));

                        // Recargar la escena de colores
                        coin_cuantity.resetColor();
                        type_Text.resetColor();

                        for(int j = 0; j < skins_Back.length; j++)
                        {
                            skins_Back[j].resetColor();
                        }
                        for(int j = 0; j < skins_Code.length; j++)
                        {
                            skins_Code[j].resetColor();
                        }
                        for(int j = 0; j < skins_Color.length; j++)
                        {
                            skins_Color[j].resetColor();
                        }
                    }
                }
            }

            if(buttonBackToDefault.handleInput(events.get(i))){
                if(Skin_Type.values()[type] == Skin_Type.BACKGROUND) {
                    GameManager.getInstance().equipBackgroundSkin(-1);
                }

                if(Skin_Type.values()[type] == Skin_Type.CODE){
                    GameManager.getInstance().equipCode(-1);
                }

                if(Skin_Type.values()[type] == Skin_Type.COLORS){
                    GameManager.getInstance().equipPalette(ResourceManager.getInstance().getDefault_Palette());
                }
            }
        }

    }

    private void changeText() {
        if(Skin_Type.values()[type] == Skin_Type.BACKGROUND){
            type_Text.setText("Fondos");
        }
        else if(Skin_Type.values()[type] == Skin_Type.CODE){
            type_Text.setText("Codigos");
        }
        else{
            type_Text.setText("Colores");
        }
    }

    private void createButtons(){

        createBackgroundsShop();
        createCodePacksShop();
        createPalettesShop();

        buttonBackToDefault = new ButtonObject(graphics, new Vector2(width - skin_Size.x, height - skin_Size.y),
                                                                skin_Size, "backToMonkey.jpg");
    }

    private void createBackgroundsShop()
    {
        boolean[] unlocked_Skins = GameManager.getInstance().getUnlockedSkinsByIndex(0);
        Vector2 pos = new Vector2(skin_Pos.x, skin_Pos.y);

        int aux = width / N_SKIN_COLUMN;
        Vector2 size = new Vector2(aux - 100, aux - 100);
        int xIndex = 0;

        int a = ResourceManager.getInstance().shop_backgrounds.size();
        for(int i = 0; i < ResourceManager.getInstance().shop_backgrounds.size(); i++)
        {
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

            // Miniatura
            String image = ResourceManager.getInstance().shop_backgrounds.get(i).first;

            // Crear el objeto
            skins_Back[i] = new BuyObject(graphics, pos, size, image);
            if(unlocked_Skins[i]){
                skins_Back[i].setUnlock(true);
            }
            skins_Back[i].setPrice(100 * i);
        }
    }
    private void createCodePacksShop()
    {
        boolean[] unlocked_Skins = GameManager.getInstance().getUnlockedSkinsByIndex(1);
        Vector2 pos = new Vector2(skin_Pos.x, skin_Pos.y);

        int aux = width / N_SKIN_COLUMN;
        Vector2 size = new Vector2(aux - 100, aux - 100);
        int xIndex = 0;

        for(int i = 0; i < ResourceManager.getInstance().shop_codes.size(); i++)
        {
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

            // Miniatura
            String image = ResourceManager.getInstance().shop_codes.get(i).first;

            // Crear el objeto
            skins_Code[i] = new BuyObject(graphics, pos, size, image);
            if(unlocked_Skins[i]){
                skins_Code[i].setUnlock(true);
            }
            skins_Code[i].setPrice(100 * i);
        }
    }

    private void createPalettesShop()
    {
        boolean[] unlocked_Skins = GameManager.getInstance().getUnlockedSkinsByIndex(2);
        Vector2 pos = new Vector2(skin_Pos.x, skin_Pos.y);

        int aux = width / N_SKIN_COLUMN;
        Vector2 size = new Vector2(aux - 100, aux - 100);
        int xIndex = 0;

        for(int i = 0; i < ResourceManager.getInstance().shop_palettes.size(); i++)
        {
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

            // Miniatura
            String image = "thumbnails/" + ResourceManager.getInstance().shop_palettes.get(i).getThumbnail();

            // Crear el objeto
            skins_Color[i] = new BuyObject(graphics, pos, size, image);
            if(unlocked_Skins[i]){
                skins_Color[i].setUnlock(true);
            }
            skins_Color[i].setPrice(100 * i);
        }
        int a = 0;
    }
    private void createNavigators(){
        buttonForward = new ButtonObject(graphics, new Vector2(width/2 + 100, 100), new Vector2(80,80), "ArrowNavigators.png");
        buttonBackward = new ButtonObject(graphics, new Vector2(width/2 - 200, 100), new Vector2(80,80), "ArrowNavigators_Left.png");
    }
}
