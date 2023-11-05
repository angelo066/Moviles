package com.practica1.gamelogic;

import com.practica1.engine.Color;
import com.practica1.engine.Engine;
import com.practica1.engine.Font;
import com.practica1.engine.GameObject;
import com.practica1.engine.TouchEvent;
import com.practica1.engine.Vector2;

public class Boton extends GameObject {
    Vector2 size;
    Color color;
    String textContent;
    boolean redondeado = false;
    boolean conTexto = false;
    boolean conImagen = false;
    float arc;
    Font font;
    Texto text;
    Imagen image;

    // Constructora para boton sin redondeo de bordes
    public Boton(Engine e, int sceneWidth, int sceneHeight, Vector2 size, Color colorBoton, Color colorTexto) {
        super(e, sceneWidth, sceneHeight);
        this.size = size;
        this.color = colorBoton;
    }

    public Boton(Engine e, int sceneWidth, int sceneHeight, Vector2 size, Font font, String text, Color colorBoton, Color colorTexto) {
        super(e, sceneWidth, sceneHeight);
        this.size = size;
        conTexto = true;
        this.font = font;
        this.textContent = text;
        this.color = colorBoton;
        this.text = new Texto(e,sceneWidth,sceneHeight, pos, font, text, colorTexto);
        this.text.centrar();
    }

    // Constructora para boton con redondeo de bordes
    public Boton(Engine e, int sceneWidth, int sceneHeight, Vector2 size, float arc, Color colorBoton, Color colorTexto) {
        super(e, sceneWidth, sceneHeight);
        this.size = size;
        this.arc = arc;
        redondeado = true;
        this.color = colorBoton;
    }

    public Boton(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, Vector2 size, float arc, Font font, String text, Color colorBoton, Color colorTexto) {
        super(e, sceneWidth, sceneHeight, pos);
        this.size = size;
        this.arc = arc;
        redondeado = true;
        conTexto = true;
        this.font = font;
        this.textContent = text;
        this.color = colorBoton;
        this.text = new Texto(e,sceneWidth,sceneHeight, new Vector2(pos), font, text, colorTexto);
        this.text.centrar();
    }

    public Boton(Engine e, int sceneWidth, int sceneHeight, Vector2 pos, Vector2 size, String ruta) {
        super(e, sceneWidth, sceneHeight, pos);
        this.size = size;
        this.image = new Imagen(e,sceneWidth,sceneHeight, pos, size, ruta);
        conImagen = true;
    }

    @Override
    public void render() {

        if (conImagen) {
            this.image.render();
        } else {
            engine.getGraphics().setColor(color);
            if (redondeado)
                engine.getGraphics().fillRoundRectangle(pos.x, pos.y, size.x, size.y, arc);
            else
                engine.getGraphics().fillRectangle(pos.x, pos.y, size.x, size.y);

            if (conTexto)
                text.render();
        }

    }

    @Override
    public boolean handleInput(TouchEvent event) {
        int touchX = event.x;
        int touchY = event.y;
        boolean inside = false;

        if (event.type == TouchEvent.TouchEventType.CLICK) {
            //Dentro de manera horizontal
            if (touchX > pos.x && touchX < pos.x + size.x) {
                if (touchY > pos.y && touchY < pos.y + size.y) {
                    inside = true;
                    System.out.println("DENTRO");
                }
            }
        }


        //Entiendo que esto hay que devolverlo asi
        return inside;
    }

    // Modifica la posicion iniciar para que sea la centrada
    public void centrar() {
        pos.x = pos.x - size.x / 2;
        pos.y = pos.y - size.y / 2;
    }


}
