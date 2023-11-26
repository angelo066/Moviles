package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class ResourceManager {
    private static ResourceManager Instance;

    protected Engine engine;
    protected HashMap<String,Image> images;
    protected HashMap<String,Font> fonts;

    private ResourceManager(){
        images = new HashMap<>();
        fonts = new HashMap<>();
    }
    public static void Init(Engine engine){
        if(Instance == null) {
            Instance = new ResourceManager();
            Instance.engine = engine;
        }
    }

    public static void release(){
        Instance.images.clear();
        Instance.fonts.clear();
        Instance.engine = null;
    }
    public static ResourceManager getInstance(){
        return Instance;
    }
    public Image createImage(String file){
        if(images.containsKey(file)) {
            return images.get(file);
        }
        else{
            Image newImage = engine.getGraphics().newImage(file);
            images.put(file,newImage);
            return newImage;
        }
    }

    public Font createFont(String file, int size, boolean isBold, boolean isItalic){
        if(fonts.containsKey(file)){
            return fonts.get(file);
        }
        else{
            Font newFont = engine.getGraphics().newFont(file,size,isBold,isItalic);
            fonts.put(file,newFont);
            return newFont;
        }
    }

    public Image getImage(String id){
        if(images.containsKey(id))
            return images.get(id);

        return null;
    }

    public Font getFont(String id){
        if(fonts.containsKey(id))
            return fonts.get(id);

        return null;
    }

    public boolean deleteImage(String id){
        if(images.containsKey(id)){
            images.remove(id);
            return true;
        }
        return false;
    }

    public boolean deleteFont(String id){
        if(fonts.containsKey(id)){
            fonts.remove(id);
            return true;
        }
        return false;
    }

}
