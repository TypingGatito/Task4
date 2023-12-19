package com.cgvsu.math;

public class CommonMethods {

    //определение размера
    public static int getSize(float[] a){
        if (a.length > 4){
            return (int) (a.length/Math.sqrt(a.length));
        } else {
            return a.length;
        }
    }

    //печать матрицы в классическом виде
    public static void printMatrix(float[] m){
        int ctr = 0, size = getSize(m);
        for (float v : m) {
            System.out.print(v + " ");
            ctr++;
            if (ctr % size == 0) System.out.println();
        }
    }

    //сравнение с нулём через эпсилон
    public static boolean isZero(float a){
        return Math.abs(a) < 1E-7;
    }

    public static float[][] fromOneToTwo(float[] a){
        int size = getSize(a);
        float[][] res = new float[size][size];
        int ctr = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                res[i][j] = a[ctr];
                ctr++;
            }
        }
        return res;
    }
}
