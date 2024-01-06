package com.cgvsu.draw.rasterisation;

public class ZBuffer {
    private float[][] zBuffer;

    public ZBuffer(int width, int height) {
        this.zBuffer = new float[width][height];
        setBig(zBuffer);
    }
    public static void setBig(float[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = Float.MAX_VALUE;
            }
        }
    }

    public void setByXY(int x, int y, float z) {
        zBuffer[x][y] = z;
    }
    public float[][] getzBuffer() {
        return zBuffer;
    }
}
