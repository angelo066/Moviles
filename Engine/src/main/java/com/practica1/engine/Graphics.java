package com.practica1.engine;

/**
 * Clase para el manejo de los gráficos de la aplicacion
 */
public interface Graphics {

    /**
     * Crea un nuevo objeto Image
     *
     * @param name Nombre del archivo
     * @return La nueva imagen
     */
    Image newImage(String name);

    /**
     * Dibuja una imagen en el canvas
     *
     * @param image Imagen a dibujar
     * @param x     Posicion en eje x
     * @param y     Posicion en eje y
     * @param w     Anchura
     * @param h     Altura
     */
    void drawImage(Image image, int x, int y, int w, int h);

    /**
     * Crea un nuevo objeto Font
     *
     * @param name     Nombre del archivo
     * @param size     Tamanio de la fuente
     * @param isBold   Bold?
     * @param isItalic Italic?
     * @return La nueva fuente
     */
    Font newFont(String name, int size, boolean isBold, boolean isItalic);


    /**
     * Establece la fuente a usar
     *
     * @param font Fuente a usar
     */
    void setFont(Font font);

    /**
     * Dibuja un texto en eol canvas
     *
     * @param text Texto a dibujar
     * @param x    Posicion en eje x
     * @param y    Posicion en eje y
     */
    void drawText(String text, float x, float y);

    /**
     * Establece el color con el que se pinta el canvas
     *
     * @param color
     */
    void setColor(Color color);

    /**
     * Limpia el canvas y lo pinta de un color
     *
     * @param color Color para el canvas
     */
    void clear(Color color);

    /**
     * Translada el canvas
     *
     * @param x Translacion en eje x
     * @param y Translacion en eje y
     */
    void translate(float x, float y);

    /**
     * Escala el canvas
     *
     * @param x Escalado en eje x
     * @param y Escalado en eje y
     */
    void scale(float x, float y);

    /**
     * Guarda la actual transformacion del canvas
     */
    void save();

    /**
     * Restaura la ultima transformacion del canvas guardada mediante "save"
     */
    void restore();

    /**
     * Pinta y rellena un rectangulo
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param width  Anchura
     * @param height Altura
     */
    void fillRectangle(float cx, float cy, float width, float height);

    /**
     * Pinta y rellena un rectangulo con esquinas redondeados
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param width  Anchura
     * @param height Altura
     * @param arc    Redondeo de las esquinas
     */
    void fillRoundRectangle(float cx, float cy, float width, float height, float arc);

    /**
     * Pinta un rectangulo
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param width  Anchura
     * @param height Altura
     */
    void drawRectangle(float cx, float cy, float width, float height);

    /**
     * Pinta un rectangulo con esquinas redondeados
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param width  Anchura
     * @param height Altura
     * @param arc    Redondeo de las esquinas
     */
    void drawRoundRectangle(float cx, float cy, float width, float height, float arc);

    /**
     * Pinta una linea
     *
     * @param initX Posicion inicial en el eje x
     * @param InitY Posicion inicial en el eje y
     * @param endX  Posicion final en el eje x
     * @param endY  Posicion final en el eje y
     */
    void drawLine(float initX, float InitY, float endX, float endY);

    /**
     * Pinta un circulo.
     * Se pinta -> inicioX = cx, inicioY = cy, finX = cx + 2*radius, finY = cY + 2*radius
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param radius Radio del circulo
     */
    void drawCircle(float cx, float cy, float radius);

    /**
     * Pinta y rellena un circulo.
     * Se pinta -> inicioX = cx, inicioY = cy, finX = cx + 2*radius, finY = cY + 2*radius
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param radius Radio del circulo
     */
    void fillCircle(float cx, float cy, float radius);

    /**
     * @return Anchura de la ventana
     */
    int getWidth();

    /**
     * @return Altura de la ventana
     */
    int getHeight();

    /**
     * Establece el tamanio de la escena.
     * Hay que llamar a este metodo desde el init de las escenas.
     * El tamanio de dicha escena se establece en la misma
     *
     * @param width  Anchura de la escena
     * @param height Altura de la escena
     */
    void setSceneSize(int width, int height);

    /**
     * @param font  Fuente
     * @param texto Texto
     * @return Anchura del canvas que ocupa el texto con la fuente actual
     */
    float getFontMetricWidth(Font font, String texto);

    /**
     * @param font Fuente
     * @return Altura del canvas que ocupa el texto con la fuente actual
     */
    float getFontMetricHeight(Font font);

    /**
     * @return Escalado del canvas en el eje x
     */
    float getScaleX();

    /**
     * @return Escalado del canvas en el eje y
     */
    float getScaleY();

    /**
     * @return Translacion del canvas en el eje x
     */
    float getTranslateX();

    /**
     * @return Translacion del canvas en el eje y
     */
    float getTranslateY();


}
