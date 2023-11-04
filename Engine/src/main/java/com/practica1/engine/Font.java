package com.practica1.engine;

/**
 * Clase que envuelve una fuente en la aplicacion
 */
public interface Font {

    /**
     * Establece el nuevo tamanio de fuente
     *
     * @param newSize
     */
    public void setSize(int newSize);

    /**
     * @return El tamanio de fuente
     */
    public int getSize();
}
