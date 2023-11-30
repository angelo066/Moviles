package com.practica1.androidgame;

import com.practica1.androidengine.Engine;
import com.practica1.androidengine.Scene;
import com.practica1.androidengine.Color;


import org.w3c.dom.Text;

public class Shop extends Scene {

    //Enum que indica que le estamos vendiendo al usuario
    private enum Skin_Type {BACKGROUND, CODE, COLORS}

    //Botones con los que compramos skins
    private ButtonObject[] skins;

    //Precios de las skins
    private TextObject[] prices;

    //Imagenes que muestran lo que contiene la skin
    private ImageObject[] skin_Images;

    //Para tener en cuenta que estamos vendiendo
    private Skin_Type type;

    //Mostrar el icono de las monedas
    private ImageObject coins;

    //Escribir la cantidad de monedas que tiene el usuario
    private TextObject coin_cuantity;

    //Texto de la parte superior de la pantalla que nos indica en que tienda estamos
    private TextObject type_Text;

    //Boton para pasar a la siguiente pantalla
    private ButtonObject forward;

    //Boton para volver a la pantalla anterior
    private ButtonObject backward;

    public Shop(){
        this.width = 1080;
        this.height = 1920;
    }

    @Override
    public void init(Engine engine){
        super.init(engine);

        Vector2 pos = new Vector2(1000, 200);
        Vector2 size = new Vector2(100, 100);

        coins = new ImageObject(engine.getGraphics(), pos, size, "coins.png");

    }

    public void render(){
        //Fondo de al APP
        graphics.clear(Color.WHITE.getValue());

        // Fondo del juego
        graphics.setColor(Color.WHITE.getValue());
        graphics.fillRectangle(0, 0, width, height);

        //Objetos
        coins.render();
    }
}
