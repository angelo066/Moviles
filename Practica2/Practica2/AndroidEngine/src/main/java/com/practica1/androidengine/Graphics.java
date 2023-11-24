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

    public Image newImage(String name) {
        String filename = imagesRoute + name;
        try {
            return new Image(filename, assetManager);
        } catch (Exception e) {
            return null;
        }
    }

    public void drawImage(Image image, int x, int y, int w, int h) {
        Image aImage = (Image) image;

        //Rectangulo src y dest
        Rect src = new Rect(0, 0, aImage.getWidth(), aImage.getHeight());
        Rect dst = new Rect(x, y, x + w, y + h);
        canvas.drawBitmap(aImage.getImage(), src, dst, paint);
    }


    public Font newFont(String name, int size, boolean isBold, boolean isItalic) {
        String filename = fontsRoute + name;
        try {
            return new Font(filename, assetManager, size, isBold, isItalic);
        } catch (Exception e) {
            return null;
        }
    }

    public void setFont(Font font) {
        Font aFont = (Font) font;
        paint.setTextSize(font.getSize());
        paint.setTypeface(aFont.getFont());
    }

    public void drawText(String text, float x, float y) {
        paint.setStyle(Paint.Style.FILL);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        canvas.drawText(text, x, y + (metrics.bottom - metrics.top), paint);
    }


    public void setColor(int color) {
        paint.setColor(color);
    }

    public void clear(int color) {
        canvas.drawColor(color);
    }

    public void translate(float x, float y) {
        canvas.translate(x, y);
        translateX = x;
        translateY = y;
    }

    public void scale(float x, float y) {
        canvas.scale(x, y);
        scaleX = x;
        scaleY = y;
    }

    public void save() {
        canvas.save();
    }

    public void restore() {
        canvas.restore();
    }

    public void fillRectangle(float cx, float cy, float width, float height) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(cx, cy, cx + width, cy + height, paint);
    }

    public void fillRoundRectangle(float cx, float cy, float width, float height, float arc) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(cx, cy, cx + width, cy + height, arc, arc, paint);

    }

    public void drawRectangle(float cx, float cy, float width, float height) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(cx, cy, cx + width, cy + height, paint);
    }

    public void drawRoundRectangle(float cx, float cy, float width, float height, float arc) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(cx, cy, cx + width, cy + height, arc, arc, paint);
    }

    public void drawLine(float initX, float InitY, float endX, float endY) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(initX, InitY, endX, endY, paint);
    }

    public void drawCircle(float cx, float cy, float radius) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx + radius, cy + radius, radius, this.paint);
    }

    public void fillCircle(float cx, float cy, float radius) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx + radius, cy + radius, radius, this.paint);
    }


    public int getWidth() {
        return renderView.getWidth();
    }

    public int getHeight() {
        return renderView.getHeight();
    }


    public void setSceneSize(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;
    }

    public float getFontMetricWidth(Font font, String texto) {
        setFont(font);
        return paint.measureText(texto);
    }

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

    public float getScaleX() {
        return scaleX;
    }

    public float getScaleY() {
        return scaleY;
    }

    public float getTranslateX() {
        return translateX;
    }

    public float getTranslateY() {
        return translateY;
    }

}
