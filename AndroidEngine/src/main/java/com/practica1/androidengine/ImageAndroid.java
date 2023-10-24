package com.practica1.androidengine;

import android.graphics.Bitmap;

import com.practica1.engine.Image;

public class ImageAndroid implements Image {
    public ImageAndroid(Bitmap b){
        bitmap = b;
    }
    Bitmap bitmap;
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    public Bitmap getImage(){return bitmap;};
}
