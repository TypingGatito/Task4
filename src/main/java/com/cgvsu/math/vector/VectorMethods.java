package com.cgvsu.math.vector;

import com.cgvsu.math.CommonMethods;
import com.cgvsu.math.matrix.MatrixDimFour;

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
    public static VectorDimThree multiplyMatrix4ByVector3(final MatrixDimFour matrix, final VectorDimThree vertex) {
        /*final float x = (vertex.getX() * matrix.getMIJ(0, 0)) + (vertex.getY() * matrix.getMIJ(1, 0)) + (vertex.getZ() * matrix.getMIJ(2, 0)) + matrix.getMIJ(3, 0);
        final float y = (vertex.getX() * matrix.getMIJ(0, 1)) + (vertex.getY() * matrix.getMIJ(1, 1)) + (vertex.getZ() * matrix.getMIJ(2, 1)) + matrix.getMIJ(3, 1);
        final float z = (vertex.getX() * matrix.getMIJ(0, 2)) + (vertex.getY() * matrix.getMIJ(1, 2)) + (vertex.getZ() * matrix.getMIJ(2, 2)) + matrix.getMIJ(3, 2);
        final float w = (vertex.getX() * matrix.getMIJ(0, 3)) + (vertex.getY() * matrix.getMIJ(1, 3)) + (vertex.getZ() * matrix.getMIJ(2, 3)) + matrix.getMIJ(3, 3);*/
        final float x = (vertex.getX() * matrix.getMIJ(0, 0)) + (vertex.getY() * matrix.getMIJ(0, 1)) + (vertex.getZ() * matrix.getMIJ(0, 2)) + matrix.getMIJ(0, 3);
        final float y = (vertex.getX() * matrix.getMIJ(1, 0)) + (vertex.getY() * matrix.getMIJ(1, 1)) + (vertex.getZ() * matrix.getMIJ(1, 2)) + matrix.getMIJ(1, 3);
        final float z = (vertex.getX() * matrix.getMIJ(2, 0)) + (vertex.getY() * matrix.getMIJ(2, 1)) + (vertex.getZ() * matrix.getMIJ(2, 2)) + matrix.getMIJ(2, 3);
        final float w = (vertex.getX() * matrix.getMIJ(3, 0)) + (vertex.getY() * matrix.getMIJ(3, 1)) + (vertex.getZ() * matrix.getMIJ(3, 2)) + matrix.getMIJ(3, 3);
        return new VectorDimThree(x / w, y / w, z / w);
    }

    public static VectorDimTwo vertexToPoint(final VectorDimThree vertex, final int width, final int height) {
/*        return new VectorDimTwo(vertex.getX() * width + width / 2.0F,
                -vertex.getY() * height + height / 2.0F);*/
        return new VectorDimTwo(vertex.getX() * (width - 1)/2F + (width-1) / 2.0F,
                vertex.getY() * (1-height)/2F + (height-1) / 2.0F);
    }
    public static VectorDimThree vertexToPoint3(final VectorDimThree vertex, final int width, final int height) {
/*        return new VectorDimThree(vertex.getX() * width + width / 2.0F,
                -vertex.getY() * height + height / 2.0F, vertex.getZ());*/
        return new VectorDimThree(vertex.getX() * (width - 1)/2F + (width-1) / 2.0F,
                vertex.getY() * (1-height)/2F + (height-1) / 2.0F, vertex.getZ());
    }
}
