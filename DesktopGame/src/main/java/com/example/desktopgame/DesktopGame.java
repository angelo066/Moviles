package com.example.desktopgame;

import com.example.desktop_engine.EngineDesktop;

public class DesktopGame {
    public static void main(String[]args){
        EngineDesktop a = new EngineDesktop();

        Thread t = new Thread(a);
        t.start();
    }
}