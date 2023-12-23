package com.practica1.androidengine;

import android.content.Context;
import android.view.SurfaceView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase motor de la aplicacion en android
 */
public class Engine implements Runnable {

    private volatile boolean running = false;
    private Thread thread;
    private SurfaceView view;
    private Graphics graphics;
    private Input input;
    private Audio audio;
    private Scene scene;
    private Scene newScene;
    private AdManager ads;
    private SensorHandler sensorHandler;
    private Context context;

    /**
     * @param view Ventana de la aplicacion
     */
    public Engine(SurfaceView view) {
        this.view = view;
        this.ads = null;
        this.sensorHandler = null;
        this.context = null;
        this.graphics = new Graphics(view);
        this.input = new Input(view);
        this.audio = new Audio(view.getContext().getAssets());
        this.scene = null;
        this.newScene = null;
    }

    /**
     * @return Objeto Graphics de la aplicacion
     */
    public Graphics getGraphics() {
        return graphics;
    }

    /**
     * @return Objeto Input de la aplicacion
     */
    public Input getInput() {
        return input;
    }

    /**
     * @return Objeto Audio de la aplicacion
     */
    public Audio getAudio() {
        return audio;
    }

    public void setAds(AdManager adManager) {
        ads = adManager;
    }

    public AdManager getAds() {
        return ads;
    }

    public void setSensorHandler(SensorHandler s) {
        sensorHandler = s;
    }

    public SensorHandler getSensorHandler() {
        return sensorHandler;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public SurfaceView getView() {
        return view;
    }

    public void run() {
        if (thread != Thread.currentThread())
            throw new RuntimeException("run() should not be called directly");            // Programación defensiva

        while (running && view.getWidth() == 0) ;

        long lastFrameTime = System.nanoTime();

        while (running) {

            //cambiar la escena
            changeScene();

            if (scene == null) break;

            //handleInput
            handleInput();

            //update
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            update(elapsedTime);

            // renderizado
            render();
        }

    }

    /**
     * Reanuda la ejecucion de la aplicacion
     */
    public void resume() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Pausa la ejecucion de la aplicacion
     */
    public void pause() {
        if (running) {
            running = false;
            while (true) {
                try {
                    thread.join();
                    thread = null;
                    break;
                } catch (InterruptedException ie) {
                    //Esto no debería ocurrir nunca
                }
            }
        }
    }

    /**
     * Establece la escena de la aplicacion
     *
     * @param scene Escena a establecer
     */
    public void setScene(Scene scene) {
        if (this.scene == null)
            this.scene = scene;
        else
            newScene = scene;
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Cambia, si es necesario, la escena al principio de frame
     */
    private void changeScene() {
        if (newScene != null) {
            scene = newScene;
            newScene = null;
        }
    }

    /**
     * Update del motor
     *
     * @param deltaTime
     */
    public void update(double deltaTime) {
        scene.update(deltaTime);
    }

    /**
     * Renderizado del motor
     */
    public void render() {
        graphics.prepareRender();
        scene.render();
        graphics.releaseRender();
    }

    /**
     * Manejo de input del motor
     */
    private void handleInput() {

        ArrayList<TouchEvent> inputEvents = input.getTouchEvents();

        for (TouchEvent event : inputEvents) {
            event.x -= graphics.getTranslateX();
            event.y -= graphics.getTranslateY();
            event.x /= graphics.getScaleX();
            event.y /= graphics.getScaleY();
        }
        scene.handleInput(inputEvents);
    }

    /**
     * Apertura de archivos
     */
    public BufferedReader openAssetFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(graphics.getAssetManager().open(fileName), "UTF-8"));
        return br;
    }

    /**
     * Devuelve las rutas de todos los archivos de una carpeta
     */
    public List<String> obtainFolderFiles(String baseRoute) {
        List<String> nameFiles = new ArrayList<>();

        try {
            // Obtenemos la lista de archivos
            String[] fileList = graphics.getAssetManager().list(baseRoute);

            // Si la carpeta no estaba vacia
            if (fileList != null) {
                // Creamos la ruta completa
                for (String file : fileList) {
                    String completeRoute = baseRoute + "/" + file;
                    nameFiles.add(completeRoute);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return nameFiles;
    }

}
