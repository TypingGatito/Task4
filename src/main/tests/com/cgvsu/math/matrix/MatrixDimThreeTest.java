package com.cgvsu.math.matrix;

import com.cgvsu.math.vector.VectorDimThree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixDimThreeTest {
    @Test
    void sumMatrix(){
        MatrixDimThree m1 = new MatrixDimThree(new float[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        MatrixDimThree m2 = new MatrixDimThree(new float[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}});
        MatrixDimThree m3 = new MatrixDimThree(MatrixDimThree.sumMatrix(m1, m2).getM1());
        MatrixDimThree m4 = new MatrixDimThree(new float[][]{{1, 3, 5}, {7, 9, 11}, {13, 15, 17}});
        for (int i = 0; i < m1.getM1().length; i++){
            for (int j = 0; j < m3.getM1()[0].length; j++) {
                assertEquals(m4.getM1()[i][j], m3.getM1()[i][j]);
            }
        }
    }

    @Test
    void subtractMatrix(){
        MatrixDimThree m1 = new MatrixDimThree(new float[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        MatrixDimThree m2 = new MatrixDimThree(new float[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}});
        MatrixDimThree m3 = new MatrixDimThree(MatrixDimThree.subtractMatrix(m1, m2).getM1());
        for (int i = 0; i < m1.getM1().length; i++){
            for (int j = 0; j < m3.getM1()[0].length; j++) {
                assertEquals(1f, m3.getM1()[i][j]);
            }
        }
    }

    @Test
    void mMultV(){
        MatrixDimThree m1 = new MatrixDimThree(new float[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}});
        VectorDimThree v1 = new VectorDimThree(new float[]{1, 1, 0});
        VectorDimThree v = new VectorDimThree(MatrixDimThree.mMultV(m1, v1).getV());
        for (int i = 0; i < v.getV().length; i++){
            assertEquals( 3f, v.getV()[i]);
        }
    }

    @Test
    void multMatrix(){
        MatrixDimThree m1 = new MatrixDimThree(new float[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        MatrixDimThree m2 = new MatrixDimThree(new float[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}});
        MatrixDimThree m3 = new MatrixDimThree(MatrixDimThree.multMatrix(m1, m2).getM1());
        MatrixDimThree m4 = new MatrixDimThree(new float[][]{{24, 30, 36}, {51, 66, 81}, {78, 102, 126}});
        for (int i = 0; i < m4.getM1().length; i++){
            for (int j = 0; j < m4.getM1()[0].length; j++) {
                assertEquals(m4.getM1()[i][j], m3.getM1()[i][j]);
            }
        }
    }

    @Test
    void transposition(){
        MatrixDimThree m = new MatrixDimThree(new float[][]{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}});
        MatrixDimThree m1 = new MatrixDimThree(MatrixDimThree.transposition(m).getM1());
        MatrixDimThree m2 = new MatrixDimThree(new float[][]{{0, 3, 6}, {1, 4, 7}, {2, 5, 8}});
        for (int i = 0; i < m2.getM1().length; i++){
            for (int j = 0; j < m2.getM1()[0].length; j++) {
                assertEquals(m2.getM1()[i][j], m1.getM1()[i][j]);
            }
        }
    }

    @Test
    void zeroMatrix(){
        MatrixDimThree m0 = new MatrixDimThree(MatrixDimThree.zeroMatrix().getM1());
        MatrixDimThree m = new MatrixDimThree(new float[3][3]);
        for (int i = 0; i < m.getM1().length; i++){
            for (int j = 0; j < m.getM1()[0].length; j++) {
                assertEquals(0.0f, m.getM1()[i][j]);
            }
        }
    }

    @Test
    void eyeMatrix(){
        MatrixDimThree m1 = new MatrixDimThree(MatrixDimThree.eyeMatrix1().getM1());
        MatrixDimThree m = new MatrixDimThree(new float[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
        for (int i = 0; i < m.getM1().length; i++){
            for (int j = 0; j < m.getM1()[0].length; j++) {
                assertEquals(m.getM1()[i][j], m1.getM1()[i][j]);
            }
        }
    }

    @Test
    void getDeterminant(){
        MatrixDimThree m = new MatrixDimThree(new float[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        assertEquals(0.0f, MatrixDimThree.getDeterminant(m));
    }

    /*@Test
    void reverseMatrix(){
        MatrixDimThree m = new MatrixDimThree(new float[]{2, 5, 7, 6, 3, 4, 5, -2, -3});
        MatrixDimThree mRev = new MatrixDimThree(new float[]{1, -1, 1, -38, 41, -34, 27, -29, 24});
        for (int i = 0; i < m.getM().length; i++){
            assertEquals(m.getM()[i], mRev.getM()[i]);
        }
    }*/
}
