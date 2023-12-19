package com.cgvsu.math.matrix;

import com.cgvsu.math.CommonMethods;

public class MatrixMethods {

    //сложение
    public static float[] sumMatrix(float[] m1, float[] m2){
        int size = CommonMethods.getSize(m1);
        float[] m = new float[size*size];
        for(int i = 0; i < m.length; i++){
            m[i] = m1[i] + m2[i];
        }
        return m;
    }

    //вычитание
    public static float[] subtractMatrix(float[] m1, float[] m2){
        int size = CommonMethods.getSize(m1);
        float[] m = new float[size*size];
        for(int i = 0; i < m.length; i++){
            m[i] = m1[i] - m2[i];
        }
        return m;
    }

    //умножение на вектор
    public static float[] mMultV(float[] m, float[] v){
        int size = CommonMethods.getSize(m);
        float[] res = new float[size];
        int mem = 0;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                res[i] += v[j]*m[mem];
                mem++;
            }
        }
        return res;
    }

    //перемножение матриц
    public static float[] multMatrix(float[] m1, float[] m2){
        int size = CommonMethods.getSize(m1);
        float[] res = new float[size*size];
        float[] m3 = MatrixMethods.transposition(m2);
        int mem = 0, memA = 0, mem2 = 0;
        for (int i = 0; i < res.length; i++){
            for (int j = mem; j < mem + size; j++) {
                res[i] += m1[memA] * m3[j];
                memA++;
            }
            mem += size;
            if ((i+1) % size == 0){
                mem = 0;
                mem2 += size;
            }
            memA = mem2;
        }
        return res;
    }

    //транспонирование
    /*public static float[] transposition(float[] m){
        int size = CommonMethods.getSize(m);
        float[] res = new float[size*size];
        //мап где ключ остаток, а значение - то место, куда будет ставиться элем. с соотв. остатком коэффициента
        //HashMap<Integer, Integer> move = new HashMap<>();
        int[] move1 = new int[size];
        int mem = 0, indexMem = 0;
        //массив со сдвигами для коэффициентов
        int[] move = new int[size];
        for (int i = 0; i < size; i++){
            move1[i] = i%size;
            //move.put(i%size, mem);
            //mem += size;
        }
        for (int i = 0; i < size*size; i++){
            indexMem = i%size;
            res[move1[indexMem]] = m[i];
            move1[indexMem]++;
            //res[move.get(indexMem)] = m[i];
            //mem = move.get(indexMem);
            //move.remove(indexMem);
            //move.put(indexMem, mem + 1);
        }
        return res;
    }*/

    public static float[] transposition(float[] m){
        int size = CommonMethods.getSize(m);
        float[] res = new float[size*size];
        int[] move1 = new int[size];
        int mem = 0, indexMem = 0;
        for (int i = 0; i < size; i++){
            move1[i] = mem;
            mem += size;
        }
        for (int i = 0; i < size*size; i++){
            indexMem = i%size;
            res[move1[indexMem]] = m[i];
            move1[indexMem]++;
        }
        return res;
    }

    //нулевая матрица
    public static float[] zeroMatrix(int size){
        return new float[size*size];
    }

    //единичная матрица
    public static float[] eyeMatrix(int size){
        float[] res = new float[size*size];
        int mem = 0;
        for(int i = 0; i < res.length; i++){
            if (i == mem){
                res[i] = 1;
                mem += size + 1;
            } else {
                res[i] = 0;
            }
        }
        return res;
    }

    public static float[][] eyeMatrix1(int size){
        float[][] res = new float[size][size];
        int mem = 0;
        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                if (i == j) res[i][j] = 1;
            }
        }
        return res;
    }

    public static float[][] zeroMatrix1(int size){
        return new float[size][size];
    }

    //вычисление определителя
    public static float getDeterminant(float[] m){
        float determinant = 0;
        if (m.length == 4){
            determinant = m[0]*m[3] - m[1]*m[2];
        }
        else {
            for (int i = 0; i < CommonMethods.getSize(m); i++){
                determinant += (float) (Math.pow(-1, i)*m[i]*getDeterminant(cropMatrix(m, i)));
            }
        }
        return determinant;
    }

    private static float[] cropMatrix(float[] m, int k){
        int size = CommonMethods.getSize(m), mem = 0;
        float[] res = new float[size*size - (size*2 - 1)];
        for (int i = 0; i < size*size; i++){
            if (i >= size && i%size != k%size){
                res[mem] = m[i];
                mem++;
            }
        }
        return res;
    }

    private static float[] getStartAndFinish(float a, float size){
        float[] res = new float[2];
        int ctr = 0;
        float mem = a;
        return res;
    }

    //вычисление обратной матрицы
    public static float[] getReverseMatrix(float[] m){
        if (!CommonMethods.isZero(getDeterminant(m))) {
            return multOnScale(transposition(m), 1/getDeterminant(m));
        } else {
            throw new ArithmeticException();
        }
    }

    public static float[] multOnScale(float[] m, float a){
        float[] res = new float[m.length];
        for (int i = 0; i < m.length; i++){
            res[i] = m[i]/a;
        }
        return res;
    }

    public static float[] getMinorMatrix(float[] m){
        float[] res = new float[m.length];
        for (int i = 0; i < m.length; i++){
            res[i] = getDeterminant(cropMatrix(m, i));
        }
        return res;
    }

}
