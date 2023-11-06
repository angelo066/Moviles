package com.practica1.gamelogic;

/**
 * Clase que contiene la informacion relativa a un intento (una fila del tablero)
 */
public class Attempt
{
    CircleObject[] combination = null;    // Lista de circulos
    int checks_pos = 0;    // Numero de aciertos de posicion
    int checks_color = 0;    // Numero de aciertos de color
    CheckObject checks = null;    // Objeto que indica los aciertos del intento
    Texto textNumber;    // Indicador del intento
    int coloredCircles = 0;    // Numero de circulos coloreados

}
