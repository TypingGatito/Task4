package com.cgvsu.math.vector;

import com.cgvsu.math.CommonMethods;
import com.cgvsu.math.exception.IncompatibleSizesException;

public class VectorDimThree implements Vectorable{
    private final int SIZE = 3;
    private float[] v = new float[SIZE];

    public VectorDimThree(float[] v){
        if (CommonMethods.getSize(v) == CommonMethods.getSize(this.v)) {
            this.v[0] = v[0];
            this.v[1] = v[1];
            this.v[2] = v[2];
        } else {
            throw new IncompatibleSizesException();
        }
    }

    public VectorDimThree(float x, float y, float z){
        this.v[0] = x;
        this.v[1] = y;
        this.v[2] = z;
    }

    public VectorDimThree(){
        this.v[0] = 0;
        this.v[1] = 0;
        this.v[2] = 0;
    }

    //сложение
    public static VectorDimThree sumVector(VectorDimThree v1, VectorDimThree v2){
        return new VectorDimThree(VectorMethods.sumVector(v1.getV(), v2.getV()));
    }

    //вычитание
    public static VectorDimThree subtractVector(VectorDimThree v1, VectorDimThree v2){
        return new VectorDimThree(VectorMethods.subtractVector(v1.getV(), v2.getV()));
    }

    //умножение на скаляр
    public static VectorDimThree multiplyByScalar(float scalar, VectorDimThree v){
        return new VectorDimThree(VectorMethods.multiplyByScalar(scalar, v.getV()));
    }

    //деление на скаляр
    public static VectorDimThree divideByScalar(float scalar, VectorDimThree v){
        return new VectorDimThree(VectorMethods.divideByScalar(scalar, v.getV()));
    }

    //вычисление длины
    public static float getVectorLength(VectorDimThree v){
        return VectorMethods.getVectorLength(v.getV());
    }

    //нормализация
    public static VectorDimThree normalize(VectorDimThree v){
        return new VectorDimThree(VectorMethods.normalize(v.getV()));
    }

    //скалярное произведение
    public static float scaleMultiply(VectorDimThree v1, VectorDimThree v2){
        return VectorMethods.scaleMultiply(v1.getV(), v2.getV());
    }

    //векторное произведение (для вектора размерности 3)
    /*
     i    j    k
    v1.X v1.Y v1.Z
    v2.X v2.Y v2.Z
    vecA * vecB = v1.Y*v2.Z - v1.Z*v2.Y, v1.Z*v2.X - v1.X*v2.Z, v1.X*v2.Y - v1.Y*v2.X;
    */
    public static VectorDimThree vectorMultiplyV3(VectorDimThree v1, VectorDimThree v2){
        return new VectorDimThree(new float[]{v1.getV()[1]*v2.getV()[2] - v1.getV()[2]*v2.getV()[1],
                v1.getV()[2]*v2.getV()[0] - v1.getV()[0]*v2.getV()[2],
                v1.getV()[0]*v2.getV()[1] - v1.getV()[1]*v2.getV()[0]});
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

    //для работы по Х и У и Z
    public float getX(){
        return v[0];
    }

    public float getY(){
        return v[1];
    }

    public float getZ(){
        return v[2];
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
    @Override
    public boolean equals(Object v2){
        final float eps = 1e-5f;
        if(!(v2 instanceof VectorDimThree vv2)) return false;
        //return true;
        return Math.abs(vv2.getX() - this.getX()) < eps &&
                Math.abs(vv2.getY() - this.getY()) < eps &&
                Math.abs(vv2.getZ() - this.getZ()) < eps;
    }

    public String toStr() {
        return getX() + " " + getY() + " " + getZ();
    }
}
