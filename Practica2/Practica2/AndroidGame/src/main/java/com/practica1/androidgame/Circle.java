package com.practica1.androidgame;

import com.practica1.androidengine.Color;
import com.practica1.androidengine.Graphics;
import com.practica1.androidengine.Image;
import com.practica1.androidengine.TouchEvent;

import java.io.Serializable;

/**
 * GameObject Circulo, encapsula la info y comportamientos relativos a un circulo del tablero
 */
public class Circle implements Serializable {
    private TextObject id;
    private Color color;
    private boolean uncovered;
    private boolean colorblind;
    private int circleRadius;
    private Vector2 pos;
    private ImageObject image;
    private transient Graphics graphics;

    //Para la serializacion
    private String imageName;

    /**
     * @param graphics     Objecto graphics del motor
     * @param pos          Posicion del circulo
     * @param circleRadius Radio del circulo
     */
    public Circle(Graphics graphics, Vector2 pos, int circleRadius) {
        this.graphics = graphics;
        this.circleRadius = circleRadius;
        this.pos = pos;
        this.color = Color.NO_COLOR;

        int colorText = GameManager.getInstance().getCurrentSkinPalette().getColor2();
        this.id = new TextObject(graphics, new Vector2(pos.x + circleRadius, pos.y + circleRadius),
                "Nexa.ttf", String.valueOf(this.color.getId()), colorText, 50, false, false);
        this.id.center();

        this.uncovered = false;
        this.colorblind = false;

        image = null;
    }
    public Circle(Graphics graphics, Vector2 pos, int circleRadius, String imageRoute) {
        this.graphics = graphics;
        this.circleRadius = circleRadius;
        this.pos = pos;

        this.color = Color.NO_COLOR;
        int colorText = GameManager.getInstance().getCurrentSkinPalette().getColor2();
        this.id = new TextObject(graphics, new Vector2(pos.x + circleRadius, pos.y + circleRadius),
                "Nexa.ttf", String.valueOf(this.color.getId()), colorText, 50, false, false);
        this.id.center();

        this.uncovered = false;
        this.colorblind = false;

        imageName = imageRoute;
        // Creacion de la imagen
        image = new ImageObject(graphics, new Vector2(pos), new Vector2(circleRadius*2, circleRadius*2), imageRoute);
    }

    /**
     * Renderizado
     */
    public void render() {

        // Si se ha descubierto pintamos el color normal
        if (uncovered) {

            if(image != null)
                image.render();
            else
            {
                graphics.setColor(color.getValue());
                graphics.fillCircle(pos.x, pos.y, circleRadius);
            }

            // Si el modo daltonico esta activado pintamos el numero
            if (colorblind)
                id.render();

        }
        // Si no lo pintamos bloqueado
        else {
            int internCircleRadius = circleRadius / 4;
            graphics.setColor(Color.GREY.getValue());
            graphics.fillCircle(pos.x, pos.y, circleRadius);
            graphics.setColor(Color.DARK_GREY.getValue());
            graphics.fillCircle(pos.x + circleRadius - internCircleRadius, pos.y + circleRadius - internCircleRadius, internCircleRadius);
        }


    }

    /**
     * Manejo de input
     *
     * @param event
     * @return Si se ha pulsado el objeto
     */
    public boolean handleInput(TouchEvent event) {
        int touchX = event.x;
        int touchY = event.y;

        if (event.type == TouchEvent.TouchEventType.TOUCH_DOWN) {

            if (touchX > pos.x && touchX < pos.x + circleRadius * 2) {
                if (touchY > pos.y && touchY < pos.y + circleRadius * 2)
                    return true;
            }
        }
        return false;
    }

    /**
     * @return El color del circulo
     */
    public Color getColor() {
        return color;
    }

    /**
     * Asignar color e id (para el texto)
     */
    public void setColor(Color c) {
        this.color = c;
        this.id.setText(String.valueOf(color.getId()));
        this.id.center();
    }

    /**
     * Descubrir el color
     *
     * @param setUncover
     */
    public void setUncovered(boolean setUncover) {
        uncovered = setUncover;
    }

    /**
     * Activar modo daltonico
     *
     * @param setColorblind
     */
    public void setColorblind(boolean setColorblind) {
        this.colorblind = setColorblind;
    }

    /**
     * @return Si esta descubierto el color
     */
    public boolean getUncovered() {
        return this.uncovered;
    }

    /**
     * Traslada el objeto
     *
     * @param translateX
     * @param translateY
     */
    public void translate(int translateX, int translateY) {
        pos.x += translateX;
        pos.y += translateY;
        id.translate(translateX, translateY);

        if(image != null)
            image.translate(translateX,translateY);
    }

    /**
     * @return Posicion del objeto
     */
    public Vector2 getPos() {
        return pos;
    }

    public void setImage(Image image)
    {
        String imageName = image.getFile();
        imageName = imageName.replace("sprites/", "");

        this.imageName = imageName;
        this.image = new ImageObject(graphics, new Vector2(pos), new Vector2(circleRadius*2, circleRadius*2), image);
    }

    public Image getImage()
    {
        if(image == null) return null;
        return image.getImage();
    }

    public void load(Graphics graphics) {
        this.graphics = graphics;

        this.id.load(graphics);

        if(this.imageName != null){
            // Creacion de la imagen
            image = new ImageObject(graphics, new Vector2(pos), new Vector2(circleRadius*2, circleRadius*2), imageName);
        }
    }

}
