package com.practica1.engine;

/**
 * Clase que envuelve un sonido en la aplicacion
 */
public interface Sound {

    /**
     * Reproduce el sonido
     */
    void play(boolean loop);

    /**
     * Para el sonido
     */
    void stop();

    /**
     * Establece el loop del sonido a True/False
     */
    void loop(boolean loop);

    /**
     * Libera los recursos del sonido
     */
    void release();
}
