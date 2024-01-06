package com.cgvsu.mathvector;

import com.cgvsu.math.vector.VectorDimFour;

import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorDimTwoTest {
    @Test
    void sumVector(){
        VectorDimTwo v1 = new VectorDimTwo(new float[]{-24, 23});
        VectorDimTwo v2 = new VectorDimTwo(new float[]{24, -23});
        VectorDimTwo v3 = VectorDimTwo.sumVector(v1, v2);
        assertEquals((float) 0.0, v3.getV()[0]);
        assertEquals((float) 0.0, v3.getV()[1]);
    }

    @Test
    void subtractVector(){
        VectorDimTwo v1 = new VectorDimTwo(new float[]{-24, 23});
        VectorDimTwo v2 = new VectorDimTwo(new float[]{24, -23});
        VectorDimTwo v3 = VectorDimTwo.subtractVector(v1, v2);
        assertEquals((float) -48.0, v3.getV()[0]);
        assertEquals((float) 46.0, v3.getV()[1]);
    }

    @Test
    void multiplyByScalar(){
        VectorDimTwo v1 = new VectorDimTwo(new float[]{-24, 23});
        VectorDimTwo v3 = VectorDimTwo.multiplyByScalar((float) -10.0, v1);
        assertEquals((float) 240.0, v3.getV()[0]);
        assertEquals((float) -230.0, v3.getV()[1]);
    }

    @Test
    void divideByScalar(){
        VectorDimTwo v1 = new VectorDimTwo(new float[]{-25, 15});
        VectorDimTwo v3 = VectorDimTwo.divideByScalar((float) -10.0, v1);
        assertEquals((float) 2.5, v3.getV()[0]);
        assertEquals((float) -1.5, v3.getV()[1]);
    }

    @Test
    void getVectorLength(){
        VectorDimTwo v1 = new VectorDimTwo(new float[]{-6, 8});
        assertEquals((float) 10.0, VectorDimTwo.getVectorLength(v1));
    }

    @Test
    void normalize(){
        VectorDimTwo v1 = new VectorDimTwo(new float[]{-6, 8});
        VectorDimTwo v3 = VectorDimTwo.normalize(v1);
        assertEquals((float) -0.6, v3.getV()[0]);
        assertEquals((float) 0.8, v3.getV()[1]);
    }

    @Test
    void scaleMultiply(){
        VectorDimTwo v1 = new VectorDimTwo(new float[]{-12, 3.5f});
        VectorDimTwo v2 = new VectorDimTwo(new float[]{0.5F, 10});
        float res = VectorDimTwo.scaleMultiply(v1, v2);
        assertEquals(29.0f, res);
    }
}
