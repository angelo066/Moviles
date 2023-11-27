package com.practica1.androidengine;

/**
 * Enum que envuelve colores en forma de enteros (en hexadecimal)
 */
public enum Color {
    RED(0xFFFF0000, 0),
    ORANGE(0xFFFF8000, 1),
    YELLOW(0xFFFFFF00, 2),
    GREEN(0xFF00FF00, 3),
    CYAN(0xFF00FFFF, 4),
    BLUE(0XFF0000FF, 5),
    PURPLE(0xFFFF00FF, 6),
    PINK(0xFFFF0080, 7),
    BROWN(0xFF804000, 8),
    WHITE(0xFFFFFFFF, 9),
    LIGHT_GRAY(0xFF333333, 10),
    GREY(0xFFC6C6C6, 11),
    DARK_GREY(0xFF8C8C8C, 12),

    BLACK(0xFF000000, 13),
    NO_COLOR(0xFF000000, 14);

    private int value;
    private int id;

    private Color(int v, int id) {
        this.value = v;
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public int getId() {
        return id;
    }
}
