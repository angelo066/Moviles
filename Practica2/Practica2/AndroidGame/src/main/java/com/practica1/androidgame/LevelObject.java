package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.TouchEvent;


/**
 * GameObject Texto, encapsula las funcionalidades de pintar texto dentro de un objeto
 */
public class LevelObject {
    private Vector2 pos;
    private Vector2 size;
    private Vector2 iniPos;
    private Graphics graphics;
    ButtonObject levelButton;
    ImageObject lockImage;
    TextObject levelText;
    boolean unlock = false;
    boolean completed = true;


    /**
     * @param graphics Objeto graphics del engine
     * @param pos      Posicion del texto
     */
    public LevelObject(Graphics graphics, Vector2 pos, Vector2 size, int id) {

        this.graphics = graphics;
        this.pos = new Vector2(pos);
        this.iniPos = pos;
        this.size = size;

        Vector2 textPos = new Vector2(pos.x + size.x/2, pos.y + size.y /2);
        this.levelText = new TextObject(graphics, textPos, "Nexa.ttf", String.valueOf(id), Color.BLACK, 100, false, false);
        this.levelText.center();
        this.levelButton = new ButtonObject(graphics, pos, size, 20, Color.OPACITY_GREY, null);

        this.lockImage = new ImageObject(graphics, textPos, new Vector2(150,150), "lock.png");
        this.lockImage.center();
    }

    /**
     * Render
     */
    public void render()
    {
        // Renderizamos el boton
        levelButton.render();

        // Si esta completado
        if(!completed)
        {
            // Ponemos un recuadro con mas opacidad encima
            graphics.setColor(Color.WHITE.getValue());
            graphics.fillRoundRectangle(pos.x, pos.y, size.x, size.y, 20);
        }

        // Si no desbloqueado mostramos el candado
        if(!unlock)
        {
            lockImage.render();
        }
        else {
            levelText.render();
        }

    }

    public void setCompleted(boolean s)
    {
        if(unlock)
            completed = s;
    }

    public void setUnlock(boolean u)
    {
        unlock = u;
    }

    public boolean handleInput(TouchEvent event) {
        return levelButton.handleInput(event);
    }
}
