package com.practica1.androidengine;

import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import java.util.concurrent.TimeUnit;

public class NotificationHandler {

    private PushNotification pushNotification;
    private Context context;

    public NotificationHandler(Context context) {
        this.pushNotification = new PushNotification(context);
        this.context = context;
    }

    public void setPushNotificationWorker(int time, TimeUnit unit, int icon, String title, String content, String extraContent) {

        WorkRequest request = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(time, unit)
                .setInputData(new Data.Builder()
                        .putString("title", title)
                        .putString("content", content)
                        .putString("extraContent", extraContent)
                        .putInt("icon", icon)
                        .build())
                .build();

        WorkManager.getInstance(context).enqueue(request);
    }

    public void sendPushNotification(int icon, String title, String content, String extraContent) {
        pushNotification.sendNotification(icon, title, content, extraContent);
    }
}
