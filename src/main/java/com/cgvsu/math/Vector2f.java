package com.cgvsu.math;

public class Vector2f {
    private float x, y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector2f)) {
            return false;
        }
        final float eps = 1e-7f;
        return Math.abs(x - ((Vector2f) o).x) < eps && Math.abs(y - ((Vector2f) o).y) < eps;
    }
}
