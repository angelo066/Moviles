package com.practica1.engine;

public class GameObject
{
    public GameObject(Engine e)
    {
        engine = e;
        pos = new Vector2(0,0);
    }
    public Engine engine = null;
    public Vector2 pos;
    public void init(){};
    public void update(double deltaTime){}

    public void render(){}
    public void setPosX(int x) {pos.x = x;};
    public void setPosY(int y) {pos.y = y;};
}
