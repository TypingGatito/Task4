package com.cgvsu.math;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй
public class Vector3f {
    public float x;
    public float y;
    public float z;
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector3f)) {
            return false;
        }
        final float eps = 1e-7f;
        return Math.abs(x - ((Vector3f)o).x) < eps && Math.abs(y - ((Vector3f)o).y) < eps && Math.abs(z - ((Vector3f)o).z) < eps;
    }
}
