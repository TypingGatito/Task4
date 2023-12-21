package com.cgvsu.math.matrix;

import com.cgvsu.math.CommonMethods;
import com.cgvsu.math.exception.IncompatibleSizesException;
import com.cgvsu.math.vector.VectorDimThree;

public class MatrixDimThree {

    private static final int SIZE = 3;
    private float[] m = new float[SIZE*SIZE];

    protected MatrixDimThree(float[] m){
        if (CommonMethods.getSize(m) == CommonMethods.getSize(this.m)) {
            for(int i = 0; i < SIZE*SIZE; i++) {
                this.m[i] = m[i];
            }
        } else {
            throw new IncompatibleSizesException();
        }
    }

    public MatrixDimThree(float[][] m){
        int ctr = 0;
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                this.m[ctr] = m[i][j];
                ctr ++;
            }
        }
    }

    public MatrixDimThree(){
        int ctr = 0;
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                this.m[ctr] = 0f;
                ctr ++;
            }
        }
    }

    //сложение
    public static MatrixDimThree sumMatrix(MatrixDimThree m1, MatrixDimThree m2){
        return new MatrixDimThree(MatrixMethods.sumMatrix(m1.getM(), m2.getM()));
    }

    //вычитание
    public static MatrixDimThree subtractMatrix(MatrixDimThree m1, MatrixDimThree m2){
        return new MatrixDimThree(MatrixMethods.subtractMatrix(m1.getM(), m2.getM()));
    }

    //умножение на вектор3
    public static VectorDimThree mMultV(MatrixDimThree m, VectorDimThree v){
        return new VectorDimThree(MatrixMethods.mMultV(m.getM(), v.getV()));
    }

    //перемножение матриц
    public static MatrixDimThree multMatrix(MatrixDimThree m1, MatrixDimThree m2){
        return new MatrixDimThree(MatrixMethods.multMatrix(m1.getM(), m2.getM()));
    }

    //транспонирование
    public static MatrixDimThree transposition(MatrixDimThree m){
        return new MatrixDimThree(MatrixMethods.transposition(m.getM()));
    }

    //нулевая матрица
    public static MatrixDimThree zeroMatrix(){
        return new MatrixDimThree(MatrixMethods.zeroMatrix(SIZE));
    }

    public static MatrixDimThree zeroMatrix1(){
        return new MatrixDimThree(MatrixMethods.zeroMatrix1(SIZE));
    }

    //единичная матрица
    public static MatrixDimThree eyeMatrix(){
        return new MatrixDimThree(MatrixMethods.eyeMatrix(SIZE));
    }

    public static MatrixDimThree eyeMatrix1(){
        return new MatrixDimThree(MatrixMethods.eyeMatrix1(SIZE));
    }

    //вычисление определителя
    public static float getDeterminant(MatrixDimThree m){
        return MatrixMethods.getDeterminant(m.getM());
    }

    //обратная матрица
    /*public static MatrixDimThree reverseMatrix(MatrixDimThree m){
        return new MatrixDimThree(MatrixMethods.getReverseMatrix(m.getM()));
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
