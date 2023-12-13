package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;

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
        ResourceManager.getInstance().createImage("coins.png");
        ResourceManager.getInstance().createImage("central.png");
        ResourceManager.getInstance().createImage("autobus.png");
        ResourceManager.getInstance().createImage("taberna.png");
        ResourceManager.getInstance().createImage("select_skin.png");
        ResourceManager.getInstance().createImage("lock.png");
        ResourceManager.getInstance().createImage("ArrowNavigators.png");
        ResourceManager.getInstance().createImage("ArrowNavigators_Left.png");
        ResourceManager.getInstance().createImage("moe_background.png");
        ResourceManager.getInstance().createImage("nuclear_background.png");
        ResourceManager.getInstance().createImage("otto_background.png");

        ResourceManager.getInstance().createImage("pack_1/pack_1_1.png");
        ResourceManager.getInstance().createImage("pack_1/pack_1_2.png");
        ResourceManager.getInstance().createImage("pack_1/pack_1_3.png");
        ResourceManager.getInstance().createImage("pack_1/pack_1_4.png");
        ResourceManager.getInstance().createImage("pack_1/pack_1_5.png");
        ResourceManager.getInstance().createImage("pack_1/pack_1_6.png");
        ResourceManager.getInstance().createImage("pack_1/pack_1_7.png");

        ResourceManager.getInstance().assetsChargeFinalized();

        SceneManager.getInstance().addScene(new MainMenu());
        SceneManager.getInstance().goToNextScene();
    }

}
