package com.cgvsu.draw;

import com.cgvsu.math.matrix.MatrixDimFour;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;

public class DrawUtils {
    public static float xToDraw(float x, final int width) {
        return x * (width - 1)/2F + (width-1) / 2.0F;
    }
    public static float xToBack(float x, final int width) {
        return (x - (width-1) / 2.0F) * 2F/ (width - 1);
    }

    public static float yToDraw(float y, final int height) {
        return y * (1 - height)/2F + (height - 1) / 2.0F;
    }

    public static float yToBack(float y, final int height) {
        return (y - (height - 1) / 2.0F) * 2F / (1 - height);
    }

    public static VectorDimThree multiplyMatrix4ByVector3(final MatrixDimFour matrix, final VectorDimThree vertex) {
        /*final float x = (vertex.getX() * matrix.getMIJ(0, 0)) + (vertex.getY() * matrix.getMIJ(1, 0)) + (vertex.getZ() * matrix.getMIJ(2, 0)) + matrix.getMIJ(3, 0);
        final float y = (vertex.getX() * matrix.getMIJ(0, 1)) + (vertex.getY() * matrix.getMIJ(1, 1)) + (vertex.getZ() * matrix.getMIJ(2, 1)) + matrix.getMIJ(3, 1);
        final float z = (vertex.getX() * matrix.getMIJ(0, 2)) + (vertex.getY() * matrix.getMIJ(1, 2)) + (vertex.getZ() * matrix.getMIJ(2, 2)) + matrix.getMIJ(3, 2);
        final float w = (vertex.getX() * matrix.getMIJ(0, 3)) + (vertex.getY() * matrix.getMIJ(1, 3)) + (vertex.getZ() * matrix.getMIJ(2, 3)) + matrix.getMIJ(3, 3);*/
        final float x = (vertex.getX() * matrix.getMIJ(0, 0)) + (vertex.getY() * matrix.getMIJ(0, 1)) + (vertex.getZ() * matrix.getMIJ(0, 2)) + matrix.getMIJ(0, 3);
        final float y = (vertex.getX() * matrix.getMIJ(1, 0)) + (vertex.getY() * matrix.getMIJ(1, 1)) + (vertex.getZ() * matrix.getMIJ(1, 2)) + matrix.getMIJ(1, 3);
        final float z = (vertex.getX() * matrix.getMIJ(2, 0)) + (vertex.getY() * matrix.getMIJ(2, 1)) + (vertex.getZ() * matrix.getMIJ(2, 2)) + matrix.getMIJ(2, 3);
        final float w = (vertex.getX() * matrix.getMIJ(3, 0)) + (vertex.getY() * matrix.getMIJ(3, 1)) + (vertex.getZ() * matrix.getMIJ(3, 2)) + matrix.getMIJ(3, 3);
        return new VectorDimThree(x / w, y / w, z / w);
    }

    public static VectorDimTwo vertexToPoint(final VectorDimThree vertex, final int width, final int height) {
/*        return new VectorDimTwo(vertex.getX() * width + width / 2.0F,
                -vertex.getY() * height + height / 2.0F);*/
        return new VectorDimTwo(vertex.getX() * (width - 1)/2F + (width-1) / 2.0F,
                vertex.getY() * (1-height)/2F + (height-1) / 2.0F);
    }
    public static VectorDimThree vertexToPoint3(final VectorDimThree vertex, final int width, final int height) {
/*        return new VectorDimThree(vertex.getX() * width + width / 2.0F,
                -vertex.getY() * height + height / 2.0F, vertex.getZ());*/
        return new VectorDimThree(vertex.getX() * (width - 1)/2F + (width-1) / 2.0F,
                vertex.getY() * (1-height)/2F + (height-1) / 2.0F, vertex.getZ());
    }
}
