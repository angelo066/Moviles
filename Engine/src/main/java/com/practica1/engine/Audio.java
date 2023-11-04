package com.practica1.engine;

/**
 * Clase que se encarga de gestionar el audio de la apliacion
 */
public interface Audio {

    /**
     * Carga un sonido y lo guarda en el hashmap de la clase
     *
     * @param file Nombre del archivo
     * @param id   Id asociado para el hashmap
     */
    void loadSound(String file, String id);

    /**
     * Reproduce un sonido guardado en el hashmap de la clase
     *
     * @param id   Id asociado al sonido en el hashmap
     * @param loop
     */
    void playSound(String id, boolean loop);

    /**
     * Para la reproduccion de un sonido guardado en el hashmap de la clase
     *
     * @param id Id asociado al sonido en el hashmap
     */
    void stopSound(String id);

    /**
     * Libera un sonido guardado en el hashmap de la clase
     *
     * @param id Id asociado al sonido en el hashmap
     */
    void releaseSound(String id);
}
