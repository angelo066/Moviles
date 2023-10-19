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
        setColor(color);
        fillRectangle(0, 0, getWidth(), getHeight());
    }

    @Override
    public void translate(float x, float y) {
        g_graphics.translate(x, y);
    }

    @Override
    public void scale(float x, float y) {
        g_graphics.scale(x, y);
    }

    @Override
    public void save() {
        frameTransform = (AffineTransform) g_graphics.getTransform().clone();
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

    public void updateGraphics(){
        g_graphics = (Graphics2D) g_frame.getBufferStrategy().getDrawGraphics();
    }
}
