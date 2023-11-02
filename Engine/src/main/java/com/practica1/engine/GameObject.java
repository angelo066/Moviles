package com.practica1.engine;

public class GameObject
{
    public GameObject(Engine e)
    {
        engine = e;
        pos = new Vector2(0,0);
    }
    public GameObject(Engine e, Vector2 pos)
    {
        engine = e;
        this.pos = pos;
    }
    public Engine engine = null;
    public Vector2 pos;
    public void init(){};
    public void update(double deltaTime){}

    public void render(){}
    //Que tiene que devolver handleInput
    public boolean handleInput(){return false;}
    public void setPosX(int x) {pos.x = x;};
    public void setPosY(int y) {pos.y = y;};
}
