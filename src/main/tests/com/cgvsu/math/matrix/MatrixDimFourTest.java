package com.cgvsu.math.matrix;

import com.cgvsu.math.vector.VectorDimFour;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertEquals;

public class MatrixDimFourTest {
    @Test
    void sumMatrix(){
        MatrixDimFour m1 = new MatrixDimFour(new float[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        MatrixDimFour m2 = new MatrixDimFour(new float[][]{{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}});
        MatrixDimFour m3 = new MatrixDimFour(MatrixDimFour.sumMatrix(m1, m2).getM1());
        MatrixDimFour m4 = new MatrixDimFour(new float[][]{{1, 3, 5, 7}, {9, 11, 13, 15}, {17, 19, 21, 23}, {25, 27, 29, 31}});
        for (int i = 0; i < m1.getM1().length; i++){
            for (int j = 0; j < m3.getM1()[0].length; j++) {
                assertEquals(m4.getM1()[i][j], m3.getM1()[i][j]);
            }
        }
    }

    @Test
    void subtractMatrix(){
        MatrixDimFour m1 = new MatrixDimFour(new float[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        MatrixDimFour m2 = new MatrixDimFour(new float[][]{{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}});
        MatrixDimFour m3 = new MatrixDimFour(MatrixDimFour.subtractMatrix(m1, m2).getM1());
        for (int i = 0; i < m1.getM1().length; i++){
            for (int j = 0; j < m3.getM1()[0].length; j++) {
                assertEquals(1f, m3.getM1()[i][j]);
            }
        }
    }

    @Test
    void mMultV(){
        MatrixDimFour m1 = new MatrixDimFour(new float[][]{{1, 2, 3, 0}, {1, 2, 3, 0}, {1, 2, 3, 0}, {1, 2, 3, 0}});
        VectorDimFour v1 = new VectorDimFour(new float[]{1, 1, 1, 0});
        VectorDimFour v = new VectorDimFour(MatrixDimFour.mMultV(m1, v1).getV());
        for (int i = 0; i < v.getV().length; i++){
            assertEquals(6f, v.getV()[i]);
        }
    }

    @Test
    void multMatrix(){
        MatrixDimFour m1 = new MatrixDimFour(new float[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
        MatrixDimFour m2 = new MatrixDimFour(new float[][]{{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}});
        MatrixDimFour m3 = new MatrixDimFour(MatrixDimFour.multMatrix(m1, m2).getM1());
        MatrixDimThree m4 = new MatrixDimThree(new float[][]{{80, 90, 100, 110}, {176, 202, 228, 254}, {272, 314, 356, 398}, {368, 426, 484, 542}});
        for (int i = 0; i < m4.getM1().length; i++){
            for (int j = 0; j < m4.getM1()[0].length; j++) {
                assertEquals(m4.getM1()[i][j], m3.getM1()[i][j]);
            }
        }
    }

    @Test
    void transposition(){
        MatrixDimFour m = new MatrixDimFour(new float[][]{{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}});
        MatrixDimFour m1 = new MatrixDimFour(MatrixDimFour.transposition(m).getM1());
        MatrixDimFour m2 = new MatrixDimFour(new float[][]{{0, 4, 8, 12}, {1, 5, 9, 13}, {2, 6, 10, 14}, {3, 7, 11, 15}});
        for (int i = 0; i < m2.getM1().length; i++){
            for (int j = 0; j < m2.getM1()[0].length; j++) {
                assertEquals(m2.getM1()[i][j], m1.getM1()[i][j]);
            }
        }
    }

    @Test
    void zeroMatrix(){
        MatrixDimThree m0 = new MatrixDimThree(MatrixDimThree.zeroMatrix().getM1());
        MatrixDimThree m = new MatrixDimThree(new float[4][4]);
        for (int i = 0; i < m.getM1().length; i++){
            for (int j = 0; j < m.getM1()[0].length; j++) {
                assertEquals(0.0f, m.getM1()[i][j]);
            }
        }
    }

    @Test
    void eyeMatrix(){
        MatrixDimFour m1 = new MatrixDimFour(MatrixDimFour.eyeMatrix().getM1());
        MatrixDimFour m = new MatrixDimFour(new float[][]{{1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}});
        for (int i = 0; i < m.getM1().length; i++){
            for (int j = 0; j < m.getM1()[0].length; j++) {
                assertEquals(m.getM1()[i][j], m1.getM1()[i][j]);
            }
        }
    }
}
