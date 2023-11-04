package com.practica1.engine;

import java.util.List;

/**
 * Clase que se encarga de gestionar el input de la apliacion
 */
public interface Input {

    /**
     * @return Devuelve los eventos ocurridos en el ultimo frame de la aplicacion
     */
    public List<TouchEvent> getTouchEvents();
}
