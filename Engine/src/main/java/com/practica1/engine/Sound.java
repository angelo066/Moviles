package com.practica1.engine;

/**
 * Clase que envuelve un sonido en la aplicacion
 */
public interface Sound {

    /**
     * Reproduce el sonido
     */
    void play();

    /**
     * Para el sonido
     */
    void stop();

    /**
     * Establece el loop del sonido a verdadero
     */
    void loop();

    /**
     * Libera los recursos del sonido
     */
    void release();
}
