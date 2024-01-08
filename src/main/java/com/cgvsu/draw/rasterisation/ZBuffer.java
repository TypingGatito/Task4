package com.cgvsu.draw.rasterisation;

public class ZBuffer {

    public static void setBig(float[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = Float.MAX_VALUE;
            }
        }
    }

}
