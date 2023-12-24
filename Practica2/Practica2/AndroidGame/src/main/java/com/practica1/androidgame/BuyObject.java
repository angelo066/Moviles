package com.practica1.androidgame;

import com.practica1.androidengine.Graphics;


/**
 * GameObject BuyObject, representa un objecto de la tienda
 */
public class BuyObject {
    private Vector2 pos;
    private Vector2 iniPos;
    private Graphics graphics;
    private ButtonObject buyButton;
    private ImageObject selectedImage;
    private ImageObject coinImage;
    private TextObject priceText;
    private int price;
    private boolean unlock = false;
    private boolean selected = false;


    /**
     * @param graphics Objeto graphics del engine
     * @param pos      Posicion del texto
     */
    public BuyObject(Graphics graphics, Vector2 pos, Vector2 size, String image) {

        this.graphics = graphics;
        this.pos = new Vector2(pos);
        this.iniPos = pos;

        this.buyButton = new ButtonObject(graphics, pos, size, image);
        this.selectedImage = new ImageObject(graphics, pos, size, "select_skin.png");
        Vector2 pricePos = new Vector2(pos.x + size.x / 2, pos.y + 50 + size.y);

        int colorText = GameManager.getInstance().getCurrentSkinPalette().getColor2();
        this.priceText = new TextObject(graphics, pricePos, "Nexa.ttf", String.valueOf(100), colorText, 50, false, false);
        this.priceText.center();
        this.coinImage = new ImageObject(graphics, new Vector2(pricePos.x + 80, pricePos.y), new Vector2(30, 30), "coins.png");
        this.coinImage.center();
    }

    /**
     * Render del texto
     */
    public void render() {
        // Renderizamos la imagen de la skin
        buyButton.render();

        // Si esta seleccionada marcamos el reborde
        if (selected)
            selectedImage.render();

        // Si no esta comprada mostramos precios
        if (!unlock) {
            priceText.render();
            coinImage.render();
        }

    }

    /**
     * Establece el precio
     *
     * @param p Precio
     */
    public void setPrice(int p) {
        priceText.setText(String.valueOf(p));
        price = p;
    }

    /**
     * @return Precio del objeto
     */
    public int getPrice() {
        return price;
    }

    /**
     * Bloquea/desbloquea el objeto
     *
     * @param u Estado de desbloqueo
     */
    public void setUnlock(boolean u) {
        unlock = u;
    }

    /**
     * @return Si el objeto esta desbloqueado
     */
    public boolean isUnlocked() {
        return unlock;
    }

    /**
     * @return Boton del objeto
     */
    public ButtonObject getButton() {
        return buyButton;
    }

    /**
     * Resetea el color del texto, se usa al comprar en la tienda
     */
    public void resetColor() {
        priceText.resetColor();
    }

    public void setSelected(boolean s)
    {
        selected = s;
    }

}
