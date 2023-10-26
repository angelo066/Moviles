package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.practica1.engine.Image;

import java.io.IOException;
import java.io.InputStream;

public class ImageAndroid implements Image {
    public ImageAndroid(String filename, AssetManager assetManager) throws IOException
    {
        InputStream is = assetManager.open(filename);
        bitmap = BitmapFactory.decodeStream(is);
    }
    Bitmap bitmap;
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    public Bitmap getImage(){return bitmap;};


}
