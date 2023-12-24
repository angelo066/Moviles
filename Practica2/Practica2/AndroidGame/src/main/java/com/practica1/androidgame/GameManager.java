package com.practica1.androidgame;

import android.content.Context;
import android.util.Pair;

import com.practica1.androidengine.Engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Clase GameManager
 */
public class GameManager {

    private static GameManager Instance;

    protected Engine engine;
    private Context context;
    private int coins;

    //Indice de la skin que tengo puesta
    private int currentSkinBackground = -1; //-1 cuando no hay ninguna equipada
    private int currentSkinCode = -1; //-1 cuando no hay ninguna equipada
    private Palette currentSkinPalette = new Palette("", -1, -1, -1, -1);
    private int currentLvl = 0;
    private int currentWorld = 0;

    //First = mundo Second = Level
    private Pair<Integer, Integer> lastLevelUnlocked = new Pair<>(0, 0);

    private boolean[] unlockedBackgrounds;
    private boolean[] unlockedCodes;
    private boolean[] unlockedPalettes;

    private GameManager() {

    }

    /**
     * Inicializa el GameManager
     */
    public static void Init(Engine engine) {
        if (Instance == null) {
            Instance = new GameManager();
            Instance.engine = engine;
        }
    }

    /**
     * @return Instancia del GameManager
     */
    public static GameManager getInstance() {
        return Instance;
    }

    /**
     * Guarda la informacion del jugador en un archivo
     */
    public void savePlayerData() {
        PlayerSerializeInfo playerSerializeInfo = new PlayerSerializeInfo(coins, lastLevelUnlocked.first, lastLevelUnlocked.second,
                currentSkinBackground, currentSkinCode, currentSkinPalette, unlockedBackgrounds, unlockedCodes, unlockedPalettes);

        try {
            FileOutputStream fout = context.openFileOutput("player.txt", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fout);

            out.writeObject(playerSerializeInfo);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error de entrada/salida al persistir datos del jugador");
            e.printStackTrace();
        }
    }

    /**
     * Carga la informacion del jugador desde archivo
     */
    public void loadPlayerData() {
        PlayerSerializeInfo playerSerializeInfo;

        File fileExist = new File("/data/user/0/com.practica1.androidgame/files/player.txt");

        if (fileExist.exists()) {
            try {
                FileInputStream file = context.openFileInput("player.txt");
                ObjectInputStream in = new ObjectInputStream(file);

                playerSerializeInfo = (PlayerSerializeInfo) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Cargamos la info deserializada
            playerSerializeInfo.print();

            String hashgenerator = NDKManager.getInstance().getKey() + playerSerializeInfo.toString();
            String hash = NDKManager.getInstance().createHash(hashgenerator);
            System.out.println(hash);

            coins = playerSerializeInfo.getCoins();
            lastLevelUnlocked = new Pair<Integer, Integer>(playerSerializeInfo.getUnlock_world(), playerSerializeInfo.getUnlockLevels());
            ;
            currentSkinBackground = playerSerializeInfo.getBackgroundSkin();
            currentSkinCode = playerSerializeInfo.getCodeSkin();
            currentSkinPalette = playerSerializeInfo.getPaleteSkin();
            unlockedBackgrounds = playerSerializeInfo.getUnlocked_Backgrounds();
            unlockedCodes = playerSerializeInfo.getUnlocked_Codes();
            unlockedPalettes = playerSerializeInfo.getUnlocked_Palettes();

            //Si no hay ninguna equipada ponemos la predeterminada
            if (currentSkinPalette.getThumbnail() == "")
                currentSkinPalette = ResourceManager.getInstance().getDefaultPalette();
        } else {
            defaultValues();
        }


    }

    /**
     * Establece el contexto de la aplicacion
     *
     * @param context Contexto de la aplicacion
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Anyade monedas
     *
     * @param coins Monedas a anyadir
     */
    public void addCoins(int coins) {
        this.coins += coins;
    }

    /**
     * Resta monedas al comprar
     *
     * @param coins Monedas a restar
     */
    public void buyObject(int coins) {
        this.coins -= coins;
    }

    /**
     * @return Numero de monedas del jugador
     */
    public int getCoins() {
        return coins;
    }

    /**
     * @return El ultimo nivel desbloqueado
     */
    public Pair<Integer, Integer> getUnlockedLvls() {
        return lastLevelUnlocked;
    }

    /**
     * Pasa de nivel y de mundo si es necesario al pasar un nivel
     */
    public void levelCompleted() {
        if (lastLevelUnlocked.second == ResourceManager.getInstance().getNumLevels(lastLevelUnlocked.first) - 1) {
            lastLevelUnlocked = new Pair<Integer, Integer>(lastLevelUnlocked.first + 1, 0);
        } else {
            lastLevelUnlocked = new Pair<Integer, Integer>(lastLevelUnlocked.first, lastLevelUnlocked.second + 1);
        }
    }

    /**
     * Establece el nivel actual
     *
     * @param lvl
     */
    public void setCurrentLvl(int lvl) {
        currentLvl = lvl;
    }

    /**
     * Establece el mundo actual
     *
     * @param wrld
     */
    public void setCurrentWorld(int wrld) {
        currentWorld = wrld;
    }

    /**
     * @return Nivel actual
     */
    public int getCurrentLvl() {
        return currentLvl;
    }

    /**
     * @return Mundo actual
     */
    public int getCurrentWorld() {
        return currentWorld;
    }

    /**
     * @return Si el nivel actual es el ultimo
     */
    public boolean isLastLevel() {
        return currentLvl == lastLevelUnlocked.second && currentWorld == lastLevelUnlocked.first;
    }

    /**
     * Establece el background
     *
     * @param background
     */
    public void equipBackgroundSkin(int background) {
        currentSkinBackground = background;
    }

    public int getCurrentSkinBackground() {
        return currentSkinBackground;
    }

    /**
     * Establece el code
     *
     * @param code
     */
    public void equipCode(int code) {
        currentSkinCode = code;
    }

    /**
     * @return Code actual
     */
    public int getCurrentSkinCode() {
        return currentSkinCode;
    }

    /**
     * Establece la palette
     *
     * @param palette
     */
    public void equipPalette(Palette palette) {
        currentSkinPalette = palette;
    }

    /**
     * @return La palette actual
     */
    public Palette getCurrentSkinPalette() {
        return currentSkinPalette;
    }

    /**
     * @return Context de la aplicacion
     */
    public Context getContext() {
        return context;
    }

    /**
     * Desbloquea un tipo de skin
     *
     * @param type  Tipo de skin
     * @param index Index de skin
     */
    public void unlockedSkin(int type, int index) {
        if (type == 0) {
            unlockedBackgrounds[index] = true;
        } else if (type == 1) {
            unlockedCodes[index] = true;
        } else {
            unlockedPalettes[index] = true;
        }

    }

    /**
     * @param index
     * @return Skins desbloqueadas por index
     * 0 background 1 codes 2 palettes
     */
    public boolean[] getUnlockedSkinsByIndex(int index) {
        if (index == 0) return unlockedBackgrounds;
        if (index == 1) return unlockedCodes;

        return unlockedPalettes;
    }

    /**
     * Establece las skins a los valores por defecto
     */
    public void defaultValues() {

        unlockedBackgrounds = new boolean[ResourceManager.getInstance().getNumShopBackgrounds()];
        unlockedCodes = new boolean[ResourceManager.getInstance().getNumShopCodes()];
        unlockedPalettes = new boolean[ResourceManager.getInstance().getNumShopPalettes()];

        currentSkinPalette = ResourceManager.getInstance().getDefaultPalette();
    }
}
