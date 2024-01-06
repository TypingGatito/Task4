package com.cgvsu.draw.light;

import com.cgvsu.math.vector.VectorDimThree;

public class LightUtils {
    public static float findL(VectorDimThree ray, VectorDimThree normal) {
/*        System.out.println(ray.toStr() + " R");
        System.out.println(VectorDimThree.normalize(normal).toStr() + " n");
        System.out.println(-VectorDimThree.scaleMultiply(ray, VectorDimThree.normalize(normal)));*/
        return -VectorDimThree.scaleMultiply(ray, VectorDimThree.normalize(normal));
    }
    public static VectorDimThree findRay(VectorDimThree lightSource, VectorDimThree point) {
        VectorDimThree ray = VectorDimThree.subtractVector(point, lightSource);

/*        System.out.println(ray.toStr() + " R");
        System.out.println(VectorDimThree.normalize(ray).toStr() + " R");*/
        return VectorDimThree.normalize(ray);
    }
}
