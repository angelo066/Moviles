package com.practica1.engine;

import java.io.IOException;

public interface Graphics {
    Image newImage(String name) throws IOException;

    Font newFont(String filename, int size, boolean isBold);

    void clear(int color);

    void translate(float x, float y);
    void scale(float x, float y);
    void save();
    void restore();

    void drawImage(Image image, int x, int y, int w, int h); //void drawImage(Image image, ...)

    void setColor (int color);

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

    void setSceneSize(int width,int height);

    int getSceneWidth();

    int getSceneHeight();


}
