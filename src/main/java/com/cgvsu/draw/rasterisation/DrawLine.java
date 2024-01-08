package com.cgvsu.draw.rasterisation;

import com.cgvsu.draw.DrawUtils;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import static java.lang.Math.abs;

public class DrawLine {
    public static void BR(int x0, int y0, int x1, int y1, PixelWriter pixelWriter){
        if (x0 > x1) {
            int a = x1;
            x1 = x0;
            x0 = a;
            a = y1;
            y1 = y0;
            y0 = a;
        }
        int deltax = abs(x1-x0);
        int deltay = abs(y1 - y0);
        int error = 0;
        int deltaerr = (deltay + 1);
        int y = y0;
        int diry = y1 - y0;
        if (diry > 0) {
            diry=1;
        }
        if (diry < 0) {
            diry=-1;
        }
        for (int x = x0; x <= x1; x++) {
            pixelWriter.setColor(x, y, Color.RED);
            error+=deltaerr;
            if (error >= (deltax + 1)) {
                y=y + diry;
                error=error - (deltax + 1);
            }
        }
    }

    public static void DDA(int x1, int y1, double z1,
                           int x2, int y2, double z2,
                           Color c1, Color c2,
                           PixelWriter pixelWriter,
                           float[][] zBuffer){
        double r1 = 255*c1.getRed();
        double g1 = 255*c1.getGreen();
        double b1 = 255*c1.getBlue();

        double r2 = 255*c2.getRed();
        double g2 = 255*c2.getGreen();
        double b2 = 255*c2.getBlue();


        double r;
        double g;
        double b;
        double cur;
        double z;


        double dx = x2 - x1;
        double dy = y2 - y1;
        double step = Math.max(abs(dx), abs(dy));
        dx = dx/step;
        dy = dy/step;
        double x = x1;
        double y = y1;

        for (int i = 0; i <step; i++) {
            cur = Math.pow((x - x1)*(x - x1) + (y - y1)*(y - y1), 0.5)/
                    Math.pow((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1), 0.5);
            r = cur * (r2 - r1) + r1;
            g = cur * (g2 - g1) + g1;
            b = cur * (b2 - b1) + b1;

            z = cur * (z2 - z1) + z1;

            pixelWriter.setColor((int)Math.round(x), (int)Math.round(y), Color.rgb((int)Math.round(r),
                    (int)Math.round(g), (int)Math.round(b)));
            x += dx;
            y += dy;
        }
    }

    public static void drawLine(float[][] zBuffer,
                                int x0, int y0, double z0,
                                int x1, int y1, double z1,
                                PixelWriter pixelWriter, Color color,
                                int width, int height) {
        int x = x0;
        int y = y0;

        int dx = Math.abs(x1 - x);
        int dy = Math.abs(y1 - y);

        int sx = x < x1 ? 1 : -1;
        int sy = y < y1 ? 1 : -1;

        int err = dx-dy;
        int e2;

        double cur;
        float z;

        while (true)
        {
/*
            float xCur = DrawUtils.xToBack(x, width);
            float yCur = DrawUtils.yToBack(y, height);
            float x0Cur = DrawUtils.xToBack(x0, width);
            float y0Cur = DrawUtils.yToBack(y0, height);
            float x1Cur = DrawUtils.xToBack(x1, width);
            float y1Cur = DrawUtils.yToBack(y1, height);
            cur = Math.pow((xCur - x0Cur)*(xCur - x0Cur) + (yCur - y0Cur)*(yCur - y0Cur), 0.5)/
                    Math.pow((x1Cur - x0Cur)*(x1Cur - x0Cur) + (y1Cur - y0Cur)*(y1Cur - y0Cur), 0.5);
*/

            cur = Math.pow((x - x0)*(x - x0) + (y - y0)*(y - y0), 0.5)/
                    Math.pow((x1 - x0)*(x1 - x0) + (y1 - y0)*(y1 - y0), 0.5);

            if (x < 0 || y < 0 || x >= width || y >= height) break;
            z = (float) (cur * (z1 - z0) + z0);

            if ((zBuffer[x][y] > z - 1E-5)) {
                pixelWriter.setColor(x, y, color);
                zBuffer[x][y] = z;
            }


            if (x == x1 && y == y1) {
                break;
            }

            e2 = 2 * err;
            if (e2 > -dy)
            {
                err = err - dy;
                x = x + sx;
            }

            if (e2 < dx)
            {
                err = err + dx;
                y = y + sy;
            }
        }
    }

}
