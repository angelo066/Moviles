package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Clase que envuelve una imagen en la aplicacion en android
 */
public class Image {

    private Bitmap bitmap;

    /**
     * @param filename     Nombre del archivo
     * @param assetManager AssetsManager en android
     */
    public Image(String filename, AssetManager assetManager) {
        try {
            InputStream is = assetManager.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            throw new RuntimeException("ERROR AL CARGAR IMAGEN POR ANDROID");
        }

    }

    public int getWidth() {
        return bitmap.getWidth();
    }

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
