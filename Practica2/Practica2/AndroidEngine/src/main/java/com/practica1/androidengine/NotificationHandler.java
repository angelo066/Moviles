package com.practica1.androidengine;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

/**
 * Clase para manejar las notificaciones
 */
public class NotificationHandler {
    private PushNotification pushNotification;
    private Context context;

    /**
     * @param context Contexto de la aplicacion
     */
    public NotificationHandler(Context context) {
        this.pushNotification = new PushNotification(context);
        this.context = context;

        //Si la aplicacion no tiene permisos de notificaciones, se le pide al jugador que las active
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 0);

    }

    /**
     * Establece un worker para enviar una notificacion despues de initialDelay
     *
     * @param initialDelay Retraso inicial
     * @param unit         Unidad de retraso inicial
     * @param icon         Icono de la notificacion
     * @param title        Titulo de la notificacion
     * @param content      Contenido de la notificacion
     * @param extraContent Contenido extra de la notificacion
     */
    public void setPushNotificationWorker(int initialDelay, TimeUnit unit, int icon, String title, String content, String extraContent) {

        WorkRequest request = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(initialDelay, unit)
                .setInputData(new Data.Builder()
                        .putString("title", title)
                        .putString("content", content)
                        .putString("extraContent", extraContent)
                        .putInt("icon", icon)
                        .build())
                .build();

        WorkManager.getInstance(context).enqueue(request);
    }

    /**
     * Establece un worker para enviar una notificacion periodica cada delay tiempo despues de initialDelay
     *
     * @param initialDelay Retraso inicial
     * @param unit         Unidad de retraso inicial
     * @param icon         Icono de la notificacion
     * @param title        Titulo de la notificacion
     * @param content      Contenido de la notificacion
     * @param extraContent Contenido extra de la notificacion
     * @param delay        Tiempo entre notifiaciones
     * @param delayUnit    Unidad del tiempo entre notificaciones
     */
    public void setPushNotificationWorkerPeriodic(int initialDelay, TimeUnit unit, int delay, TimeUnit delayUnit, int icon, String title, String content, String extraContent) {
        WorkRequest request = new PeriodicWorkRequest.Builder(NotificationWorker.class, delay, delayUnit)
                .setInitialDelay(initialDelay, unit)
                .setInputData(new Data.Builder()
                        .putString("title", title)
                        .putString("content", content)
                        .putString("extraContent", extraContent)
                        .putInt("icon", icon)
                        .build())
                .build();

        WorkManager.getInstance(context).enqueue(request);
    }

    /**
     * Manda una notificacion push
     *
     * @param icon         Icono de la notificacion
     * @param title        Titulo de la notificacion
     * @param content      Contenido de la notificacion
     * @param extraContent Contenido extra de la notificacion
     */
    public void sendPushNotification(int icon, String title, String content, String extraContent) {
        pushNotification.sendNotification(icon, title, content, extraContent);
    }

    /**
     * Para todos los workers
     */
    public void stopPushNotificationWorker() {
        WorkManager.getInstance(context).cancelAllWork();
    }
}
