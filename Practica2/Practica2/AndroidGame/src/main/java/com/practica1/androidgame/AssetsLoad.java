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
        engine.getAudio().loadSound("silla.wav");

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
        ResourceManager.getInstance().createImage("backToMonkey.jpg");

        ResourceManager.getInstance().loadBackgroundImages();
        ResourceManager.getInstance().loadThumbnailsImages();
        ResourceManager.getInstance().loadPackCodes();
        ResourceManager.getInstance().loadShop();

        ResourceManager.getInstance().assetsChargeFinalized();

        SceneManager.getInstance().addScene(new MainMenu());
        SceneManager.getInstance().goToNextScene();

    }

}
