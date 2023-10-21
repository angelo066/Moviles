package com.practica1.engine;

public class GameObject
{
    public GameObject(Engine e)
    {
        engine = e;
    }
    public Engine engine = null;
    public int posX = 0;
    public int posY = 0;
    public void init(){};
    public void update(double deltaTime){}

    public void render(){}
    public void setPosX(int x) {posX = x;};
    public void setPosY(int y) {posY = y;};
}
