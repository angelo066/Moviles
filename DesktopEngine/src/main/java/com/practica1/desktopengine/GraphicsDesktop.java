package com.practica1.desktopengine;

import com.practica1.engine.Color;
import com.practica1.engine.Font;
import com.practica1.engine.Graphics;
import com.practica1.engine.Image;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;

/**
 * Clase para el manejo de los gráficos de la aplicacion en desktop
 */
public class GraphicsDesktop implements Graphics {

    private JFrame g_frame;
    private Graphics2D g_graphics;

    AffineTransform frameTransform;
    private int sceneWidth;
    private int sceneHeight;
    private String imagesRoute = "assets/sprites/";
    private String fontsRoute = "assets/fonts/";

    private float scaleX = 1, scaleY = 1, translateX = 0, translateY = 0;


    /**
     * @param frame Ventana de la aplicacion
     */
    public GraphicsDesktop(JFrame frame) {
        g_frame = frame;
        g_graphics = (Graphics2D) g_frame.getBufferStrategy().getDrawGraphics();
    }

    @Override
    public Image newImage(String name) {
        String filename = imagesRoute + name;
        try {
            return new ImageDesktop(filename);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void drawImage(Image image, int x, int y, int w, int h) {
        ImageDesktop dImage = (ImageDesktop) image;
        // ------------------------------------ dst ------------------------ scr
        g_graphics.drawImage(dImage.getImage(), x, y, x + w, y + h, 0, 0, dImage.getWidth(), dImage.getHeight(), null);
    }

    @Override
    public Font newFont(String name, int size, boolean isBold, boolean isItalic) {
        String filename = fontsRoute + name;
        return new FontDesktop(filename, size, isBold, isItalic);
    }

    @Override
    public void setFont(Font font) {
        FontDesktop dFont = (FontDesktop) font;
        g_graphics.setFont(dFont.getFont());
    }

    @Override
    public void drawText(String text, float x, float y) {
        g_graphics.drawString(text, x, y);
    }

    @Override
    public void setColor(Color color) {
        java.awt.Color newColor = new java.awt.Color(color.getValue());
        g_graphics.setColor(newColor);
    }

    @Override
    public void clear(Color color) {
        AffineTransform transform = g_graphics.getTransform();
        g_graphics.setTransform(frameTransform);
        setColor(color);
        fillRectangle(0, 0, g_frame.getWidth(), g_frame.getHeight());
        g_graphics.setTransform(transform);
    }

    @Override
    public void translate(float x, float y) {
        g_graphics.translate((int) x, (int) y);
        translateX = x;
        translateY = y;
    }

    @Override
    public void scale(float x, float y) {
        g_graphics.scale(x, y);
        scaleX = x;
        scaleY = y;
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
        g_graphics.drawOval((int) cx, (int) cy, (int) (2 * radius), (int) (2 * radius));
    }

    @Override
    public void fillCircle(float cx, float cy, float radius) {
        g_graphics.fillOval((int) cx, (int) cy, (int) (2 * radius), (int) (2 * radius));
    }

    @Override
    public int getWidth() {
        return g_frame.getWidth();
    }

    @Override
    public int getHeight() {
        return g_frame.getHeight();
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
    public float getFontMetricWidth(Font font, String text) {
        FontDesktop dFont = (FontDesktop) font;
        g_graphics.setFont(dFont.getFont());
        FontMetrics fm = g_graphics.getFontMetrics();
        return fm.stringWidth(text);
    }

    @Override
    public float getFontMetricHeight(Font font) {
        FontDesktop dFont = (FontDesktop) font;
        FontMetrics fm = g_graphics.getFontMetrics(dFont.getFont());
        int s = fm.getAscent() + fm.getDescent();
        return fm.getHeight();
    }

    /**
     * Prepara todo para el renderizado
     */
    public void prepareRender() {
        g_graphics = (Graphics2D) g_frame.getBufferStrategy().getDrawGraphics();

        save();

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

    /**
     * Libera todo lo necesario despues del renderizado
     */
    public void releaseRender() {
        restore();
        g_graphics.dispose();
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
