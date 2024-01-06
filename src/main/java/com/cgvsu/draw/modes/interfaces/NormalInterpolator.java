package com.cgvsu.draw.modes.interfaces;

import com.cgvsu.math.vector.VectorDimThree;

public interface NormalInterpolator {
    VectorDimThree interpolate(float x, float y);
}
