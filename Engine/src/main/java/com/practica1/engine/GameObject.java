package com.practica1.engine;

/**
 * Clase que representa una entidad de juego
 */
public class GameObject {
    /**
     * @param eng Objeto engine de la aplicacion
     */
    public GameObject(Engine eng) {
        engine = eng;
        pos = new Vector2(0, 0);
    }

    /**
     * @param eng Objeto engine de la aplicacion
     * @param pos Posicion de la entidad
     */
    public GameObject(Engine eng, Vector2 pos) {
        engine = eng;
        this.pos = pos;
    }

    public Engine engine = null;
    public Vector2 pos;

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
