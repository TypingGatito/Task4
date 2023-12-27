package com.cgvsu.draw.rasterisation;

import com.cgvsu.math.vector.VectorDimTwo;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;


public class TriangleRasterisation {
    private PixelWriter pixelWriter;
    private float wx1;
    private float wx2;

    private float dxLeft;
    private float dxRight;

    public TriangleRasterisation(PixelWriter pixelWriter) {
        this.pixelWriter = pixelWriter;
    }

    public void rasterizeTriangle(VectorDimTwo point1, Color color1,
                                  VectorDimTwo point2, Color color2,
                                  VectorDimTwo point3, Color color3) {

        PointWithColor p1 = new PointWithColor(point1, color1);
        PointWithColor p2 = new PointWithColor(point2, color2);
        PointWithColor p3 = new PointWithColor(point3, color3);

        sortVectorsByY(p1, p2, p3);

        System.out.println(p1.point.getY() + " " + p2.point.getY() + " "+p3.point.getY() + " ");

        int x1 = (int) p1.point.getX();
        int y1 = (int) p1.point.getY();
        int x2 = (int) p2.point.getX();
        int y2 = (int) p2.point.getY();
        int x3 = (int) p3.point.getX();
        int y3 = (int) p3.point.getY();

        float dx12 = calculateSideXIncrement(x1, y1, x2, y2);
        float dx13 = calculateSideXIncrement(x1, y1, x3, y3);
        float dx23 = calculateSideXIncrement(x2, y2, x3, y3);

        computeBeforeUpperPart(dx12, dx13, x1);
        drawPart(p1.point, p1.color,
                p2.point, p2.color,
                p3.point, p3.color,
                y1, y2 - 1);

        computeBeforeLowerPart(dx13, dx23, x1, y1, x2, y2);
        drawPart(p1.point, p1.color,
                p2.point, p2.color,
                p3.point, p3.color,
                y2, y3);
    }

    private void drawPixel(VectorDimTwo point1, Color color1,
                           VectorDimTwo point2, Color color2,
                           VectorDimTwo point3, Color color3,
                           int x, int y) {

        pixelWriter.setArgb(x, y, Interpolation.interpolateARGB(point1, color1,
                point2, color2,
                point3, color3,
                new VectorDimTwo(x, y)));
    }
    private class PointWithColor {
        public PointWithColor(VectorDimTwo point, Color color) {
            this.point = point;
            this.color = color;
        }

        public VectorDimTwo point;
        public Color color;
    }

    private void sortVectorsByY(final PointWithColor p1, final PointWithColor p2, final PointWithColor p3) {
        VectorDimTwo tempP;
        Color tempC;
        if (p2.point.getY() < p1.point.getY()) {
            tempP = p2.point;
            tempC = p2.color;

            p2.point = p1.point;
            p2.color = p1.color;

            p1.point = tempP;
            p1.color = tempC;
        }
        if (p3.point.getY() < p1.point.getY()) {
            tempP = p3.point;
            tempC = p3.color;

            p3.point = p1.point;
            p3.color = p1.color;
            p1.point = tempP;
            p1.color = tempC;

        }
        if (p3.point.getY() < p2.point.getY()) {
            tempP = p3.point;
            tempC = p3.color;

            p3.point = p2.point;
            p3.color = p2.color;
            p2.point = tempP;
            p2.color = tempC;

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

    private void drawPart(VectorDimTwo point1, Color color1,
                          VectorDimTwo point2, Color color2,
                          VectorDimTwo point3, Color color3,
                          int leftY, int rightY) {
        for (int y = leftY; y <= rightY; y++, wx1 += dxLeft, wx2 += dxRight) {
            drawLine(point1, color1, point2, color2, point3, color3, y);
        }
    }

    private void drawLine(VectorDimTwo point1, Color color1,
                          VectorDimTwo point2, Color color2,
                          VectorDimTwo point3, Color color3,
                          int y) {
        for (int x = (int)(wx1); x <= (int)(wx2); x++) {
            drawPixel(point1, color1, point2, color2, point3, color3, x, y);
        }
    }

}
