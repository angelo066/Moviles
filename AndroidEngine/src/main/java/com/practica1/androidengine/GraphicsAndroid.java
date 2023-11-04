package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.practica1.engine.Font;
import com.practica1.engine.Image;
import com.practica1.engine.Graphics;

import java.io.IOException;

/**
 * Clase para el manejo de los gráficos de la aplicacion en android
 */
public class GraphicsAndroid implements Graphics {

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
    GraphicsAndroid(SurfaceView view) {
        renderView = view;
        holder = view.getHolder();
        paint = new Paint();
        paint.setColor(0xFF000000);
        assetManager = this.renderView.getContext().getAssets();
    }

    @Override
    public Image newImage(String name) {
        String filename = imagesRoute + name;
        ImageAndroid image = null;
        try {
            image = new ImageAndroid(filename, assetManager);
        } catch (IOException e) {
            throw new RuntimeException("ERROR AL CARGAR IMAGEN POR ANDROID");
        }
        return image;
    }

    @Override
    public void drawImage(Image image, int x, int y, int w, int h) {
        ImageAndroid aImage = (ImageAndroid) image;

        //Rectangulo src y dest
        Rect src = new Rect(0, 0, aImage.getWidth(), aImage.getHeight());
        Rect dst = new Rect(x, y, x + w, y + h);
        canvas.drawBitmap(aImage.getImage(), src, dst, paint);
    }


    @Override
    public Font newFont(String name, int size, boolean isBold, boolean isItalic) {
        String filename = fontsRoute + name;
        return new FontAndroid(filename, assetManager, size, isBold, isItalic);
    }

    @Override
    public void setFont(Font font) {
        FontAndroid aFont = (FontAndroid) font;
        paint.setTextSize(font.getSize());
        paint.setTypeface(aFont.getFont());
    }

    @Override
    public void drawText(String text, float x, float y) {
        canvas.drawText(text, x, y, paint);
    }


    @Override
    public void setColor(int color) {
        paint.setColor(color);
    }

    @Override
    public void clear(int color) {
        canvas.drawColor(color);
    }


    @Override
    public void translate(float x, float y) {
        canvas.translate(x, y);
        translateX = x;
        translateY = y;
    }

    @Override
    public void scale(float x, float y) {
        canvas.scale(x, y);
        scaleX = x;
        scaleY = y;
    }

    @Override
    public void save() {
        canvas.save();
    }

    @Override
    public void restore() {
        canvas.restore();
        scaleX = 1;
        scaleY = 1;
        translateY = 0;
        translateX = 0;
    }


    @Override
    public void fillRectangle(float cx, float cy, float width, float height) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(cx, cy, cx + width, cy + height, paint);
    }

    @Override
    public void fillRoundRectangle(float cx, float cy, float width, float height, float arc) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(cx, cy, cx + width, cy + height, arc, arc, paint);

    }

    @Override
    public void drawRectangle(float cx, float cy, float width, float height) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(cx, cy, cx + width, cy + height, paint);
    }

    @Override
    public void drawRoundRectangle(float cx, float cy, float width, float height, float arc) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(cx, cy, cx + width, cy + height, arc, arc, paint);
    }

    @Override
    public void drawLine(float initX, float InitY, float endX, float endY) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(initX, InitY, endX, endY, paint);
    }

    @Override
    public void drawCircle(float cx, float cy, float radius) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx + radius, cy - radius, radius, this.paint);
    }

    @Override
    public void fillCircle(float cx, float cy, float radius) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx + radius, cy + radius, radius, this.paint);
    }


    @Override
    public int getWidth() {
        return renderView.getWidth();
    }

    @Override
    public int getHeight() {
        return renderView.getHeight();
    }


    @Override
    public void setSceneSize(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;
    }

    @Override
    public int getSceneWidth() {
        return sceneWidth;
    }

    @Override
    public int getSceneHeight() {
        return sceneHeight;
    }

    @Override
    public float getFontMetricWidth(Font font, String texto) {
        paint.setTextSize(font.getSize());
        return ((int) paint.measureText(texto));
    }

    @Override
    public float getFontMetricHeight(Font font) {
        Paint.FontMetrics metrics = paint.getFontMetrics();
        return metrics.bottom - metrics.top;
    }


    /**
     * Prepara todo para el renderizado
     */
    public void prepareRender() {
        while (!holder.getSurface().isValid()) ;
        canvas = this.holder.lockCanvas();

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
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public float getScaleX() {
        return scaleX;
    }

    @Override
    public float getScaleY() {
        return scaleY;
    }

    @Override
    public float getTranslateX() {
        return translateX;
    }

    @Override
    public float getTranslateY() {
        return translateY;
    }

}
