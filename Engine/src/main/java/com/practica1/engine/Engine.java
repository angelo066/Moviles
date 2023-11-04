package com.practica1.engine;

/**
 * Clase motor de la aplicacion
 */
public interface Engine {
    /**
     * @return Objeto Graphics de la aplicacion
     */
    Graphics getGraphics();

    /**
     * @return Objeto Input de la aplicacion
     */
    Input getInput();

    /**
     * @return Objeto Audio de la aplicacion
     */
    Audio getAudio();

    /**
     * Reanuda la ejecucion de la aplicacion
     */
    void resume();

    /**
     * Pausa la ejecucion de la aplicacion
     */
    void pause();

    /**
     * Establece la escena de la aplicacion
     *
     * @param scene Escena a establecer
     */
    void setScene(Scene scene);

}
