package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.practica1.engine.Image;

import java.io.IOException;
import java.io.InputStream;

/**
 * Clase que envuelve una imagen en la aplicacion en android
 */
public class ImageAndroid implements Image {

    /**
     * @param filename     Nombre del archivo
     * @param assetManager AssetsManager en android
     * @throws IOException
     */
    public ImageAndroid(String filename, AssetManager assetManager) throws IOException {
        InputStream is = assetManager.open(filename);
        bitmap = BitmapFactory.decodeStream(is);
    }

    private Bitmap bitmap;

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    /**
     * @return La imagen envuelta
     */
    public Bitmap getImage() {
        return bitmap;
    }
}
