package com.cgvsu.math.vector;

import com.cgvsu.math.CommonMethods;
import com.cgvsu.math.exception.IncompatibleSizesException;

public class VectorDimFour implements Vectorable{
    public final int SIZE = 4;
    private float[] v = new float[SIZE];

    public VectorDimFour(float[] v){
        if (CommonMethods.getSize(v) == CommonMethods.getSize(this.v)) {
            this.v[0] = v[0];
            this.v[1] = v[1];
            this.v[2] = v[2];
            this.v[3] = v[3];
        } else {
            throw new IncompatibleSizesException();
        }
    }

    public VectorDimFour(float x, float y, float z, float w){
        this.v[0] = x;
        this.v[1] = y;
        this.v[2] = z;
        this.v[3] = w;
    }

    public VectorDimFour(){
        this.v[0] = 0;
        this.v[1] = 0;
        this.v[2] = 0;
        this.v[3] = 0;
    }

    //сложение
    public static VectorDimFour sumVector(VectorDimFour v1, VectorDimFour v2){
        return new VectorDimFour(VectorMethods.sumVector(v1.getV(), v2.getV()));
    }

    //вычитание
    public static VectorDimFour subtractVector(VectorDimFour v1, VectorDimFour v2){
        return new VectorDimFour(VectorMethods.subtractVector(v1.getV(), v2.getV()));
    }

    //умножение на скаляр
    public static VectorDimFour multiplyByScalar(float scalar, VectorDimFour v){
        return new VectorDimFour(VectorMethods.multiplyByScalar(scalar, v.getV()));
    }

    //деление на скаляр
    public static VectorDimFour divideByScalar(float scalar, VectorDimFour v){
        return new VectorDimFour(VectorMethods.divideByScalar(scalar, v.getV()));
    }

    //вычисление длины
    public static float getVectorLength(VectorDimFour v){
        return VectorMethods.getVectorLength(v.getV());
    }

    //нормализация
    public static VectorDimFour normalize(VectorDimFour v){
        return new VectorDimFour(VectorMethods.normalize(v.getV()));
    }

    //скалярное произведение
    public static float scaleMultiply(VectorDimFour v1, VectorDimFour v2){
        return VectorMethods.scaleMultiply(v1.getV(), v2.getV());
    }

    //деление на w + переведение в трёхмерный
    //if w = 0 --> ok
    public static VectorDimThree normalizeByW(VectorDimFour v){
        if (!CommonMethods.isZero(v.getV()[3])){
            return new VectorDimThree(new float[]{v.getV()[0]/v.getV()[3], v.getV()[1]/v.getV()[3], v.getV()[2]/v.getV()[3]});
        } else {
            return new VectorDimThree(new float[]{v.getX(), v.getY(), v.getZ()});
        }
    }

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

    //для работы по Х и У и Z и W
    public float getX(){
        return v[0];
    }

    public float getY(){
        return v[1];
    }

    public float getZ(){
        return v[2];
    }

    public float getW(){
        return v[3];
    }

    public void setX(float a){
        v[0] = a;
    }

    public void setY(float a){
        v[1] = a;
    }

    public void setZ(float a){
        v[2] = a;
    }

    public void setW(float a){
        v[3] = a;
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
