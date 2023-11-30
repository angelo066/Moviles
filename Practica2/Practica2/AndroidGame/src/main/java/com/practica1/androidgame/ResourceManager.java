package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.Image;

import java.util.HashMap;

/**
 * Gestor de recursos
 */
public class ResourceManager {
    private static ResourceManager Instance;
    protected Engine engine;
    protected HashMap<String, Image> images;
    protected HashMap<String, Font> fonts;

    private ResourceManager() {
        images = new HashMap<>();
        fonts = new HashMap<>();
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

}
