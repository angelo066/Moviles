package com.practica1.androidengine;

/**
 * Clase que representa una entidad de juego
 */
public class GameObject {
    /**
     * @param eng         Objeto engine de la aplicacion
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     */
    public GameObject(Engine eng, int sceneWidth, int sceneHeight) {
        engine = eng;
        pos = new Vector2(0, 0);
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    /**
     * @param eng         Objeto engine de la aplicacion
     * @param pos         Posicion de la entidad
     * @param sceneWidth  Anchura de la escena
     * @param sceneHeight Altura de la escena
     */
    public GameObject(Engine eng, int sceneWidth, int sceneHeight, Vector2 pos) {
        engine = eng;
        this.pos = pos;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
    }

    public Engine engine = null;
    public Vector2 pos;
    protected int sceneWidth, sceneHeight;

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

    /**
     * Cambia la posicion en x de la entidad
     *
     * @param x
     */
    public void setPosX(int x) {
        pos.x = x;
    }


    /**
     * Cambia la posicion en y de la entidad
     *
     * @param y
     */
    public void setPosY(int y) {
        pos.y = y;
    }
}
