package com.example.desktop_engine;

import com.example.engine.Font;
import com.example.engine.Graphics;
import com.example.engine.Image;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;

public class GraphicsDesktop implements Graphics {

    private JFrame g_frame;
    private Graphics2D g_graphics;

    AffineTransform frameTransform;
    private int sceneWidth;
    private int sceneHeight;

    public GraphicsDesktop(JFrame frame) {
        g_frame = frame;
        g_graphics = (Graphics2D) g_frame.getBufferStrategy().getDrawGraphics();
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
        AffineTransform transform = g_graphics.getTransform();
        restore();
        setColor(color);
        fillRectangle(0, 0, g_frame.getWidth(), g_frame.getHeight());
        g_graphics.setTransform(transform);
    }

    @Override
    public void translate(float x, float y) {
        g_graphics.translate((int) x, (int) y);
    }

    @Override
    public void scale(float x, float y) {
        g_graphics.scale(x, y);
    }

    @Override
    public void save() {
        frameTransform = g_graphics.getTransform();
    }

    @Override
    public void restore() {
        g_graphics.setTransform(frameTransform);
    }

    @Override
    public void drawImage(Image image) {

    }

    @Override
    public void setColor(int color) {
        java.awt.Color newColor = new java.awt.Color(color);
        g_graphics.setColor(newColor);
    }

    @Override
    public void fillRectangle(float cx, float cy, float width, float height) {
        g_graphics.fillRect((int) cx, (int) cy, (int) width, (int) height);
    }

    @Override
    public void fillRoundRectangle(float cx, float cy, float width, float height, float arc) {
        g_graphics.fillRoundRect((int) cx, (int) cy, (int) width, (int) height, (int) arc, (int) arc);
    }

    @Override
    public void drawRectangle(float cx, float cy, float width, float height) {
        g_graphics.drawRect((int) cx, (int) cy, (int) width, (int) height);
    }

    @Override
    public void drawRoundRectangle(float cx, float cy, float width, float height, float arc) {
        g_graphics.drawRoundRect((int) cx, (int) cy, (int) width, (int) height, (int) arc, (int) arc);
    }

    @Override
    public void drawLine(float initX, float InitY, float endX, float endY) {
        g_graphics.drawLine((int) initX, (int) InitY, (int) endX, (int) endY);
    }

    @Override
    public void drawCircle(float cx, float cy, float radius) {
        g_graphics.drawOval((int) cx, (int) cy, (int) radius, (int) radius);
    }

    @Override
    public void fillCircle(float cx, float cy, float radius) {
        g_graphics.fillOval((int) cx, (int) cy, (int) radius, (int) radius);
    }

    @Override
    public void drawText(String text, float x, float y) {

    }

    @Override
    public int getWidth() {
        return g_frame.getWidth();
    }

    @Override
    public int getHeight() {
        return g_frame.getHeight();
    }

    public void prepareRender() {
        g_graphics = (Graphics2D) g_frame.getBufferStrategy().getDrawGraphics();

        restore();

        int width = g_frame.getWidth() - g_frame.getInsets().left - g_frame.getInsets().right;
        int height = g_frame.getHeight() - g_frame.getInsets().top - g_frame.getInsets().bottom;

        float scaleX = (float) width / sceneWidth;
        float scaleY = (float) height / sceneHeight;
        float scale = Math.min(scaleX, scaleY);

        float translateX = ((g_frame.getWidth() - g_frame.getInsets().right + g_frame.getInsets().left) - sceneWidth * scale) / 2f;
        float translateY = ((g_frame.getHeight() - g_frame.getInsets().bottom + g_frame.getInsets().top) - sceneHeight * scale) / 2f;

        translate(translateX, translateY);
        scale(scale, scale);

    }

    public void releaseRender() {
        g_graphics.dispose();
    }

    @Override
    public void setSceneSize(int width, int height) {
        sceneWidth = width;
        sceneHeight = height;
    }

}
