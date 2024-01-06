package com.cgvsu.draw.light;

import com.cgvsu.math.vector.VectorDimThree;

public class LightParams {
    private VectorDimThree LightSource;
    private double k = 0.4;

    public void setLightSource(VectorDimThree lightSource) {
        LightSource = lightSource;
    }

    public void setK(double k) {
        this.k = k;
    }

    public VectorDimThree getLightSource() {
        return LightSource;
    }

    public double getK() {
        return k;
    }
}
