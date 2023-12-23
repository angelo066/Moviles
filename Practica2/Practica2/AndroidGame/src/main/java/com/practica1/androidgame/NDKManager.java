package com.practica1.androidgame;

public class NDKManager {

    static {
        System.loadLibrary("fibonacci");
    }
    public native int computeFibonacci(int n);
}
