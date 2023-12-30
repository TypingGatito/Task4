package com.cgvsu.math.affinetransformations;

import com.cgvsu.math.matrix.MatrixDimFour;
import com.cgvsu.math.matrix.MatrixDimThree;
import com.cgvsu.math.vector.VectorDimFour;
import com.cgvsu.math.vector.VectorDimThree;

public class AffineTransformations {
    //SCALE
    private static MatrixDimFour scale(float sx, float sy, float sz){
        return new MatrixDimFour(new float[][]{{sx, 0, 0, 0}, {0, sy, 0, 0}, {0, 0, sz, 0}, {0, 0, 0, 1}});
    }

    //ROTATE
    private static MatrixDimFour rotate(float fx, float fy, float fz){
        MatrixDimThree res = new MatrixDimThree();
        MatrixDimThree mx = new MatrixDimThree(new float[][]{{1, 0, 0}, {0, (float) Math.cos(fx), (float) Math.sin(fx)}, {0, (float) -Math.sin(fx), (float) Math.cos(fx)}});
        MatrixDimThree my = new MatrixDimThree(new float[][]{{(float) Math.cos(fy), 0, (float) Math.sin(fy)}, {0, 1, 0}, {(float) -Math.sin(fy), 0, (float) Math.cos(fy)}});
        MatrixDimThree mz = new MatrixDimThree(new float[][]{{(float) Math.cos(fz), (float) Math.sin(fz), 0}, {(float) -Math.sin(fz), (float) Math.cos(fz), 0}, {0, 0, 1}});
        res = MatrixDimThree.multMatrix(mx, my);
        res = MatrixDimThree.multMatrix(res, mz);
        return MatrixDimThree.from3To4(res);
    }

    //TRANSLATE
    public static MatrixDimFour translate(float tx, float ty, float tz){
        return new MatrixDimFour(new float[][]{{1, 0, 0, tx}, {0, 1, 0, ty}, {0, 0, 1, tz}, {0, 0, 0, 1}});
    }

    //to global coordinates
    public static VectorDimThree fromLocalToGlobal(float sx, float sy, float sz,
                                                   float fx, float fy, float fz,
                                                   float tx, float ty, float tz, VectorDimThree v){
        VectorDimFour v1 = new VectorDimFour(new float[]{v.getX(), v.getY(), v.getZ(), 1});
        v1 = MatrixDimFour.mMultV(scale(sx, sy, sz), v1);
        v1 = MatrixDimFour.mMultV(rotate(fx, fy, fz), v1);
        v1 = MatrixDimFour.mMultV(translate(tx, ty, tz), v1);
        return VectorDimFour.normalizeByW(v1);
    }

}
