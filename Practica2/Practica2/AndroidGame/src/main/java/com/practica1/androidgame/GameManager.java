package com.practica1.androidgame;

import android.content.Context;

import com.practica1.androidengine.Engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameManager {

    private static GameManager Instance;
    protected Engine engine;
    Context context;

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

    public void savePlayerData()
    {

        PlayerSerializeInfo playerSerializeInfo = new PlayerSerializeInfo(coins, 10, coins, coins);

        try{
            FileOutputStream fout = context.openFileOutput("player.txt", Context.MODE_PRIVATE);
            ObjectOutputStream out =  new ObjectOutputStream(fout);

            out.writeObject(playerSerializeInfo);
            out.flush();
            out.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al persistir datos del jugador");
            e.printStackTrace();
        }
    }

    public void loadPlayerData()
    {
        PlayerSerializeInfo playerSerializeInfo;
        try {
            FileInputStream file = context.openFileInput("player.txt");
            ObjectInputStream in = new ObjectInputStream(file);

            playerSerializeInfo = (PlayerSerializeInfo)in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        playerSerializeInfo.print();
    }

    public void setContext(Context context){
        this.context = context;
    }
}
