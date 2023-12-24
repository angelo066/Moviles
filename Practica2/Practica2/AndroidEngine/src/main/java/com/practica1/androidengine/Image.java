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
    private String file;

    /**
     * @param filename     Nombre del archivo
     * @param assetManager AssetsManager en android
     */
    public Image(String filename, AssetManager assetManager) {
        try {
            file = filename;
            InputStream is = assetManager.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            throw new RuntimeException("ERROR AL CARGAR IMAGEN POR ANDROID");
        }

    }

    /**
     * @return Anchura de la imagen
     */
    public int getWidth() {
        return bitmap.getWidth();
    }

    /**
     * @return Altura de la imagen
     */
    public int getHeight() {
        return bitmap.getHeight();
    }

    /**
     * @return La imagen envuelta
     */
    public Bitmap getImage() {
        return bitmap;
    }

    /**
     * @return Ruta de la imagen
     */
    public String getFile() {
        return file;
    }
}
