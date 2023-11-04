package com.practica1.engine;

public enum Color {
    NO_COLOR(0xFF333333),
    ROJO(0xFFFF0000),
    NARANJA(0xFFFF8000),
    AMARILLO(0xFFFFFF00),
    VERDE(0xFF00FF00),
    CYAN(0xFF00FFFF),
    AZUL(0XFF0000FF),
    MORADO(0xFFFF00FF),
    ROSA(0xFFFF0080),
    MARRON(0xFF804000),
    BLANCO(0xFFFFFFFF),
    GRIS(0xFFC6C6C6),
    GRIS_OSCURO(0xFF8C8C8C),
    NEGRO(0xFF000000);

    private int value;

    private Color(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }
}
