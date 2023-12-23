package com.practica1.androidengine;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class NotificationWorker extends Worker {
    private PushNotification pushNotification;

    public NotificationWorker(Context context, WorkerParameters parameters) {
        super(context, parameters);
        pushNotification = new PushNotification(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        String title = getInputData().getString("title");
        String content = getInputData().getString("content");
        String extraContent = getInputData().getString("extraContent");
        int icon = getInputData().getInt("icon", 1);

        return pushNotification.sendNotification(icon, title, content, extraContent);
    }
}
