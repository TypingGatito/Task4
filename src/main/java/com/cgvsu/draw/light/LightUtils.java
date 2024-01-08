package com.cgvsu.draw.light;

import com.cgvsu.math.vector.VectorDimThree;

public class LightUtils {
    public static float findL(VectorDimThree ray, VectorDimThree normal) {
        return -VectorDimThree.scaleMultiply(ray, VectorDimThree.normalize(normal));
    }
    public static VectorDimThree findRay(VectorDimThree lightSource, VectorDimThree point) {
        VectorDimThree ray = VectorDimThree.subtractVector(point, lightSource);

        return VectorDimThree.normalize(ray);
    }
}
