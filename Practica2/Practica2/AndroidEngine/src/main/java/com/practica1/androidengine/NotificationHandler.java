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
import androidx.core.graphics.drawable.IconCompat;

public class NotificationHandler<T> {

    private final String CHANNEL_ID = "MasterMind";
    private final CharSequence name = "Name";
    private final String description = "Description";
    private Context context;
    private Class<T> intentActionClass;
    private int notificationId;

    public NotificationHandler(Context context, Class<T> intentActionClass) {
        this.context = context;
        this.intentActionClass = intentActionClass;
        this.notificationId = 0;

        createChannel();
    }


    private void createChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private PendingIntent createIntent() {
        Intent intent = new Intent(context, intentActionClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

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

    public void sendNotification(int icon, String title, String content, String extraContent) {

        NotificationCompat.Builder builder = createNotification(icon, title, content, extraContent);

        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(notificationId, builder.build());
            notificationId++;
        }
    }
}
