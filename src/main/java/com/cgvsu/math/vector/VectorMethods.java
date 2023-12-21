package com.cgvsu.math.vector;

import com.cgvsu.math.CommonMethods;

public class VectorMethods {

        //сложение
    public static float[] sumVector(float[] v1, float[] v2){
        float[] res = new float[CommonMethods.getSize(v1)];
        for(int i = 0; i < res.length; i++){
            res[i] = v1[i] + v2[i];
        }
        return res;
    }

    //вычитание
    public static float[] subtractVector(float[] v1, float[] v2){
        float[] res = new float[CommonMethods.getSize(v1)];
        for(int i = 0; i < res.length; i++){
            res[i] = v1[i] - v2[i];
        }
        return res;
    }

    //умножение на скаляр
    public static float[] multiplyByScalar(float scal, float[] v){
        float[] res = new float[CommonMethods.getSize(v)];
        for(int i = 0; i < res.length; i++){
            res[i] = v[i]*scal;
        }
        return res;
    }

    //деление на скаляр
    public static float[] divideByScalar(float scal, float[] v){
        float[] res = new float[CommonMethods.getSize(v)];
        if (!CommonMethods.isZero(scal)) {
            for (int i = 0; i < res.length; i++) {
                res[i] = v[i] / scal;
            }
        } else {
            throw new ArithmeticException();
        }
        return res;
    }

    //вычисление длины
    public static float getVectorLength(float[] v){
        float res = 0;
        for (float value : v) {
            res += (float) Math.pow(value, 2);
        }
        return (float) Math.pow(res, 0.5);
    }

    //нормализация
    public static float[] normalize(float[] v){
        if (getVectorLength(v) == 0) {
            return v;
        } else {
            return divideByScalar(getVectorLength(v), v);
        }
    }

    //скалярное произведение
    public static float scaleMultiply(float[] v1, float[] v2){
        float res = 0;
        for (int i = 0; i < v1.length; i++){
            res += v1[i]*v2[i];
        }
        return res;
    }
}
