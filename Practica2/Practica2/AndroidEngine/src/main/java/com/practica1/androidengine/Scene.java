package com.practica1.androidengine;

import java.util.ArrayList;

/**
 * Clase que representa una escena del juego
 */
public class Scene {

    protected int width = 1080;
    protected int height = 1920;

    protected Engine engine;

    protected Graphics graphics;
    protected Audio audio;
    private boolean initialized = false;

    /**
     * Inicializar
     *
     * @param engine Engine de la aplicacion
     */
    public void init(Engine engine) {
        this.engine = engine;
        this.graphics = engine.getGraphics();
        this.audio = engine.getAudio();
        engine.getGraphics().setSceneSize(width, height);
        initialized = true;
    }

    /**
     * Update de la escena
     *
     * @param deltaTime
     */
    public void update(double deltaTime) {

    }

    /**
     * Renderizado de la escena
     */
    public void render() {

    }

    /**
     * Manejo de input de la escena
     *
     * @param events
     */
    public void handleInput(ArrayList<TouchEvent> events) {

    }

    public boolean isInitialized() {
        return initialized;
    }

}
