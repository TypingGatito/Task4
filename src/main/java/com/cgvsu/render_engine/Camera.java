package com.cgvsu.render_engine;

import com.cgvsu.math.matrix.MatrixDimFour;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimFour;

public class Camera {

    private VectorDimThree position;
    private VectorDimThree target;
    private float fov;
    private float aspectRatio;
    private float nearPlane;
    private float farPlane;

    public Camera(
            final VectorDimThree position,
            final VectorDimThree target,
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void setPosition(final VectorDimThree position) {
        this.position = position;
    }

    public void setTarget(final VectorDimThree target) {
        this.target = target;
    }

    public void setAspectRatio(final float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public VectorDimThree getPosition() {
        return position;
    }

    public VectorDimThree getTarget() {
        return target;
    }

    //весьма сомнительная операция - перемещение позиции
    public void movePosition(final VectorDimThree translation) {
        VectorDimFour v4 = new VectorDimFour(position.getX(), position.getY(), position.getZ(), 1);
        MatrixDimFour m4 = new MatrixDimFour(new float[][]{{1, 0, 0, translation.getX()},
                {0, 1, 0, translation.getY()},
                {0, 0, 1, translation.getZ()},
                {0, 0, 0, 1}});
        this.position = VectorDimFour.normalizeByW(MatrixDimFour.mMultV(m4, v4));
    }

    //перемещение объекта
    public void moveTarget(final VectorDimThree translation) {
        VectorDimFour v4 = new VectorDimFour(target.getX(), target.getY(), target.getZ(), 1);
        MatrixDimFour m4 = new MatrixDimFour(new float[][]{{1, 0, 0, translation.getX()},
                {0, 1, 0, translation.getY()},
                {0, 0, 1, translation.getZ()},
                {0, 0, 0, 1}});
        this.target = VectorDimFour.normalizeByW(MatrixDimFour.mMultV(m4, v4));
    }

    public MatrixDimFour getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public MatrixDimFour getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

}