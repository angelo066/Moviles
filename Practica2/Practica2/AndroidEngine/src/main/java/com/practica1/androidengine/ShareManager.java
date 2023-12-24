package com.practica1.androidengine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.view.PixelCopy;
import android.view.SurfaceView;

/**
 * Clase para compartir una captura del juego a traves de distintas apps (intento)
 */
public class ShareManager {

    private SurfaceView surfaceview;
    private Context context;

    /**
     * @param surfaceview SurfaceView de la aplicacion
     * @param context     Contexto de la aplicacion
     */
    public ShareManager(SurfaceView surfaceview, Context context) {
        this.surfaceview = surfaceview;
        this.context = context;
    }

    /**
     * Compartir
     *
     * @param x                Posicion en x donde empieza la captura
     * @param y                Posicion en y donde empieza la captura
     * @param w                Anchura de la captura
     * @param h                Altura de la captura
     * @param shareTitle       Titulo del intento
     * @param extraMessage     Texto adicional
     * @param descriptionTitle Titulo de la descripcion de la captura
     * @param description      Descripcion de la captura
     */
    public void share(int x, int y, int w, int h, String shareTitle, String extraMessage, String descriptionTitle, String description) {
        Bitmap bitmap = Bitmap.createBitmap(surfaceview.getWidth(), surfaceview.getHeight(),
                Bitmap.Config.ARGB_8888);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            HandlerThread handlerThread = new HandlerThread("PixelCopier");
            handlerThread.start();
            PixelCopy.request(surfaceview, bitmap, new PixelCopy.OnPixelCopyFinishedListener() {
                @Override
                public void onPixelCopyFinished(int copyResult) {
                    if (copyResult == PixelCopy.SUCCESS) {
                        Bitmap screenBitmap = Bitmap.createBitmap(bitmap, x, y, w, h);
                        createImage(context, screenBitmap, shareTitle, extraMessage, descriptionTitle, description);
                    }
                    handlerThread.quitSafely();
                }
            }, new Handler(handlerThread.getLooper()));
        }
    }

    /**
     * Crear imagen, intento y enviarlo
     *
     * @param shareTitle       Titulo del intento
     * @param extraMessage     Texto adicional
     * @param descriptionTitle Titulo de la descripcion de la captura
     * @param description      Descripcion de la captura
     * @param context          Contexto de la aplicacion
     * @param screenBitmap     Bitmap a convertir en imagen
     */
    private void createImage(Context context, Bitmap screenBitmap, String shareTitle, String extraMessage, String descriptionTitle, String description) {
        String imageUri = MediaStore.Images.Media.insertImage(context.getContentResolver(), screenBitmap, descriptionTitle, description);
        Uri uri = Uri.parse(imageUri);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_TEXT, extraMessage);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(shareIntent, shareTitle));
    }
}
