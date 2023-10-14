package com.example.desktopgame;

import com.example.desktop_engine.EngineDesktop;

import com.example.desktop_engine.GraphicsDesktop;

public class DesktopGame {
    public static void main(String[]args){
        EngineDesktop engine = new EngineDesktop();

        Thread t = new Thread(engine);
        t.start();

    }
}