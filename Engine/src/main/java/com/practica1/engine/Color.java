package com.practica1.engine;

/**
 * Enum que envuelve colores en forma de enteros (en hexadecimal)
 */
public enum Color {
    NO_COLOR(0xFF333333, 0),
    ROJO(0xFFFF0000, 1),
    NARANJA(0xFFFF8000, 2),
    AMARILLO(0xFFFFFF00, 3),
    VERDE(0xFF00FF00, 4),
    CYAN(0xFF00FFFF, 5),
    AZUL(0XFF0000FF, 6),
    MORADO(0xFFFF00FF, 7),
    ROSA(0xFFFF0080, 8),
    MARRON(0xFF804000, 9),
    BLANCO(0xFFFFFFFF, 10),
    GRIS(0xFFC6C6C6, 11),
    GRIS_OSCURO(0xFF8C8C8C, 12),
    NEGRO(0xFF000000, 13);

    private int value;
    private int id;

    private Color(int v, int id) {
        this.value = v;
        this.id = id;
    }

    public int getValue() {
        return value;
    }
    public int getId(){return id;};
}
