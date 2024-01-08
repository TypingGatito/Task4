package com.cgvsu.draw.rasterisation;

import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;
import javafx.scene.paint.Color;

public class Interpolation {
    public static int interpolateARGB(VectorDimTwo point1, Color color1,
                                      VectorDimTwo point2, Color color2,
                                      VectorDimTwo point3, Color color3,
                                      VectorDimTwo pointToCenter) {
        VectorDimThree barycentric = calculateBarycentric(point1, point2, point3, pointToCenter);

        return interpolateARGB(barycentric.getX(), barycentric.getY(), barycentric.getZ(), color1, color2, color3);
    }

    public static int interpolateARGB (float alpha, float beta, float gamma,
                                       Color colorA, Color colorB, Color colorC) {
        double red = alpha * colorA.getRed() + beta * colorB.getRed() + gamma * colorC.getRed();
        double green = alpha * colorA.getGreen() + beta * colorB.getGreen() + gamma * colorC.getGreen();
        double blue = alpha * colorA.getBlue() + beta * colorB.getBlue() + gamma * colorC.getBlue();

        int argb = (255 << 24) + (clampColor(red) << 16) + (clampColor(green) << 8) + clampColor(blue);
        return argb;
    }

    private static int clampColor(double color) {
        int intColor = (int) (color * 255);
        int signMask = ~(intColor >>> 24);
        intColor &= signMask;
        int clampMask = ~((intColor >> 8) * 255);
        intColor &= clampMask;
        intColor &= 255;
        return intColor;
    }
    public static float interpolateZ(VectorDimThree point1, VectorDimThree point2, VectorDimThree point3, int x, int y) {
        VectorDimThree barycentric = calculateBarycentric(new VectorDimTwo(point1.getX(), point1.getY()),
                new VectorDimTwo(point2.getX(), point2.getY()),
                new VectorDimTwo(point3.getX(), point3.getY()), new VectorDimTwo(x, y));
        return interpolateZ(barycentric.getX(), barycentric.getY(), barycentric.getZ(),
                point1, point2, point3);
    }

    public static float interpolateZ(VectorDimThree point1, VectorDimThree point2, VectorDimThree point3, float x, float y) {
        VectorDimThree barycentric = calculateBarycentric(new VectorDimTwo(point1.getX(), point1.getY()),
                new VectorDimTwo(point2.getX(), point2.getY()),
                new VectorDimTwo(point3.getX(), point3.getY()), new VectorDimTwo(x, y));
        return interpolateZ(barycentric.getX(), barycentric.getY(), barycentric.getZ(),
                point1, point2, point3);
    }

    public static float interpolateZ(float alpha, float beta, float gamma,
            VectorDimThree point1, VectorDimThree point2, VectorDimThree point3) {
        return alpha * point1.getZ() + beta * point2.getZ() + gamma * point3.getZ();
    }
    public static VectorDimThree interpolateVectorDimThree(float alpha, float beta, float gamma,
                                                           VectorDimThree vec1, VectorDimThree vec2, VectorDimThree vec3) {
        VectorDimThree v1 = VectorDimThree.multiplyByScalar(alpha, vec1);
        VectorDimThree v2 = VectorDimThree.multiplyByScalar(beta, vec2);
        VectorDimThree v3 = VectorDimThree.multiplyByScalar(gamma, vec3);
        return VectorDimThree.sumVector(VectorDimThree.sumVector(v1, v2), v3);
    }

    public static VectorDimThree interpolateVectorDimThree(VectorDimThree point1, VectorDimThree v1, VectorDimThree point2,
                                                           VectorDimThree v2, VectorDimThree point3, VectorDimThree v3,
                                                           float x, float y) {
        VectorDimThree barycentric = calculateBarycentric(new VectorDimTwo(point1.getX(), point1.getY()),
                new VectorDimTwo(point2.getX(), point2.getY()),
                new VectorDimTwo(point3.getX(), point3.getY()),
                new VectorDimTwo(x, y));

        return interpolateVectorDimThree(barycentric.getX(), barycentric.getY(), barycentric.getZ(),
                v1, v2, v3);
    }

    public static VectorDimThree calculateBarycentric(VectorDimTwo point1,
                                                      VectorDimTwo point2,
                                                      VectorDimTwo point3,
                                                      VectorDimTwo pointToCenter) {

        return calculateBarycentric(point1.getX(), point1.getY(),
                point2.getX(), point2.getY(),
                point3.getX(), point3.getY(),
                pointToCenter.getX(), pointToCenter.getY());
    }
    public static VectorDimThree calculateBarycentric(VectorDimThree point1,
                                                      VectorDimThree point2,
                                                      VectorDimThree point3,
                                                      VectorDimTwo pointToCenter) {

        return calculateBarycentric(point1.getX(), point1.getY(),
                point2.getX(), point2.getY(),
                point3.getX(), point3.getY(),
                pointToCenter.getX(), pointToCenter.getY());
    }

    public static VectorDimThree calculateBarycentric(float x1, float y1,
                                                      float x2, float y2,
                                                      float x3, float y3,
                                                      float x, float y) {
        float def = x1 * (y2 - y3) +
                x2 * (y3 - y1) +
                x3 * (y1 - y2);

        float def1 = x * (y2 - y3) +
                x2 * (y3 - y) +
                x3 * (y - y2);

        float def2 = x1 * (y - y3) +
                x * (y3 - y1) +
                x3 * (y1 - y);

        float def3 = x1 * (y2 - y) +
                x2 * (y - y1) +
                x * (y1 - y2);

        return new VectorDimThree(def1 / def, def2 / def, def3 / def);
    }
}
