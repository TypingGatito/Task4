package com.cgvsu.infoclasses;

public class ModelMatrixInfo {
    //rotate
    private float angleX;
    private float angleY;
    private float angleZ;
    //scale
    private float sX;
    private float sY;
    private float sZ;
    //translate
    private float dX;
    private float dY;
    private float dZ;

    public ModelMatrixInfo() {
    }

    public ModelMatrixInfo(final int angleX, final int angleY, final int angleZ,
                           final int sX, final int sY, final int sZ,
                           final int dX, final int dY, final int dZ) {
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

    //outer methods
    public void increaseAngleX(final float angleX) {
        this.angleX += angleX;
    }

    public void increaseAngleY(final float angleY) {
        this.angleY += angleY;
    }

    public void increaseAngleZ(final float angleZ) {
        this.angleZ += angleZ;
    }

    public void increaseSX(final float sX) {
        this.sX += sX;
    }

    public void increaseSY(final float sY) {
        this.sY += sY;
    }

    public void increaseSZ(final float sZ) {
        this.sZ += sZ;
    }

    public void increaseDX(final float dX) {
        this.dX += dX;
    }

    public void increaseDY(final float dY) {
        this.dY += dY;
    }

    public void increaseDZ(final float dZ) {
        this.dZ += dZ;
    }
    //getters
    public float getAngleX() {
        return angleX;
    }

    public float getAngleY() {
        return angleY;
    }

    public float getAngleZ() {
        return angleZ;
    }

    public float getsX() {
        return sX;
    }

    public float getsY() {
        return sY;
    }

    public float getsZ() {
        return sZ;
    }

    public float getdX() {
        return dX;
    }

    public float getdY() {
        return dY;
    }

    public float getdZ() {
        return dZ;
    }

    //setters
    public void setAngleX(final float angleX) {
        this.angleX = angleX;
    }

    public void setAngleY(final float angleY) {
        this.angleY = angleY;
    }

    public void setAngleZ(final float angleZ) {
        this.angleZ = angleZ;
    }

    public void setsX(final float sX) {
        this.sX = sX;
    }

    public void setsY(final float sY) {
        this.sY = sY;
    }

    public void setsZ(final float sZ) {
        this.sZ = sZ;
    }

    public void setdX(final float dX) {
        this.dX = dX;
    }

    public void setdY(final float dY) {
        this.dY = dY;
    }

    public void setdZ(final float dZ) {
        this.dZ = dZ;
    }
}
