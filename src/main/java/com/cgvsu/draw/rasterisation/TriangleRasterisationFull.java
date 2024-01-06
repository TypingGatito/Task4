package com.cgvsu.draw.rasterisation;

import com.cgvsu.draw.DrawUtils;
import com.cgvsu.draw.modes.interfaces.Lighter;
import com.cgvsu.draw.modes.interfaces.NormalInterpolator;
import com.cgvsu.draw.modes.interfaces.PixelExtractor;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorMethods;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.util.function.Function;


public class TriangleRasterisationFull {
    private float[][] zBuffer;
    private PixelWriter pixelWriter;
    private PixelExtractor pixelExtractor;
    private Lighter lighter;
    private int height;
    private int width;
    private float wx1;
    private float wx2;

    private float dxLeft;
    private float dxRight;
    private NormalInterpolator normalInterpolator;
    private NormalInterpolator rayI;
    //private Function<VectorDimThree, VectorDimThree> toProjection;
    VectorDimThree dr1;
    VectorDimThree dr2;
    VectorDimThree dr3;

    public TriangleRasterisationFull(PixelWriter pixelWriter, final float[][] zBuffer,
                                     Lighter lighter, PixelExtractor pixelExtractor, int width, int height,
                                     NormalInterpolator normalInterpolator, NormalInterpolator rayI) {
        this.pixelWriter = pixelWriter;
        this.zBuffer = zBuffer;
        this.lighter = lighter;
        this.pixelExtractor = pixelExtractor;
        this.width = width;
        this.height = height;
        this.normalInterpolator = normalInterpolator;
        this.rayI = rayI;
    }

    //векторы уже для отрисовки
    public void rasterizeTriangle(VectorDimThree point1Or,
                                  VectorDimThree point2Or,
                                  VectorDimThree point3Or) {

        //System.out.println(point1.getZ() + " " + point2.getZ() + " " + point3.getZ());
/*        VectorDimThree point1 = VectorMethods.vertexToPoint3(point1, width, height);
        VectorDimThree point2 = VectorMethods.vertexToPoint3(point2, width, height);
        VectorDimThree point3 = VectorMethods.vertexToPoint3(point3, width, height);*/
        VectorDimThree point1 = new VectorDimThree(point1Or.getV());
        VectorDimThree point2 = new VectorDimThree(point2Or.getV());
        VectorDimThree point3 = new VectorDimThree(point3Or.getV());

        sortPointsByY(point1, point2, point3);

        VectorDimThree p1ToDraw = VectorMethods.vertexToPoint3(point1, width, height);
        VectorDimThree p2ToDraw = VectorMethods.vertexToPoint3(point2, width, height);
        VectorDimThree p3ToDraw = VectorMethods.vertexToPoint3(point3, width, height);

        dr1 = p1ToDraw;
        dr2 = p2ToDraw;
        dr3 = p3ToDraw;

        int x1 = (int) p1ToDraw.getX();
        int y1 = (int) p1ToDraw.getY();
        int x2 = (int) p2ToDraw.getX();
        int y2 = (int) p2ToDraw.getY();
        int x3 = (int) p3ToDraw.getX();
        int y3 = (int) p3ToDraw.getY();


        float dx12 = calculateSideXIncrement(x1, y1, x2, y2);
        float dx13 = calculateSideXIncrement(x1, y1, x3, y3);
        float dx23 = calculateSideXIncrement(x2, y2, x3, y3);

        computeBeforeUpperPart(dx12, dx13, x1);
        drawPart(point1, point2, point3,
                y1, y2 - 1);

        computeBeforeLowerPart(dx13, dx23, x1, y1, x2, y2);
        drawPart(point1, point2, point3,
                y2, y3);
    }


    private void drawPart(VectorDimThree point1,
                          VectorDimThree point2,
                          VectorDimThree point3,
                          int leftY, int rightY) {

        for (int y = leftY; y <= rightY; y++, wx1 += dxLeft, wx2 += dxRight) {
            drawLine(point1, point2, point3, y);
        }
    }

