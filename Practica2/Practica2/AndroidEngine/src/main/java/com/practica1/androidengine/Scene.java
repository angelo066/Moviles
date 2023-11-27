package com.practica1.androidengine;

import java.util.ArrayList;

/**
 * Clase que representa una escena del juego
 */
public class Scene {

    protected int width = 1080;
    protected int height = 1920;

    protected Engine engine;

    /**
     * Inicializar
     *
     * @param engine Engine de la aplicacion
     */
    public void init(Engine engine){
        this.engine = engine;
        engine.getGraphics().setSceneSize(width, height);
    }

    /**
     * Update de la escena
     *
     * @param deltaTime
     */
    public void update(double deltaTime){

    }

    /**
     * Renderizado de la escena
     */
    public void render(){

    }

    /**
     * Manejo de input de la escena
     *
     * @param events
     */
    public void handleInput(ArrayList<TouchEvent> events){

    }

    /**
     * @return Anchura de la escena
     */
    public int getWidth(){
        return width;
    }

    /**
     * @return Altura de la escena
     */
    public int getHeight(){
        return height;
    }

    /**
     * @return Engine de la escena
     */
    public Engine getEngine(){
        return engine;
    }

}
