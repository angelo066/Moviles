package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        engine.setScene(getNextScene());
        removeScene();
    }


    public void addScene(Scene newScene) {
        if (newScene == null) return;
        Instance.sceneQueue.add(newScene);
        if(!newScene.isInitialized())newScene.init(engine);
    }

    public Scene getNextScene() {
        if(Instance.sceneQueue.size() > 0) return Instance.sceneQueue.element();
        return null;
    }

    public void removeScene(){
        Instance.sceneQueue.remove();
    }

    public void saveData(){
        Scene currentScene = engine.getScene();
        if(currentScene != null){
            System.out.println("Partida Guardada");
            currentScene.saveData();
        }
    }

    public void loadData(){
        File file = new File("/data/user/0/com.practica1.androidgame/files/game.txt");

        if(file.exists()){
            System.out.println("Partida cargada");
            MasterMind masterMind = new MasterMind(Difficulty.EASY);
            masterMind.savedGame();
            addScene(masterMind);
            goToNextScene();

            file.delete();
        }

        /*
        try{
            FileInputStream file2 = GameManager.getInstance().getContext().openFileInput("game.txt");
            ObjectInputStream in = new ObjectInputStream(file2);

            if(file != null){
                System.out.println("Partida cargada");
                MasterMind masterMind = new MasterMind();
                masterMind.loadData();
                addScene(masterMind);
                goToNextScene();

                file2.close();
                file2.reset();

            }
            else{
                System.out.println("No hay ninguna partida guardaada");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        */


    }
}
