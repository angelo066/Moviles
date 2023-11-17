package com.practica1.gamelogic;

/**
 * Clase que contiene la informacion relativa a un intento (una fila del tablero)
 */
public class Attempt {
    public CircleObject[] combination = null;    // Lista de circulos
    public int checksPos = 0;    // Numero de aciertos de posicion
    public int checksColor = 0;    // Numero de aciertos de color
    public CheckObject checks = null;    // Objeto que indica los aciertos del intento
    public TextObject textNumber;    // Indicador del intento
    public int coloredCircles = 0;    // Numero de circulos coloreados

}
