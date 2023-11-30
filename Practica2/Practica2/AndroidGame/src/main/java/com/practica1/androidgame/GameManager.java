package com.practica1.androidgame;

import com.practica1.androidengine.Engine;

public class GameManager {

    private static GameManager Instance;
    protected Engine engine;

    private int coins;

    //Numero total de skins;
    private int n_skins_Background;
    private int n_skins_Codes;
    private int n_skins_Color;

    //Indice de la skin que tengo puesta
    private int actual_Skin_Background;
    private int actual_Skin_Code;
    private int actual_Skin_Color;

    private GameManager(){

    }


    public static void Init(Engine engine){

        if (Instance == null) {
            Instance = new GameManager();
            Instance.engine = engine;
        }

    }


    public static GameManager getInstance() {
        return Instance;
    }

    public void setN_skins_Background(int n){n_skins_Background = n;}

    public int getN_skins_Background(){return n_skins_Background;}
}
