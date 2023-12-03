package com.practica1.gamelogic;


import com.practica1.engine.Engine;
import com.practica1.engine.Scene;

/**
 * Escena para la pre carga de assets
 */
public class AssetsLoad extends Scene {
    public AssetsLoad() {
        this.width = 1080;
        this.height = 1920;
    }

    @Override
    public void init(Engine engine) {
        super.init(engine);

        engine.getAudio().loadSound("botonInterfaz.wav");
        engine.getAudio().loadSound("clickboton.wav");
        engine.getAudio().loadSound("yuju.wav");
        engine.getAudio().loadSound("douh.wav");

        ResourceManager.getInstance().createFont("BarlowCondensed-Regular.ttf", 200, true, true);
        ResourceManager.getInstance().createFont("Nexa.ttf", 80, false, false);

        ResourceManager.getInstance().createImage("volver.png");
        ResourceManager.getInstance().createImage("ojo.png");
        ResourceManager.getInstance().createImage("ojo2.png");

        engine.setScene(new MainMenu());
    }

}
