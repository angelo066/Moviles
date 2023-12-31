package com.practica1.androidengine;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import androidx.work.ListenableWorker.Result;

/**
 * Clase que representa una notificacion push
 */
public class PushNotification {
    private final String CHANNEL_ID = "MasterMind";
    private final CharSequence name = "MasterMind";
    private final String description = "MasterMind Game";
    private Context context;

    /**
     * @param context Contexto de la aplicacion
     */
    public PushNotification(Context context) {
        this.context = context;

        createChannel();
    }

    /**
     * Crea un canal de notificaciones
     */
    private void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Crea el intento ejectutar al pinchar la notificacion
     *
     * @return Intento de la notificacion
     */
    private PendingIntent createIntent() {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    /**
     * Crea la notificacion
     *
     * @param icon         Icono de la notificacion
     * @param title        Titulo de la notificacion
     * @param content      Contenido de la notificacion
     * @param extraContent Contenido extra de la notificacion
     * @return Notificacion creada
     */
    private NotificationCompat.Builder createNotification(int icon, String title, String content, String extraContent) {
        PendingIntent pendingIntent = createIntent();

        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(extraContent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    }

    /**
     * Manda la notificacion
     *
     * @param icon         Icono de la notificacion
     * @param title        Titulo de la notificacion
     * @param content      Contenido de la notificacion
     * @param extraContent Contenido extra de la notificacion
     * @return Resultado de mandar la aplicacion (Success/Failure)
     */
    public Result sendNotification(int icon, String title, String content, String extraContent) {

        NotificationCompat.Builder builder = createNotification(icon, title, content, extraContent);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, builder.build());
            return Result.success();
        }
        return Result.failure();
    }

}
