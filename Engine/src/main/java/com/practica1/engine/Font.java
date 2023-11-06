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

    /**
     * Establece el bold del texto
     *
     * @param bold
     */
    public void setBold(boolean bold);

    /**
     * @return Si la fuente es bold
     */
    public boolean isBold();

    /**
     * Establece el italic del texto
     *
     * @param italic
     */
    public void setItalic(boolean italic);

    /**
     * @return Si la fuente es italic
     */
    public boolean isItalic();
}
