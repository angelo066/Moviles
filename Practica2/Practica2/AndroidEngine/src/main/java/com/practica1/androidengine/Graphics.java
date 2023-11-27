package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Clase para el manejo de los gráficos de la aplicacion en android
 */
public class Graphics {

    private SurfaceView renderView;

    private SurfaceHolder holder;
    private Paint paint;
    private Canvas canvas;

    private int sceneWidth;
    private int sceneHeight;

    private AssetManager assetManager;
    private String imagesRoute = "sprites/";
    private String fontsRoute = "fonts/";

    private float scaleX = 1, scaleY = 1, translateX = 0, translateY = 0;


    /**
     * @param view Ventana de la aplicacion
     */
    Graphics(SurfaceView view) {
        renderView = view;
        holder = view.getHolder();
        paint = new Paint();
        paint.setColor(0xFF000000);
        assetManager = this.renderView.getContext().getAssets();
    }

    /**
     * Crea un nuevo objeto Image
     *
     * @param name Nombre del archivo
     * @return La nueva imagen
     */
    public Image newImage(String name) {
        String filename = imagesRoute + name;
        try {
            return new Image(filename, assetManager);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Dibuja una imagen en el canvas
     *
     * @param image Imagen a dibujar
     * @param x     Posicion en eje x
     * @param y     Posicion en eje y
     * @param w     Anchura
     * @param h     Altura
     */
    public void drawImage(Image image, int x, int y, int w, int h) {
        Image aImage = (Image) image;

        //Rectangulo src y dest
        Rect src = new Rect(0, 0, aImage.getWidth(), aImage.getHeight());
        Rect dst = new Rect(x, y, x + w, y + h);
        canvas.drawBitmap(aImage.getImage(), src, dst, paint);
    }

    /**
     * Crea un nuevo objeto Font
     *
     * @param name     Nombre del archivo
     * @param size     Tamanio de la fuente
     * @param isBold   Bold?
     * @param isItalic Italic?
     * @return La nueva fuente
     */
    public Font newFont(String name, int size, boolean isBold, boolean isItalic) {
        String filename = fontsRoute + name;
        try {
            return new Font(filename, assetManager, size, isBold, isItalic);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Establece la fuente a usar
     *
     * @param font Fuente a usar
     */
    public void setFont(Font font) {
        Font aFont = (Font) font;
        paint.setTextSize(font.getSize());
        paint.setTypeface(aFont.getFont());
    }

    /**
     * Dibuja un texto en eol canvas
     *
     * @param text Texto a dibujar
     * @param x    Posicion en eje x
     * @param y    Posicion en eje y
     */
    public void drawText(String text, float x, float y) {
        paint.setStyle(Paint.Style.FILL);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        canvas.drawText(text, x, y + (metrics.bottom - metrics.top), paint);
    }

    /**
     * Establece el color con el que se pinta el canvas
     *
     * @param color
     */
    public void setColor(int color) {
        paint.setColor(color);
    }

    /**
     * Limpia el canvas y lo pinta de un color
     *
     * @param color Color para el canvas
     */
    public void clear(int color) {
        canvas.drawColor(color);
    }

    /**
     * Translada el canvas
     *
     * @param x Translacion en eje x
     * @param y Translacion en eje y
     */
    public void translate(float x, float y) {
        canvas.translate(x, y);
        translateX = x;
        translateY = y;
    }

    /**
     * Escala el canvas
     *
     * @param x Escalado en eje x
     * @param y Escalado en eje y
     */
    public void scale(float x, float y) {
        canvas.scale(x, y);
        scaleX = x;
        scaleY = y;
    }

    /**
     * Guarda la actual transformacion del canvas
     */
    public void save() {
        canvas.save();
    }

    /**
     * Restaura la ultima transformacion del canvas guardada mediante "save"
     */
    public void restore() {
        canvas.restore();
    }

    /**
     * Pinta y rellena un rectangulo
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param width  Anchura
     * @param height Altura
     */
    public void fillRectangle(float cx, float cy, float width, float height) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(cx, cy, cx + width, cy + height, paint);
    }

    /**
     * Pinta y rellena un rectangulo con esquinas redondeados
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param width  Anchura
     * @param height Altura
     * @param arc    Redondeo de las esquinas
     */
    public void fillRoundRectangle(float cx, float cy, float width, float height, float arc) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(cx, cy, cx + width, cy + height, arc, arc, paint);

    }

    /**
     * Pinta un rectangulo
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param width  Anchura
     * @param height Altura
     */
    public void drawRectangle(float cx, float cy, float width, float height) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(cx, cy, cx + width, cy + height, paint);
    }

    /**
     * Pinta un rectangulo con esquinas redondeados
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param width  Anchura
     * @param height Altura
     * @param arc    Redondeo de las esquinas
     */
    public void drawRoundRectangle(float cx, float cy, float width, float height, float arc) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(cx, cy, cx + width, cy + height, arc, arc, paint);
    }

