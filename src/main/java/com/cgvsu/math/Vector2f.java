package com.cgvsu.math;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй
public class Vector2f {
    public float x;
    public float y;
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector2f)) {
            return false;
        }
        final float eps = 1e-7f;
        return Math.abs(x - ((Vector2f)o).x) < eps && Math.abs(y - ((Vector2f)o).y) < eps;
    }
}
