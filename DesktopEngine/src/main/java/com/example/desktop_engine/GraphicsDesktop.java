package com.example.desktop_engine;

import com.example.engine.Color;
import com.example.engine.Font;
import com.example.engine.Graphics;
import com.example.engine.Image;
import com.example.desktop_engine.ColorDesktop;

import java.awt.Graphics2D;

import javax.swing.JFrame;

public class GraphicsDesktop implements Graphics {

    private JFrame g_frame;
    private Graphics2D g_graphics;

    public GraphicsDesktop(JFrame frame){
        g_frame = frame;
        g_graphics = (Graphics2D) frame.getBufferStrategy().getDrawGraphics();
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
        ColorDesktop c  =new ColorDesktop();
        c.c = color;
        setColor(c);
        fillRectangle(0,0,g_frame.getWidth(),g_frame.getHeight());
    }

    @Override
    public void translate(float x, float y) {

    }

    @Override
    public void scale(float x, float y) {

    }

    @Override
    public void save() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void drawImage(Image image) {

    }

    @Override
    public void setColor(Color color) {
        java.awt.Color color2 = new java.awt.Color(((ColorDesktop)color).c);
        ColorDesktop colorAux = (ColorDesktop)color;
        int color_Convertido = colorAux.c;
        g_graphics.setColor(new java.awt.Color(color_Convertido));
    }

    @Override
    public void fillRectangle(float cx, float cy, float width, float height) {
        g_graphics.fillRect((int)cx,(int)cy,(int)width,(int)height);
    }

    @Override
    public void fillRoundRectangle(float cx, float cy, float width, float height, float arc) {
        g_frame.getGraphics().fillRoundRect((int)cx,(int)cy,(int)width,(int)height,(int)arc,(int)arc);
    }

    @Override
    public void drawRectangle(float cx, float cy, float width, float height) {
        g_frame.getGraphics().drawRect((int)cx,(int)cy,(int)width,(int)height);
    }

    @Override
    public void drawRoundRectangle(float cx, float cy, float width, float height, float arc) {
        g_frame.getGraphics().drawRoundRect((int)cx,(int)cy,(int)width,(int)height,(int)arc,(int)arc);
    }

    @Override
    public void drawLine(float initX, float InitY, float endX, float endY) {
        g_frame.getGraphics().drawLine((int)initX,(int)InitY,(int)endX,(int)endY);
    }

    @Override
    public void drawCircle(float cx, float cy, float radius) {

    }

    @Override
    public void fillCircle(float cx, float cy, float radius) {

    }

    @Override
    public void drawText(String text, float x, float y) {

    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }
}
