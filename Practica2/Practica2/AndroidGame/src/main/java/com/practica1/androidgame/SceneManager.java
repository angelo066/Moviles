package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Gestor de esceneas
 */
public class SceneManager {

    private static SceneManager Instance;
    private Engine engine;
    private Queue<Scene> sceneQueue;

    private SceneManager() {
        sceneQueue = new LinkedList<>();
    }

    /**
     * Inicializa el gestor
     */
    public static void Init(Engine engine) {
        if (Instance == null) {
            Instance = new SceneManager();
            Instance.engine = engine;
        }
    }

    /**
     * Libera el gestor
     */
    public static void Release() {
        Instance = null;
    }

    /**
     * @return La instancia del gestor de escenas
     */
    public static SceneManager getInstance() {
        return Instance;
    }

    /**
     * Setea la nueva escena en el engine de la aplicacion
     */
    public void goToNextScene(){
        engine.setScene(getNextScene());
        removeScene();
    }

    /**
     * Añade una nueva escena a la cola
     * @param newScene
     */
    public void addScene(Scene newScene) {
        if (newScene == null) return;
        sceneQueue.add(newScene);
        if(!newScene.isInitialized())newScene.init(engine);
    }

    /**
     * @return La siguiente escena en la cola
     */
    public Scene getNextScene() {
        return sceneQueue.peek();
    }

    /**
     * Borra la siguiente escena en la cola
     */
    public void removeScene(){
        sceneQueue.poll();
    }

    /**
     * Guarda la informacion de la escena actual
     */
    public void saveData(){
        Scene currentScene = engine.getScene();

        if(currentScene != null)
            currentScene.saveData();

    }

    /**
     * Carga la informacion de la ultima escena
     */
    public void loadData(){
        File file = new File("/data/user/0/com.practica1.androidgame/files/game.txt");

        if(file.exists()){

            MasterMind masterMind = new MasterMind(Difficulty.EASY);
            masterMind.savedGame();
            addScene(masterMind);
            goToNextScene();

            file.delete();
        }
    }
}
