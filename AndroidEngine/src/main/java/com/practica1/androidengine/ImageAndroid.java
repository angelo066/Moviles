package com.practica1.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.practica1.engine.Image;

import java.io.InputStream;

/**
 * Clase que envuelve una imagen en la aplicacion en android
 */
public class ImageAndroid implements Image {

    private Bitmap bitmap;

    /**
     * @param filename     Nombre del archivo
     * @param assetManager AssetsManager en android
     */
    public ImageAndroid(String filename, AssetManager assetManager) {
        try {
            InputStream is = assetManager.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            throw new RuntimeException("ERROR AL CARGAR IMAGEN POR ANDROID");
        }

    }

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
