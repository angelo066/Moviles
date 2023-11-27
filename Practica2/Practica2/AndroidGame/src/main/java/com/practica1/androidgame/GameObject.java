package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.TouchEvent;

/**
 * Clase que representa una entidad de juego
 */
public class GameObject {

    public Engine engine;
    protected Vector2 iniPos;
    public Vector2 pos;
    protected int sceneWidth, sceneHeight;

    /**
     * @param eng         Objeto engine de la aplicacion
     * @param pos         Posicion de la entidad
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     */
    public GameObject(Engine eng, int sceneWidth, int sceneHeight, Vector2 pos) {
        engine = eng;
        this.iniPos = new Vector2(pos);
        this.pos = pos;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    /**
     * Metodo para inicializar la entidad
     */
    public void init() {
    }


    /**
     * Update de la entidad
     *
     * @param deltaTime
     */
    public void update(double deltaTime) {
    }

    /**
     * Render de la entidad
     */
    public void render() {
    }

    /**
     * Manejo de input de la entidad
     *
     * @return
     */
    public boolean handleInput(TouchEvent event) {
        return false;
    }

}
