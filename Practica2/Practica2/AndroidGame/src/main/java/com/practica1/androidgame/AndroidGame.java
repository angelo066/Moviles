package com.practica1.androidgame;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.practica1.androidengine.AdManager;
import com.practica1.androidengine.Engine;
import com.practica1.androidengine.NotificationHandler;
import com.practica1.androidengine.SensorHandler;
import com.practica1.androidengine.ShareManager;

//TODO:
//  Securizar con NDK
//  Persistencia <--
//  Paletas de Colores
//  Compartir <--
//  Notificiaciones
//  Memoria
//  Estilo predeterminado en la tienda


public class AndroidGame extends AppCompatActivity {
    private Engine engine;

    private NotificationHandler notificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_android_game);
        SurfaceView surfaceView = findViewById(R.id.surfaceView);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        AdManager ads = new AdManager(this, findViewById(R.id.adView));
        SensorHandler sensorHandler = new SensorHandler(this);
        ShareManager shareManager = new ShareManager(surfaceView, this);
        notificationHandler = new NotificationHandler(this, this.getClass());

        engine = new Engine(surfaceView);
        engine.setAds(ads);
        engine.setSensorHandler(sensorHandler);
        engine.setShareManager(shareManager);


        ResourceManager.Init(engine);
        SceneManager.Init(engine);
        GameManager.Init(engine);

        GameManager.getInstance().setContext(this);

        ResourceManager.getInstance().loadLevels();
        SceneManager.getInstance().addScene(new AssetsLoad());
        SceneManager.getInstance().goToNextScene();


        engine.resume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        engine.getSensorHandler().onResume();
        SceneManager.getInstance().loadData();
        engine.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        engine.getSensorHandler().onPause();
        SceneManager.getInstance().saveData();
        notificationHandler.sendNotification(R.mipmap.ic_launcher,"MasterMind","Notificacion de prueba",
                "Parte extensible Parte extensible Parte extensible Parte extensible Parte extensible Parte extensible Parte extensible");
        engine.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        engine.getSensorHandler().onDestroy();
        ResourceManager.Release();
        SceneManager.Release();
        engine.getAds().destroy();
    }

    private void a() {

        String CHANNEL_ID = "Canal";

        //Crear el canal
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);
            CharSequence name = "Nombre";
            String description = "Descripcion";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        //Intent
        Intent intent = new Intent(this, AndroidGame.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        //Crear la notificacion
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //Mandar la notificacion
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            int notificationId = 0;
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(notificationId, builder.build());
        }


    }
}