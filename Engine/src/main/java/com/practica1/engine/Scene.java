package com.practica1.engine;

import java.util.ArrayList;

/**
 * Clase que representa una escena del juego
 */
public interface Scene {

    /**
     * Inicializar
     *
     * @param engine Engine de la aplicacion
     */
    public void init(Engine engine);

    /**
     * Update de la escena
     *
     * @param deltaTime
     */
    public void update(double deltaTime);

    /**
     * Renderizado de la escena
     */
    public void render();

    /**
     * Manejo de input de la escena
     *
     * @param events
     */
    public void handleInput(ArrayList<TouchEvent> events);
}
