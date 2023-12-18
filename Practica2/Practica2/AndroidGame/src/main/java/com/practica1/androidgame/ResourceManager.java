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
    protected List<Pair<String, String>> shop_backgrounds; // estos no son de tipo BackgroundInfo porque tenemos que poner mas cosas en la ruta del string // igual lo podemos cambiar tambien
    protected List<Pair<String, String>> shop_codes;
    protected List<PaletteInfo> shop_palettes;

    //PROVISIONAL
    int n_Images;

    private ResourceManager() {
        images = new HashMap<>();
        fonts = new HashMap<>();
        levels = new ArrayList<>();
        shop_codes = new ArrayList<>();
        shop_backgrounds = new ArrayList<>();
        shop_palettes = new ArrayList<>();
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
        Instance.shop_backgrounds.clear();
        Instance.shop_codes.clear();
        Instance.shop_palettes.clear();
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
        BufferedReader br = null;

        loadShopBackgrounds(gson, br);
        loadShopCodes(gson, br);
        loadShopColors(gson, br);
        int a = 0;
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

        // Deserializamos el json en una lista de fondos
        BackgroundInfo[] backgroundList = gson.fromJson(br, BackgroundInfo[].class);
        String backBaseRoute = "backgrounds/";
        String thumbBaseRoute = "thumbnails/";
        for(int i = 0; i < backgroundList.length; i++)
        {
            shop_backgrounds.add(new Pair<String, String>(thumbBaseRoute + backgroundList[i].getThumbnail(), backBaseRoute + backgroundList[i].getBackground()));
            System.out.println(backgroundList[i].getThumbnail() + " " + backgroundList[i].getBackground());
        }
        int a = 0;
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
            String thumbBaseRoute = "thumbnails/";
            shop_codes.add(new Pair<String, String>(thumbBaseRoute + packList[i].getThumbnail(), packList[i].getCode()));
        }
    }

    public void loadShopColors(Gson gson, BufferedReader br)
    {
        // Leemos el json
        try {
            br = engine.openAssetFile("shop/colors.json");
        }
        catch (IOException ex)
        {
            System.out.println("Error loading shop colors");
        }
        // Deserializamos el json en un objeto con la info de los packs
        PaletteInfo[] paletteInfos = gson.fromJson(br, PaletteInfo[].class);
        for(int i = 0; i< paletteInfos.length; i++)
        {
            shop_palettes.add(paletteInfos[i]);
        }
        int a = 0;
    }

    /**
     * Recorre la carpeta de sprites/backgrounds y crea todas las imagenes de fondos posibles
     */
    public void loadBackgroundImages()
    {
        // Cargar carpeta de fondos
        String baseRoute = "sprites/backgrounds";
        List<String> files = engine.obtainFolderFiles(baseRoute);

        // Crear la imagen recurso
        for(String background : files)
        {
            String filename = background.replace("sprites/", "");
            createImage(filename);
        }
    }

    /**
     * Recorre la carpeta de sprites/thumbnails y crea todas las imagenes de miniaturas posibles
     */
    public void loadThumbnailsImages()
    {
        // Cargar carpeta de fondos
        String baseRoute = "sprites/thumbnails";
        List<String> files = engine.obtainFolderFiles(baseRoute);

        // Crear la imagen recurso
        for(String background : files)
        {
            String filename = background.replace("sprites/", "");
            createImage(filename);
        }
    }

    public void loadPackCodes()
    {
        // Cargar carpeta de fondos
        String baseRoute = "sprites/packs";
        List<String> files = engine.obtainFolderFiles(baseRoute);

        // Crear la imagen recurso
        for(String packfiles : files)
        {
            List<String> images = engine.obtainFolderFiles(packfiles);
            for(String image : images)
            {
                String filename = image.replace("sprites/", "");
                createImage(filename);
            }
        }
    }

}
