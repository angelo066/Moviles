package com.practica1.androidgame;

import android.util.Pair;

import com.google.gson.Gson;
import com.practica1.androidengine.Color;
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
    private Engine engine;
    private HashMap<String, Image> images;
    private HashMap<String, Font> fonts;
    private List<List<String>> levels;
    private List<Pair<String, String>> shopBackgrounds; // estos no son de tipo BackgroundInfo porque tenemos que poner mas cosas en la ruta del string // igual lo podemos cambiar tambien
    private List<Pair<String, String>> shopCodes;
    private List<Palette> shopPalettes;
    private Palette defaultPalette;

    private ResourceManager() {
        images = new HashMap<>();
        fonts = new HashMap<>();
        levels = new ArrayList<>();
        shopCodes = new ArrayList<>();
        shopBackgrounds = new ArrayList<>();
        shopPalettes = new ArrayList<>();
        defaultPalette = new Palette("", Color.WHITE.getValue(), Color.DARK_GREY.getValue(), Color.BLACK.getValue(), Color.GREY.getValue());

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
        Instance.shopBackgrounds.clear();
        Instance.shopCodes.clear();
        Instance.shopPalettes.clear();
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

    /**
     * Carga los mundos
     */
    void loadLevels() {
        // Cargar numero de mundos
        String baseRoute = "levels";
        List<String> files = engine.obtainFolderFiles(baseRoute);

        // Cargar niveles por mundo
        for (String world : files) {
            levels.add(engine.obtainFolderFiles(world));
        }
    }

    /**
     * @param worldIndex Numero de mundo
     * @param levelIndex Numero de nivel
     * @return El nivel
     */
    public String getLevel(int worldIndex, int levelIndex) {
        return levels.get(worldIndex).get(levelIndex);
    }

    /**
     * @param worldIndex Numero de mundo
     * @return Estilo del mundo
     */
    public String getWorldSytle(int worldIndex) {
        return levels.get(worldIndex).get(levels.get(worldIndex).size() - 1);
    }

    /**
     * @return Numero de mundos
     */
    public int getNumWorlds() {
        return levels.size();
    }

    /**
     * @param worldIndex Numero de mundo
     * @return Numero de niveles del mundo
     */
    public int getNumLevels(int worldIndex) {
        return levels.get(worldIndex).size() - 1;
    } // el ultimo archivo es el style.json

    /**
     * Carga los assets de la tienda
     */
    public void loadShop() {
        Gson gson = new Gson();
        BufferedReader br = null;

        loadShopBackgrounds(gson, br);
        loadShopCodes(gson, br);
        loadShopColors(gson, br);
    }

    /**
     * Carga los fondos de la tienda
     *
     * @param gson Gson
     * @param br   BufferedReader
     */
    private void loadShopBackgrounds(Gson gson, BufferedReader br) {
        try {
            br = engine.openAssetFile("shop/backgrounds.json");

            // Deserializamos el json en una lista de fondos
            BackgroundInfo[] backgroundList = gson.fromJson(br, BackgroundInfo[].class);
            String backBaseRoute = "backgrounds/";
            String thumbBaseRoute = "thumbnails/";
            for (int i = 0; i < backgroundList.length; i++) {
                shopBackgrounds.add(new Pair<String, String>(thumbBaseRoute + backgroundList[i].getThumbnail(), backBaseRoute + backgroundList[i].getBackground()));
            }

        } catch (IOException ex) {
            System.out.println("Error loading shop backgrounds");
        }


    }


    /**
     * Carga los codigos de la tienda
     *
     * @param gson Gson
     * @param br   BufferedReader
     */
    private void loadShopCodes(Gson gson, BufferedReader br) {
        try {
            br = engine.openAssetFile("shop/codes.json");

            // Deserializamos el json en un objeto con la info de los packs
            CodeInfo[] packList = gson.fromJson(br, CodeInfo[].class);

            // Por cada pack
            for (int i = 0; i < packList.length; i++) {
                // Guardamos la info de los packs
                String thumbBaseRoute = "thumbnails/";
                shopCodes.add(new Pair<String, String>(thumbBaseRoute + packList[i].getThumbnail(), packList[i].getCode()));
            }

        } catch (IOException ex) {
            System.out.println("Error loading shop codes");
        }

    }

    /**
     * Carga las paletas de la tienda
     *
     * @param gson Gson
     * @param br   BufferedReader
     */
    public void loadShopColors(Gson gson, BufferedReader br) {
        try {
            br = engine.openAssetFile("shop/colors.json");

            // Deserializamos el json en un objeto con la info de los packs
            PaletteInfo[] paletteInfos = gson.fromJson(br, PaletteInfo[].class);
            for (int i = 0; i < paletteInfos.length; i++) {
                PaletteInfo p = paletteInfos[i];
                long cb = Long.parseLong(p.getColorBackground(), 16);
                long c1 = Long.parseLong(p.getColor1(), 16);
                long c2 = Long.parseLong(p.getColor2(), 16);
                long c3 = Long.parseLong(p.getColor3(), 16);
                Palette palette = new Palette(p.getThumbnail(), (int) cb, (int) c1, (int) c2, (int) c3);
                shopPalettes.add(palette);
            }

        } catch (IOException ex) {
            System.out.println("Error loading shop colors");
        }

    }

    /**
     * Recorre la carpeta de sprites/backgrounds y crea todas las imagenes de fondos posibles
     */
    public void loadBackgroundImages() {
        // Cargar carpeta de fondos
        String baseRoute = "sprites/backgrounds";
        List<String> files = engine.obtainFolderFiles(baseRoute);

        // Crear la imagen recurso
        for (String background : files) {
            String filename = background.replace("sprites/", "");
            createImage(filename);
        }
    }

    /**
     * Recorre la carpeta de sprites/thumbnails y crea todas las imagenes de miniaturas posibles
     */
    public void loadThumbnailsImages() {
        // Cargar carpeta de fondos
        String baseRoute = "sprites/thumbnails";
        List<String> files = engine.obtainFolderFiles(baseRoute);

        // Crear la imagen recurso
        for (String background : files) {
            String filename = background.replace("sprites/", "");
            createImage(filename);
        }
    }

    /**
     * Recorre la carpeta de sprites/codes y crea todas los codigos posibles
     */
    public void loadPackCodes() {
        // Cargar carpeta de packs
        String baseRoute = "sprites/packs";
        List<String> files = engine.obtainFolderFiles(baseRoute);

        // Crear la imagen recurso
        for (String packfiles : files) {
            List<String> images = engine.obtainFolderFiles(packfiles);
            for (String image : images) {
                String filename = image.replace("sprites/", "");
                createImage(filename);
            }
        }
    }

    /**
     * @return Paleta por defecto
     */
    public Palette getDefaultPalette() {
        return defaultPalette;
    }

    /**
     * @param i Indice del background
     * @return Background con ese indice
     */
    public Pair<String, String> getShopBackground(int i) {
        return getNumShopBackgrounds() > i ? shopBackgrounds.get(i) : null;
    }

    /**
     * @return Numero de backgrounds de la tienda
     */
    public int getNumShopBackgrounds() {
        return shopBackgrounds.size();
    }

    /**
     * @param i Indice del code
     * @return Code con ese indice
     */
    public Pair<String, String> getShopCode(int i) {
        return getNumShopCodes() > i ? shopCodes.get(i) : null;
    }

    /**
     * @return Numero de codes de la tienda
     */
    public int getNumShopCodes() {
        return shopCodes.size();
    }

    /**
     * @param i Indice de la paleta
     * @return Paleta con ese indice
     */
    public Palette getShopPalette(int i) {
        return getNumShopPalettes() > i ? shopPalettes.get(i) : null;
    }

    /**
     * @return Numero de palettes de la tienda
     */
    public int getNumShopPalettes() {
        return shopPalettes.size();
    }

}
