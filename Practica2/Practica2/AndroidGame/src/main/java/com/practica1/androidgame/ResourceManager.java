package com.practica1.androidgame;

import android.util.Pair;

import com.google.gson.Gson;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Gestor de recursos
 */
public class ResourceManager {
    private static ResourceManager Instance;
    protected Engine engine;
    protected HashMap<String, Image> images;
    protected HashMap<String, Font> fonts;
    protected List<List<String>> levels;
    protected List<Pair<String, String>> shop_backgrounds;
    protected List<Pair<String, String>> shop_codes;

    //PROVISIONAL
    int n_Images;

    private ResourceManager() {
        images = new HashMap<>();
        fonts = new HashMap<>();
        levels = new ArrayList<>();
        shop_codes = new ArrayList<>();
        shop_backgrounds = new ArrayList<>();
    }

    /**
     * Inicializa el gestor
     *
     * @param engine
     */
    public static void Init(Engine engine) {
        if (Instance == null) {
            Instance = new ResourceManager();
            Instance.engine = engine;
        }
    }

    /**
     * Libera el gestor
     */
    public static void Release() {
        Instance.images.clear();
        Instance.fonts.clear();
        Instance.engine = null;
        Instance = null;
    }

    /**
     * @return La instancia del gestor de recursos
     */
    public static ResourceManager getInstance() {
        return Instance;
    }


    /**
     * Crea una imagen
     *
     * @param file Archivo de la imagen
     * @return Imagen creada
     */
    public Image createImage(String file) {
        if (images.containsKey(file)) {
            return images.get(file);
        } else {
            n_Images++;
            Image newImage = engine.getGraphics().newImage(file);
            images.put(file, newImage);
            return newImage;
        }
    }

    /**
     * Crea una fuente
     *
     * @param file     Archivo de la fuente
     * @param size     Tamaño de fuente
     * @param isBold
     * @param isItalic
     * @return Fuente creada
     */
    public Font createFont(String file, int size, boolean isBold, boolean isItalic) {
        if (fonts.containsKey(file)) {
            return fonts.get(file);
        } else {
            Font newFont = engine.getGraphics().newFont(file, size, isBold, isItalic);
            fonts.put(file, newFont);
            return newFont;
        }
    }

    /**
     * @param id
     * @return Imagen con ese id
     */
    public Image getImage(String id) {
        if (images.containsKey(id))
            return images.get(id);

        return null;
    }

    /**
     * @param id
     * @return Fuente con ese id
     */
    public Font getFont(String id) {
        if (fonts.containsKey(id))
            return fonts.get(id);

        return null;
    }

    /**
     * Borra la imagen con ese id
     *
     * @param id
     * @return Si se ha podido borrar
     */
    public boolean deleteImage(String id) {
        if (images.containsKey(id)) {
            images.remove(id);
            return true;
        }
        return false;
    }

    public void assetsChargeFinalized(){
        //Cambiar luego
        GameManager.getInstance().setN_skins_Background(n_Images);
    }

    /**
     * Borra la fuente con ese id
     *
     * @param id
     * @return Si se ha podido borrar
     */
    public boolean deleteFont(String id) {
        if (fonts.containsKey(id)) {
            fonts.remove(id);
            return true;
        }
        return false;
    }

    public int getN_Images() {return n_Images;}

    void loadLevels()
    {
        // Cargar numero de mundos
        String baseRoute = "levels";
        List<String> files = engine.obtainFolderFiles(baseRoute);

        // Cargar niveles por mundo
        for(String world : files)
        {
            levels.add(engine.obtainFolderFiles(world));
        }
    }

    public String getLevel(int worldIndex, int levelIndex)
    {
        return levels.get(worldIndex).get(levelIndex);
    }
    public String getWorldSytle(int worldIndex)
    {
        return levels.get(worldIndex).get(levels.get(worldIndex).size()-1);
    }

    public int getNumWorlds(){return levels.size();}
    public int getNumLevels(int worldIndex){return levels.get(worldIndex).size() - 1;} // el ultimo archivo es el style.json

    public void loadShop()
    {
        // Cargar todos los estilos de los fondos
        // Creamos el parser del json
        Gson gson  = new Gson();
        BufferedReader br = null; // -> esto igual se puede sacar al resource manager o hacer algo en el motor ?

        loadShopBackgrounds(gson, br);
        loadShopCodes(gson, br);

    }

    private void loadShopBackgrounds(Gson gson, BufferedReader br)
    {
        // Leemos el json
        try {
            br = engine.openAssetFile("shop/backgrounds.json");
        }
        catch (IOException ex)
        {
            System.out.println("Error loading shop backgrounds");
        }

        // Deserializamos el json en un objeto con la info del mundo
        BackgroundInfo[] mieldaloco = gson.fromJson(br, BackgroundInfo[].class);
        for(int i = 0; i < mieldaloco.length; i++)
        {
            System.out.println(mieldaloco[i].getThumbnail() + " " + mieldaloco[i].getBackground());
        }
    }

    private void loadShopCodes(Gson gson, BufferedReader br)
    {
        // Leemos el json
        try {
            br = engine.openAssetFile("shop/codes.json");
        }
        catch (IOException ex)
        {
            System.out.println("Error loading shop codes");
        }
        // Deserializamos el json en un objeto con la info de los packs
        CodeInfo[] packList = gson.fromJson(br, CodeInfo[].class);

        // Por cada pack
        for(int i = 0; i < packList.length; i++)
        {
            System.out.println(packList[i].getThumbnail() + " " + packList[i].getCode());

            // Abrir la carpeta del pack y leer todos los archivos
            String baseRoute = "sprites/" + packList[i].getCode();
            List<String> files = engine.obtainFolderFiles(baseRoute);

            // Guardamos la info de los packs
            shop_codes.add(new Pair<String, String>(packList[i].getThumbnail(), packList[i].getCode()));

            // Cargamos la miniatura del pack
            // createImage(packList[i].getThumbnail()); <<-- esto habra que descomentarlo cuando tengamos los pngs

            // Cargar imagenes del pack
            for(int j = 0; j < files.size(); j++)
            {
                String route = packList[i].getCode() + "/" + packList[i].getCode() + "_" + (j+1) + ".png";
                createImage(route);
            }
        }
    }
}
