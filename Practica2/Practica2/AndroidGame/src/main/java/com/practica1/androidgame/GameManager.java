package com.practica1.androidgame;

import android.content.Context;
import android.util.Pair;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
    private int actual_Skin_Background = -1; //-1 cuando no hay ninguna equipada
    private int actual_Skin_Code= -1; //-1 cuando no hay ninguna equipada
    private Palette actual_Skin_Palette = new Palette("", -1, -1, -1, -1);
    private int actualLvl = 0;
    private int actualWorld = 0;

    //First = mundo Second = Level
    private Pair<Integer,Integer> lastLevelUnlocked = new Pair<>(0,0);

    private boolean[] unlocked_Backgrounds;
    private boolean[] unlocked_Codes;
    private boolean[] unlocked_Palettes;

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


        PlayerSerializeInfo playerSerializeInfo = new PlayerSerializeInfo(coins, lastLevelUnlocked.first, lastLevelUnlocked.second,
                                                    actual_Skin_Background, actual_Skin_Code, actual_Skin_Palette, unlocked_Backgrounds, unlocked_Codes, unlocked_Palettes);

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

        File fileExist = new File("/data/user/0/com.practica1.androidgame/files/player.txt");

        if(fileExist.exists()){
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
            lastLevelUnlocked = new Pair<Integer,Integer>(playerSerializeInfo.getUnlock_world(), playerSerializeInfo.getUnlockLevels()); ;
            actual_Skin_Background = playerSerializeInfo.getBackgroundSkin();
            actual_Skin_Code = playerSerializeInfo.getCodeSkin();
            actual_Skin_Palette = playerSerializeInfo.getPaleteSkin();
            unlocked_Backgrounds = playerSerializeInfo.getUnlocked_Backgrounds();
            unlocked_Codes = playerSerializeInfo.getUnlocked_Codes();
            unlocked_Palettes = playerSerializeInfo.getUnlocked_Palettes();

            //Si no hay ninguna equipada ponemos la predeterminada
            if(actual_Skin_Palette.getThumbnail() == "")
                actual_Skin_Palette = ResourceManager.getInstance().getDefault_Palette();
        }
        else{
            defaultValues();
        }


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

    public int getActual_Skin_Background(){return actual_Skin_Background;}

    public void equipCode(int s){actual_Skin_Code = s;}

    public int getActual_Skin_Code(){return actual_Skin_Code;}
    public void equipPalette(Palette p){
        actual_Skin_Palette = p;
    }

    public Palette getActual_Skin_Palette(){return actual_Skin_Palette;}

    public Context getContext(){return context;}

    public void unlockedSkin(int type, int index){
        if(type == 0){
            unlocked_Backgrounds[index] = true;
        }
        else if(type == 1){
            unlocked_Codes[index] = true;
        }
        else{
            unlocked_Palettes[index] = true;
        }

    }

    //0 background 1 codes 2 palettes
    public boolean[] getUnlockedSkinsByIndex(int index){
        if(index == 0) return unlocked_Backgrounds;
        if(index == 1) return unlocked_Codes;

        return unlocked_Palettes;
    }

    public void defaultValues(){

        unlocked_Backgrounds = new boolean[ResourceManager.getInstance().shop_backgrounds.size()];
        unlocked_Codes = new boolean[ResourceManager.getInstance().shop_codes.size()];
        unlocked_Palettes = new boolean[ResourceManager.getInstance().shop_palettes.size()];

        actual_Skin_Palette = ResourceManager.getInstance().getDefault_Palette();
    }
}