    /**
     * Pinta una linea
     *
     * @param initX Posicion inicial en el eje x
     * @param InitY Posicion inicial en el eje y
     * @param endX  Posicion final en el eje x
     * @param endY  Posicion final en el eje y
     */
    public void drawLine(float initX, float InitY, float endX, float endY) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(initX, InitY, endX, endY, paint);
    }

    /**
     * Pinta un circulo.
     * Se pinta -> inicioX = cx, inicioY = cy, finX = cx + 2*radius, finY = cY + 2*radius
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param radius Radio del circulo
     */
    public void drawCircle(float cx, float cy, float radius) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx + radius, cy + radius, radius, this.paint);
    }

    /**
     * Pinta y rellena un circulo.
     * Se pinta -> inicioX = cx, inicioY = cy, finX = cx + 2*radius, finY = cY + 2*radius
     *
     * @param cx     Posicion en el eje x
     * @param cy     Posicion en el eje y
     * @param radius Radio del circulo
     */
    public void fillCircle(float cx, float cy, float radius) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx + radius, cy + radius, radius, this.paint);
    }

    /**
     * @return Anchura de la ventana
     */
    public int getWidth() {
        return renderView.getWidth();
    }

    /**
     * @return Altura de la ventana
     */
    public int getHeight() {
        return renderView.getHeight();
    }

    /**
     * Establece el tamanio de la escena.
     * Hay que llamar a este metodo desde el init de las escenas.
     * El tamanio de dicha escena se establece en la misma
     *
     * @param width  Anchura de la escena
     * @param height Altura de la escena
     */
    public void setSceneSize(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;
    }

    /**
     * @param font  Fuente
     * @param texto Texto
     * @return Anchura del canvas que ocupa el texto con la fuente actual
     */
    public float getFontMetricWidth(Font font, String texto) {
        setFont(font);
        return paint.measureText(texto);
    }

    /**
     * @param font Fuente
     * @return Altura del canvas que ocupa el texto con la fuente actual
     */
    public float getFontMetricHeight(Font font) {
        setFont(font);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        return metrics.bottom - metrics.top;
    }


    /**
     * Prepara todo para el renderizado
     */
    public void prepareRender() {
        while (!holder.getSurface().isValid()) ;
        canvas = this.holder.lockCanvas();

        save();

        float scaleX = (float) renderView.getWidth() / sceneWidth;
        float scaleY = (float) renderView.getHeight() / sceneHeight;
        float scale = Math.min(scaleX, scaleY);

        float translateX = (renderView.getWidth() - sceneWidth * scale) / 2f;
        float translateY = (renderView.getHeight() - sceneHeight * scale) / 2f;

        translate(translateX, translateY);
        scale(scale, scale);
    }

    /**
     * Libera todo lo necesario despues del renderizado
     */
    public void releaseRender() {
        restore();
        holder.unlockCanvasAndPost(canvas);
    }

    /**
     * @return Escalado del canvas en el eje x
     */
    public float getScaleX() {
        return scaleX;
    }

    /**
     * @return Escalado del canvas en el eje y
     */
    public float getScaleY() {
        return scaleY;
    }

    /**
     * @return Translacion del canvas en el eje x
     */
    public float getTranslateX() {
        return translateX;
    }

    /**
     * @return Translacion del canvas en el eje y
     */
    public float getTranslateY() {
        return translateY;
    }

}
