package com.practica1.androidgame;

import android.content.Context;

import com.practica1.androidengine.Engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
* Clase que gestiona la gestion de librerias con c++
* Tambien es el encargado de los metodos de securizacion
 */
public class NDKManager {

    static {
        System.loadLibrary("hash");
    }

    private static NDKManager Instance;
    private String key = "HOMERO";
    public NDKManager() {
    }
    public static void Init(){

        if (Instance == null) {
            Instance = new NDKManager();
        }

    }
    public static NDKManager getInstance() {
        return Instance;
    }

    /**
     * Funcion que se llama desde c++ para generar un hash a partir de un string
     * @param h
     * @return
     */
    public native String createHash(String h);

    public String getKey(){return key;}

    /**
     * Securiza y guarda un hash en un archivo (Si filename == "") no lo guarda
     * Devuelve el hash final
     * @param data
     * @param filename
     * @return
     */
    // Metodo que
    // Si entra filename != "", guardamos el hash en archivo, sino simplemente lo calculamos
    public String secure(String data, String filename)
    {
        /// SECURIZAR /// ---------------------------------------------------------------------------
        String standard = data;
        String standardHash = NDKManager.getInstance().createHash(standard);
        String key = NDKManager.getInstance().getKey() + standardHash;
        String keyHash = NDKManager.getInstance().createHash(key);

        // Guardar en archivo
        if(filename != "")
        {
            try {
                FileOutputStream fout = GameManager.getInstance().getContext().openFileOutput(filename, Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream(fout);

                out.writeObject(keyHash);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error: Archivo no encontrado");
                e.printStackTrace();
            }
            catch (IOException e) {
                System.out.println("Error de entrada/salida al guardar el hash " + filename);
                e.printStackTrace();
            }
        }

        /// SECURIZAR /// ---------------------------------------------------------------------------

        return keyHash;
    }

    /**
     * Comprueba que el el hash guardado y el generado al cargar un archivo coinciden
     * @param savedata
     * @param filename
     * @return
     */
    public boolean checkHash(String savedata, String filename)
    {
        boolean check = true;
        String keyHash = NDKManager.getInstance().secure(savedata, ""); // obtenemos el hash

        File fileExist = new File("/data/user/0/com.practica1.androidgame/files/" + filename);
        if (fileExist.exists()) {
            try {
                // Leemos el archivo hash del player que habiamos guardado
                FileInputStream hashfile = GameManager.getInstance().getContext().openFileInput(filename);
                ObjectInputStream inh = new ObjectInputStream(hashfile);
                String outHash = (String) inh.readObject();

                // Si no coinciden es que alguien ha modificado el archivo desde fuera
                if (keyHash != outHash)
                    check = false;
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        return check;
    }


}