    private void drawLine(VectorDimThree point1,
                          VectorDimThree point2,
                          VectorDimThree point3,
                          int y) {
        int yToInter;
        int xToInter;


        for (int x = (int)(wx1); x <= (int)(wx2); x++) {
            float xToCheck = DrawUtils.xToBack(x, width);
            float yToCheck = DrawUtils.yToBack(y, height);
            float z = Interpolation.interpolateZ(point1,
                    point2, point3, xToCheck, yToCheck);


            drawPixel(x, y, z, null, x, y);
        }
    }

    private void drawPixel(int x, int y, float z, Color colorM, int xToInter, int yToInter) {
        //System.out.println(x + "p" + y);
        //if (zBuffer[x][y] != Float.MAX_VALUE) return;
        //для методов возвращаемся к однородным координатам
        float xToCheck = DrawUtils.xToBack(xToInter, width);
        float yToCheck = DrawUtils.yToBack(yToInter, height);

        //System.out.println(xToCheck + " " + yToCheck);

        if (z >= 1 || z <= -1 ||
                xToCheck >= 1 || xToCheck <= -1 ||
                yToCheck >= 1 || yToCheck <= -1) return;
        if (zBuffer[x][y] <= z) return;
        //if (zBuffer[x][y] - z <= 1E-8) return;
        Color color = colorM;
        if (colorM == null) color = pixelExtractor.getPixel(xToCheck, yToCheck);;

        VectorDimThree normal = normalInterpolator.interpolate(xToCheck, yToCheck);
        VectorDimThree ray = rayI.interpolate(xToCheck, yToCheck);

        color = lighter.light(color, ray, normal);

        //System.out.println(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
/*        System.out.println(color.getGreen());
        System.out.println(color.getBlue());*/

        pixelWriter.setColor(x, y, color);
        zBuffer[x][y] = z;
    }

    private void sortPointsByY(final VectorDimThree p1, final VectorDimThree p2, final VectorDimThree p3) {
        float tempX, tempY, tempZ;
        if (p2.getY() > p1.getY()) {
            tempX = p2.getX();
            tempY = p2.getY();
            tempZ = p2.getZ();

            p2.setX(p1.getX());
            p2.setY(p1.getY());
            p2.setZ(p1.getZ());

            p1.setX(tempX);
            p1.setY(tempY);
            p1.setZ(tempZ);
        }
        if (p3.getY() > p1.getY()) {
            tempX = p3.getX();
            tempY = p3.getY();
            tempZ = p3.getZ();

            p3.setX(p1.getX());
            p3.setY(p1.getY());
            p3.setZ(p1.getZ());

            p1.setX(tempX);
            p1.setY(tempY);
            p1.setZ(tempZ);
        }
        if (p3.getY() > p2.getY()) {

            tempX = p3.getX();
            tempY = p3.getY();
            tempZ = p3.getZ();

            p3.setX(p2.getX());
            p3.setY(p2.getY());
            p3.setZ(p2.getZ());

            p2.setX(tempX);
            p2.setY(tempY);
            p2.setZ(tempZ);
        }
    }

    private void computeBeforeUpperPart(final float dx12, final float dx13, final float wx) {
        wx1 = wx;
        wx2 = wx1;

        if (dx13 < dx12) {
            dxLeft = dx13;
            dxRight = dx12;
            return;
        }
        dxLeft = dx12;
        dxRight = dx13;
    }

    private void computeBeforeLowerPart(float dx13, float dx23, int x1, int y1, int x2, int y2) {
        if (y1 == y2) {
            wx1 = x1;
            wx2 = x2;
            if (wx1 > wx2) {
                wx1 = x2;
                wx2 = x1;
            }
        }

        if (dx13 < dx23) {
            dxLeft = dx23;
            dxRight = dx13;
            return;
        }
        dxLeft = dx13;
        dxRight = dx23;
    }

    private float calculateSideXIncrement(int x1, int y1, int x2, int y2) {
        return (y1 == y2) ? 0.0F : (float) (x2 - x1) / (y2 - y1);
    }
}
