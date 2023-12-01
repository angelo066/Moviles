package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Font;
import com.practica1.androidengine.Graphics;


/**
 * GameObject Texto, encapsula las funcionalidades de pintar texto dentro de un objeto
 */
public class BuyObject {
    private Vector2 pos;
    private Vector2 iniPos;
    private Graphics graphics;
    ButtonObject buyButton;
    ImageObject selectedImage;
    ImageObject coinImage;
    TextObject priceText;
    boolean unlock = false;
    boolean selected = true;


    /**
     * @param graphics Objeto graphics del engine
     * @param pos      Posicion del texto
     */
    public BuyObject(Graphics graphics, Vector2 pos, Vector2 size) {

        this.graphics = graphics;
        this.pos = new Vector2(pos);
        this.iniPos = pos;

        this.buyButton = new ButtonObject(graphics, pos, size, "central.png");
        this.selectedImage = new ImageObject(graphics, pos, size, "select_skin.png");
        Vector2 pricePos = new Vector2(pos.x + size.x/2, pos.y + 50 + size.y);
        this.priceText = new TextObject(graphics, pricePos, "Nexa.ttf", String.valueOf(100), Color.BLACK, 50, false, false);
        this.priceText.center();
        this.coinImage = new ImageObject(graphics, new Vector2(pricePos.x + 80, pricePos.y), new Vector2(30,30), "coins.png");
        this.coinImage.center();
    }

    /**
     * Render del texto
     */
    public void render()
    {
        // Renderizamos la imagen de la skin
        buyButton.render();

        // Si esta seleccionada marcamos el reborde
        if(selected)
            selectedImage.render();

        // Si no esta comprada mostramos precios
        if(!unlock)
        {
            priceText.render();
            coinImage.render();
        }

    }

    public void setSelected(boolean s)
    {
        if(unlock)
            selected = s;
    }

    public void setUnlock(boolean u)
    {
        unlock = u;
    }
}
