package com.practica1.androidgame;

import android.content.Context;
import android.util.Pair;

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
    //Está desbloqueado el primer nivel
    private int unlocked_lvls = 1;

    //Numero total de skins;
    private int n_skins_Background;
    private int n_skins_Codes;
    private int n_skins_Color;

    //Indice de la skin que tengo puesta
    private int actual_Skin_Background;
    private int actual_Skin_Code;
    private int actual_Skin_Color;

    private int actualLvl = 0;
    private int actualWorld = 0;

    private Pair<Integer,Integer> lastLevelUnlocked = new Pair<>(0,0);

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
        // Cuidado que el el archivo que creamos, como indica, el context, es : PRIVADO
        // Que yo el otro dia me tire 2 horas buscando el archivito 💀
        // Que que quiere decir privado, pues efectivamente que no aparece en el explorador de archivos

        PlayerSerializeInfo playerSerializeInfo = new PlayerSerializeInfo(coins, unlocked_lvls);

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

        // Cargamos la info deserializada
        playerSerializeInfo.print();
        coins = playerSerializeInfo.getCoins();
        unlocked_lvls = playerSerializeInfo.getUnlockLevels();
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void addCoins(int n){
        coins = coins + n;
    }

    //Metodo que se usa para restar monedas cuando compramos
    public void buyObject(int p) {coins = coins - p;}

    public int getCoins(){
        return coins;
    }

    public Pair<Integer,Integer> getUnlocked_lvls(){return lastLevelUnlocked;}
    public void level_Completed(){
        //Pasas de mundo
        if(lastLevelUnlocked.second == ResourceManager.getInstance().getNumLevels(lastLevelUnlocked.first) - 1){
            lastLevelUnlocked = new Pair<Integer, Integer>(lastLevelUnlocked.first + 1, 0);
        }
        else{
            lastLevelUnlocked = new Pair<Integer, Integer>(lastLevelUnlocked.first, lastLevelUnlocked.second + 1);
        }
    }

    public void setActualLvl(int lvl){actualLvl = lvl;}
    public void setActualWorld(int wrld){actualWorld = wrld;}

    public int getActualLvl(){return actualLvl;}
    public int getActualWorld(){return actualWorld;}

    public boolean isLasLevel(){
        return actualLvl == lastLevelUnlocked.second && actualWorld == lastLevelUnlocked.first;
    }

    public void equipBackgroundSkin(int s){actual_Skin_Background = s;}
}
