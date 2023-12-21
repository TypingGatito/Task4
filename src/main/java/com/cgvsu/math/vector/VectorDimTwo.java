package com.cgvsu.math.vector;

import com.cgvsu.math.CommonMethods;
import com.cgvsu.math.exception.IncompatibleSizesException;

public class VectorDimTwo implements Vectorable {
    private final int SIZE  = 2;
    private float[] v = new float[SIZE];

    public VectorDimTwo(float[] v){
        if (CommonMethods.getSize(v) == CommonMethods.getSize(this.v)) {
            this.v[0] = v[0];
            this.v[1] = v[1];
        } else {
            throw new IncompatibleSizesException();
        }
    }

    public VectorDimTwo(float x, float y){
        this.v[0] = x;
        this.v[1] = y;
    }

    public VectorDimTwo(){
        this.v[0] = 0;
        this.v[1] = 0;
    }

    //сложение
    public static VectorDimTwo sumVector(VectorDimTwo v1, VectorDimTwo v2){
        return new VectorDimTwo(VectorMethods.sumVector(v1.getV(), v2.getV()));
    }

    //вычитание
    public static VectorDimTwo subtractVector(VectorDimTwo v1, VectorDimTwo v2){
        return new VectorDimTwo(VectorMethods.subtractVector(v1.getV(), v2.getV()));
    }

    //умножение на скаляр
    public static VectorDimTwo multiplyByScalar(float scalar, VectorDimTwo v){
        return new VectorDimTwo(VectorMethods.multiplyByScalar(scalar, v.getV()));
    }

    //деление на скаляр
    public static VectorDimTwo divideByScalar(float scalar, VectorDimTwo v){
        return new VectorDimTwo(VectorMethods.divideByScalar(scalar, v.getV()));
    }

    //вычисление длины
    public static float getVectorLength(VectorDimTwo v){
        return VectorMethods.getVectorLength(v.getV());
    }

    //нормализация
    public static VectorDimTwo normalize(VectorDimTwo v){
        return new VectorDimTwo(VectorMethods.normalize(v.getV()));
    }

    //скалярное произведение
    public static float scaleMultiply(VectorDimTwo v1, VectorDimTwo v2){
        return VectorMethods.scaleMultiply(v1.getV(), v2.getV());
    }

    //для внутренней работы
    public float[] getV() {
        float[] res = new float[SIZE];
        for(int i = 0; i < SIZE; i++){
            res[i] = v[i];
        }
        return res;
    }

    public void setV(float[] v) {
        for(int i = 0; i < SIZE; i++){
            this.v[i] = v[i];
        }
    }

    //для работы по индексу
    public float getVI(int i){
        return v[i];
    }

    public void setVI(float a, int i){
        v[i] = a;
    }

    //для работы по Х и У
    public float getX(){
        return v[0];
    }

    public float getY(){
        return v[1];
    }

    public void setX(float a){
        v[0] = a;
    }

    public void setY(float a){
        v[1] = a;
    }

    //реализация интерфейса vectorable
    @Override
    public int size() {
        return SIZE;
    }

    @Override
    public float get(int i) {
        return getVI(i);
    }

    @Override
    public void set(int i, float val) {
        setVI(val, i);
    }
}
