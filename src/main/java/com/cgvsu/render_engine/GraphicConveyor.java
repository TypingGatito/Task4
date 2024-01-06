package com.cgvsu.render_engine;

import com.cgvsu.math.matrix.MatrixDimFour;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;

public class GraphicConveyor {

    public static MatrixDimFour rotateScaleTranslate() {
        float[][] matrix = new float[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};
        return new MatrixDimFour(matrix);
    }

    public static MatrixDimFour lookAt(VectorDimThree eye, VectorDimThree target) {
        return lookAt(eye, target, new VectorDimThree(0F, 1.0F, 0F));
    }

    private static MatrixDimFour lookAt(VectorDimThree eye, VectorDimThree target, VectorDimThree up) {
        VectorDimThree resultX;
        VectorDimThree resultY;
        VectorDimThree resultZ;

        resultZ = VectorDimThree.subtractVector(target, eye);
        resultX = VectorDimThree.vectorMultiplyV3(up, resultZ);
        resultY = VectorDimThree.vectorMultiplyV3(resultZ, resultX);

        resultX = VectorDimThree.normalize(resultX);
        resultY = VectorDimThree.normalize(resultY);
        resultZ = VectorDimThree.normalize(resultZ);

/*        float[][] matrix = new float[][]{
                {resultX.getX(), resultY.getX(), resultZ.getX(), 0},
                {resultX.getY(), resultY.getY(), resultZ.getY(), 0},
                {resultX.getZ(), resultY.getZ(), resultZ.getZ(), 0},
                {-VectorDimThree.scaleMultiply(resultX, eye),
                        -VectorDimThree.scaleMultiply(resultY, eye),
                        -VectorDimThree.scaleMultiply(resultZ, eye), 1}};*/
        float[][] matrix = new float[][]{
                {resultX.getX(), resultX.getY(), resultX.getZ(), -VectorDimThree.scaleMultiply(resultX, eye)},
                {resultY.getX(), resultY.getY(), resultY.getZ(), -VectorDimThree.scaleMultiply(resultY, eye)},
                {resultZ.getX(), resultZ.getY(), resultZ.getZ(), -VectorDimThree.scaleMultiply(resultZ, eye)},
                {0, 0, 0, 1}};
        return new MatrixDimFour(matrix);
    }

    public static MatrixDimFour perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        MatrixDimFour result = new MatrixDimFour();
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        result.setMIJ(tangentMinusOnDegree / aspectRatio,0, 0);
        result.setMIJ(tangentMinusOnDegree,1, 1);
        result.setMIJ((farPlane + nearPlane) / (farPlane - nearPlane),2, 2);
        result.setMIJ(1.0f,3, 2);
        result.setMIJ(2 * (nearPlane * farPlane) / (nearPlane - farPlane),2, 3);
        return result;
    }
}
