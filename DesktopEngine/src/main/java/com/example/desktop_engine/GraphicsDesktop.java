package com.example.desktop_engine;

import com.example.engine.Color;
import com.example.engine.Font;
import com.example.engine.Graphics;
import com.example.engine.Image;

public class GraphicsDesktop implements Graphics {
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

    }

    @Override
    public void fillRectangle(float cx, float cy, float width, float height) {

    }

    @Override
    public void fillRoundRectangle(float cx, float cy, float width, float height, float arc) {

    }

    @Override
    public void drawRectangle(float cx, float cy, float width, float height) {

    }

    @Override
    public void drawRoundRectangle(float cx, float cy, float width, float height, float arc) {

    }

    @Override
    public void drawLine(float initX, float InitY, float endX, float endY) {

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