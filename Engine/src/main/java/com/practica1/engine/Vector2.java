package com.practica1.engine;

public class Vector2
{
    public int x = 0;
    public int y = 0;
    public Vector2(int _x, int _y)
    {
        x = _x;
        y = _y;
    }

    public Vector2 sum(Vector2 other)
    {
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 mul_vec(Vector2 other)
    {
        return new Vector2(x * other.x, y * other.y);
    }
    public Vector2 mul_scalar(int scalar)
    {
        return new Vector2(x * scalar, y * scalar);
    }

    public double magnitude()
    {
        return Math.sqrt(x * x + y * y);
    }

    public void normalize()
    {
        double magnitude = Math.sqrt(x * x + y * y);

        if (magnitude != 0) {
            x /= magnitude;
            y /= magnitude;
        }
    }
}
