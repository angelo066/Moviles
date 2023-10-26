package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.practica1.engine.Font;

public class FontAndroid implements Font {
    private Typeface font;

    private int size = 50;
    public FontAndroid(String filename, AssetManager assetManager, int size, boolean bold, boolean italic)
    {
        Typeface newFont = Typeface.createFromAsset(assetManager, filename);
        font = newFont;
    }

    public Typeface getFont(){return font;}
    public void setSize(int f){size = f;}
    public int getSize(){return size;}
}
