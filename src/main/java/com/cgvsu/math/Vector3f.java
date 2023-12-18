package com.cgvsu.math;

public class Vector3f {
    private float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector3f)) {
            return false;
        }
        final float eps = 1e-7f;
        return Math.abs(x - ((Vector3f) o).x) < eps && Math.abs(y - ((Vector3f) o).y) < eps && Math.abs(z - ((Vector3f) o).z) < eps;
    }
}
