package com.cgvsu.draw.modes.interfaces;

import com.cgvsu.math.vector.VectorDimThree;

public interface RayInterpolator {
    VectorDimThree interpolate(float x, float y, VectorDimThree lightSource);
}
