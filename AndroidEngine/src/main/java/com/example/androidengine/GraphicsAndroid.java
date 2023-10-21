package com.example.androidengine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine.Font;
import com.example.engine.Image;
import com.example.engine.Graphics;
import com.example.engine.Scene;

public class GraphicsAndroid implements Graphics {

    private SurfaceView renderView;

    private SurfaceHolder holder;
    private Paint paint;
    private Canvas canvas;

    private int sceneWidth;
    private int sceneHeight;

    GraphicsAndroid(SurfaceView view) {
        renderView = view;
        holder = view.getHolder();
        paint = new Paint();
        paint.setColor(0x000000);
    }

    @Override
    public Image newImage(String name) {
        return null;
    }

    @Override
    public Font newFont(String filename, int size, boolean isBold) {
        return null;
    }

    @Override
    public void clear(int color) {
        canvas.drawColor(color);
    }

    @Override
    public void translate(float x, float y) {
        canvas.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        canvas.scale(x, y);
    }

    @Override
    public void save() {
        canvas.save();
    }

    @Override
    public void restore() {
        canvas.restore();
    }

    @Override
    public void drawImage(Image image) {

    }

    @Override
    public void fillRectangle(float cx, float cy, float width, float height) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(cx, cy, width, height, paint);
    }

    @Override
    public void fillRoundRectangle(float cx, float cy, float width, float height, float arc) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(cx, cy, width, height, arc, arc, paint);

    }

    @Override
    public void drawRectangle(float cx, float cy, float width, float height) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(cx, cy, width, height, paint);
    }

    @Override
    public void drawRoundRectangle(float cx, float cy, float width, float height, float arc) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(cx, cy, width, height, arc, arc, paint);
    }

    @Override
    public void drawLine(float initX, float InitY, float endX, float endY) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(initX, InitY, endX, endY, paint);
    }

    @Override
    public void drawCircle(float cx, float cy, float radius) {
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(cx, cy, radius, this.paint);
    }

    @Override
    public void fillCircle(float cx, float cy, float radius) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(cx, cy, radius, this.paint);
    }

    @Override
    public void drawText(String text, float x, float y) {

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
    public void setColor(int color) {
        paint.setColor(color);
    }

    public void prepareRender() {
        while (!holder.getSurface().isValid()) ;
        canvas = this.holder.lockCanvas();

        restore();

        float scaleX = (float) renderView.getWidth() / sceneWidth;
        float scaleY = (float) renderView.getHeight() / sceneHeight;
        float scale = Math.min(scaleX, scaleY);

        float translateX = (renderView.getWidth() - sceneWidth * scale) / 2f;
        float translateY = (renderView.getHeight() - sceneHeight * scale) / 2f;

        translate(translateX, translateY);
        scale(scale, scale);

    }

    public void releaseRender() {
        holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void setSceneSize(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;
    }
}
