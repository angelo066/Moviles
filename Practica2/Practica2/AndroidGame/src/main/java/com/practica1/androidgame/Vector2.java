package com.practica1.androidgame;

import java.io.Serializable;

/**
 * Clase que representa una posicion en el espacio
 */
public class Vector2  implements Serializable {
    public int x = 0;
    public int y = 0;

    /**
     * Por defecto
     */
    public Vector2() {
        x = 0;
        y = 0;
    }

    /**
     * @param _x Posicion en el eje x
     * @param _y Posicion en el eje y
     */
    public Vector2(int _x, int _y) {
        x = _x;
        y = _y;
    }

    /**
     * Constructor por copia
     *
     * @param other Otro vector
     */
    public Vector2(Vector2 other) {
        x = other.x;
        y = other.y;
    }

    /**
     * Suma dos vectores
     *
     * @param other Otro vector2
     * @return Vector con el resultado
     */
    public Vector2 sum(Vector2 other) {
        return new Vector2(x + other.x, y + other.y);
    }

    /**
     * Multiplica dos vectores
     *
     * @param other Otro vector2
     * @return Vector multiplicacion
     */
    public Vector2 mul_vec(Vector2 other) {
        return new Vector2(x * other.x, y * other.y);
    }

    /**
     * Multiplica un vector por el escalar dado
     *
     * @param scalar Numero entero por el que multiplicar
     * @return Vector con el resultado
     */
    public Vector2 mul_scalar(int scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    /**
     * @return Magnitud del vector
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Normaliza el vector
     */
    public void normalize() {
        double magnitude = Math.sqrt(x * x + y * y);

        if (magnitude != 0) {
            x /= magnitude;
            y /= magnitude;
        }
    }
}
