package com.cgvsu.math.matrix;

import com.cgvsu.math.CommonMethods;
import com.cgvsu.math.exception.IncompatibleSizesException;
import com.cgvsu.math.vector.VectorDimFour;

public class MatrixDimFour {

    public static final int SIZE = 4;
    private float[] m = new float[SIZE*SIZE];

    protected MatrixDimFour(float[] m){
        if (CommonMethods.getSize(m) == CommonMethods.getSize(this.m)) {
            for (int i = 0; i < 16; i++) {
                this.m[i] = m[i];
            }
        } else {
            throw new IncompatibleSizesException();
        }
    }

    public MatrixDimFour(float[][] m){
        int ctr = 0;
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                this.m[ctr] = m[i][j];
                ctr ++;
            }
        }
    }

    public MatrixDimFour(){
        int ctr = 0;
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                this.m[ctr] = 0f;
                ctr ++;
            }
        }
    }

    //сложение
    public static MatrixDimFour sumMatrix(MatrixDimFour m1, MatrixDimFour m2){
        return new MatrixDimFour(MatrixMethods.sumMatrix(m1.getM(), m2.getM()));
    }

    //вычитание
    public static MatrixDimFour subtractMatrix(MatrixDimFour m1, MatrixDimFour m2){
        return new MatrixDimFour(MatrixMethods.subtractMatrix(m1.getM(), m2.getM()));
    }

    //умножение на вектор4
    public static VectorDimFour mMultV(MatrixDimFour m, VectorDimFour v){
        return new VectorDimFour(MatrixMethods.mMultV(m.getM(), v.getV()));
    }

    //перемножение матриц
    public static MatrixDimFour multMatrix(MatrixDimFour m1, MatrixDimFour m2){
        return new MatrixDimFour(MatrixMethods.multMatrix(m1.getM(), m2.getM()));
    }

    //транспонирование
    public static MatrixDimFour transposition(MatrixDimFour m){
        return new MatrixDimFour(MatrixMethods.transposition(m.getM()));
    }

    //нулевая матрица
    public static MatrixDimFour zeroMatrix(){
        return new MatrixDimFour(MatrixMethods.zeroMatrix(SIZE));
    }

    public static MatrixDimFour zeroMatrix1(){
        return new MatrixDimFour(MatrixMethods.zeroMatrix1(SIZE));
    }

    //единичная матрица
    public static MatrixDimFour eyeMatrix(){
        return new MatrixDimFour(MatrixMethods.eyeMatrix(SIZE));
    }
    public static MatrixDimFour eyeMatrix1(){
        return new MatrixDimFour(MatrixMethods.eyeMatrix1(SIZE));
    }

    //вычисление определителя
    public static float getDeterminant(MatrixDimFour m){
        return MatrixMethods.getDeterminant(m.getM());
    }

    //обратная матрица
    /*public static MatrixDimFour reverseMatrix(MatrixDimFour m){
        return new MatrixDimFour(MatrixMethods.getReverseMatrix(m.getM()));
    }*/

    protected float[] getM() {
        float[] res = new float[SIZE*SIZE];
        for(int i = 0; i < SIZE*SIZE; i++){
            res[i] = m[i];
        }
        return res;
    }

    protected void setM(float[] a) {
        for(int i = 0; i < SIZE*SIZE; i++){
            m[i] = a[i];
        }
    }

    public float[][] getM1() {
        return CommonMethods.fromOneToTwo(m);
    }

    public void setMIJ(float a, int i, int j){
        this.m[i*SIZE+j] = a;
    }

    public float getMIJ(int i, int j){
        return this.m[i*SIZE+j];
    }
}
