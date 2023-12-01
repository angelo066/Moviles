package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Gestor de esceneas
 */
public class SceneManager {

    private static SceneManager Instance;
    private Engine engine;
    private Queue<Scene> sceneQueue;
    private boolean changeScene;

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

    public void goToNextScene(){
        engine.setScene(getScene());
        removeScene();
    }


    public void addScene(Scene newScene) {
        if (newScene == null) return;
        Instance.sceneQueue.add(newScene);
        if(!newScene.isInitialized())newScene.init(engine);
    }

    public Scene getScene() {
        return Instance.sceneQueue.element();
    }

    public void removeScene(){
        Instance.sceneQueue.remove();
    }
}
