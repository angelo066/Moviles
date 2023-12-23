package com.practica1.androidgame;

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

public class Screenshot {
    public Screenshot(SurfaceView surfaceview, Context context, int x, int y, int w, int h, String shareTitle, String extraMessage, String descriptionTitle, String description) {
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
