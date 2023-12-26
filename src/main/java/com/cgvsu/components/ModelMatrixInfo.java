package com.cgvsu.components;

public class ModelMatrixInfo {
    //rotate
    private int angleX;
    private int angleY;
    private int angleZ;
    //scale
    private int sX;
    private int sY;
    private int sZ;
    //translate
    private int dX;
    private int dY;
    private int dZ;

    public ModelMatrixInfo() {
    }

    public ModelMatrixInfo(int angleX, int angleY, int angleZ, int sX, int sY, int sZ, int dX, int dY, int dZ) {
        this.angleX = angleX;
        this.angleY = angleY;
        this.angleZ = angleZ;
        this.sX = sX;
        this.sY = sY;
        this.sZ = sZ;
        this.dX = dX;
        this.dY = dY;
        this.dZ = dZ;
    }
    //getters

    public int getAngleX() {
        return angleX;
    }

    public int getAngleY() {
        return angleY;
    }

    public int getAngleZ() {
        return angleZ;
    }

    public int getsX() {
        return sX;
    }

    public int getsY() {
        return sY;
    }

    public int getsZ() {
        return sZ;
    }

    public int getdX() {
        return dX;
    }

    public int getdY() {
        return dY;
    }

    public int getdZ() {
        return dZ;
    }

    //setters
    public void setAngleX(int angleX) {
        this.angleX = angleX;
    }

    public void setAngleY(int angleY) {
        this.angleY = angleY;
    }

    public void setAngleZ(int angleZ) {
        this.angleZ = angleZ;
    }

    public void setsX(int sX) {
        this.sX = sX;
    }

    public void setsY(int sY) {
        this.sY = sY;
    }

    public void setsZ(int sZ) {
        this.sZ = sZ;
    }

    public void setdX(int dX) {
        this.dX = dX;
    }

    public void setdY(int dY) {
        this.dY = dY;
    }

    public void setdZ(int dZ) {
        this.dZ = dZ;
    }
}
