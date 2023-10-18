package com.example.mastermind;

import com.example.engine.Engine;
import com.example.engine.Scene;
import com.example.engine.TouchEvent;

import java.util.ArrayList;

public class MasterMind implements Scene {

    private Engine engine;

    private float x;
    private float y;
    private int radius;
    private int speed;

    @Override
    public void init(Engine engine) {
        this.engine = engine;

        this.x = 50;
        this.y = 50;
        this.radius = 50;
        this.speed = 150;
    }

    @Override
    public void update(double deltaTime) {
        int maxX = this.engine.getGraphics().getWidth() - this.radius;

        this.x += this.speed * deltaTime;
        this.y += 2 * deltaTime;
        while (this.x < 0 || this.x > maxX - this.radius) {
            // Vamos a pintar fuera de la pantalla. Rectificamos.
            if (this.x < 0) {
                // Nos salimos por la izquierda. Rebotamos.
                this.x = -this.x;
                this.speed *= -1;
            } else if (this.x > maxX - this.radius) {
                // Nos salimos por la derecha. Rebotamos
                this.x = 2 * (maxX - this.radius) - this.x;
                this.speed *= -1;
            }
        }
    }

    @Override
    public void render() {
        this.engine.getGraphics().clear(0xFFFFFF);
        this.engine.getGraphics().setColor(0x1111FF);
        this.engine.getGraphics().fillCircle(this.x, this.y, this.radius);

    }

    @Override
    public void handleInput(ArrayList<TouchEvent> events) {

    }
}