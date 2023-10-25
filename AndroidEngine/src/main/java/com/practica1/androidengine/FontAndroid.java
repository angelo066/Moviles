package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.practica1.engine.Font;

public class FontAndroid implements Font {
    private Typeface font;

    public FontAndroid(String filename, AssetManager assetManager, int size, boolean bold, boolean italic)
    {
        Typeface newFont = Typeface.createFromAsset(assetManager, filename);
        font = newFont;
    }

    public Typeface getFont(){return font;};
}
