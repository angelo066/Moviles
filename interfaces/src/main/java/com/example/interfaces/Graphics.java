package com.example.interfaces;

import java.awt.Color;

public interface Graphics {
    Image newImage(String name);

    Font newFont(String filename, int size, boolean isBold);

    void clear(int color);

    void translate(float x, float y);
    void scale(float x, float y);
    void save();
    void restore();

    void drawImage(Image image); //void drawImage(Image image, ...)

    void setColor (Color color);

    void fillRectangle(float cx, float cy, float width, float height);
    void fillRoundRectangle(float cx, float cy, float width, float height, float arc);
    void drawRectangle(float cx, float cy, float width, float height);
    void drawRoundRectangle(float cx, float cy, float width, float height, float arc);
    void drawLine(float initX, float InitY, float endX, float endY);
    void drawCircle(float cx, float cy, float radius);
    void fillCircle(float cx, float cy, float radius);
    void drawText(String text, float x, float y);

    int getWidth();
    int getHeight();

}
