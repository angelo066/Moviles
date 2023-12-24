package com.practica1.androidgame;

import com.practica1.androidengine.Engine;

public class NDKManager {

    static {
        System.loadLibrary("hash");
    }

    private static NDKManager Instance;
    private String key = "HOMERO";
    public NDKManager() {
    }
    public static void Init(){

        if (Instance == null) {
            Instance = new NDKManager();
        }

    }
    public static NDKManager getInstance() {
        return Instance;
    }

    public native String createHash(String h);

    public String getKey(){return key;}
}
