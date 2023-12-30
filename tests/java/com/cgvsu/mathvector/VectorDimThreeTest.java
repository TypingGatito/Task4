package java.com.cgvsu.mathvector;

import com.cgvsu.math.vector.VectorDimThree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VectorDimThreeTest {
    @Test
    void sumVector(){
        VectorDimThree v1 = new VectorDimThree(new float[]{-24, 23, 1.5f});
        VectorDimThree v2 = new VectorDimThree(new float[]{24, -23, 2.3f});
        VectorDimThree v3 = VectorDimThree.sumVector(v1, v2);
        Assertions.assertEquals(0.0f, v3.getV()[0]);
        Assertions.assertEquals(0.0f, v3.getV()[1]);
        Assertions.assertEquals(3.8f, v3.getV()[2]);
    }

    @Test
    void subtractVector(){
        VectorDimThree v1 = new VectorDimThree(new float[]{-24, 23, 1});
        VectorDimThree v2 = new VectorDimThree(new float[]{24, -23, 2.3f});
        VectorDimThree v3 = VectorDimThree.subtractVector(v1, v2);
        Assertions.assertEquals(-48.0f, v3.getV()[0]);
        Assertions.assertEquals(46.0f, v3.getV()[1]);
        Assertions.assertEquals(-1.3f, v3.getV()[2]);
    }

    @Test
    void multiplyByScalar(){
        VectorDimThree v1 = new VectorDimThree(new float[]{-24, 23, 1});
        VectorDimThree v3 = VectorDimThree.multiplyByScalar((float) -10.0, v1);
        Assertions.assertEquals(240.0f, v3.getV()[0]);
        Assertions.assertEquals(-230.0f, v3.getV()[1]);
        Assertions.assertEquals(-10.0f, v3.getV()[2]);
    }

    @Test
    void divideByScalar(){
        VectorDimThree v1 = new VectorDimThree(new float[]{-25, 15, 45});
        VectorDimThree v3 = VectorDimThree.divideByScalar((float) -10.0, v1);
        Assertions.assertEquals((float) 2.5, v3.getV()[0]);
        Assertions.assertEquals((float) -1.5, v3.getV()[1]);
        Assertions.assertEquals((float) -4.5, v3.getV()[2]);
    }

    @Test
    void getVectorLength(){
        VectorDimThree v1 = new VectorDimThree(new float[]{-6, 8, 0});
        Assertions.assertEquals((float) 10.0, VectorDimThree.getVectorLength(v1));
    }

    @Test
    void normalize(){
        VectorDimThree v1 = new VectorDimThree(new float[]{-6, 8, 0});
        VectorDimThree v3 = VectorDimThree.normalize(v1);
        Assertions.assertEquals((float) -0.6, v3.getV()[0]);
        Assertions.assertEquals((float) 0.8, v3.getV()[1]);
        Assertions.assertEquals((float) 0.0, v3.getV()[2]);
    }

    @Test
    void scaleMultiply(){
        VectorDimThree v1 = new VectorDimThree(new float[]{-12, 3.5f, 2.3f});
        VectorDimThree v2 = new VectorDimThree(new float[]{0.5F, 10, 10});
        float res = VectorDimThree.scaleMultiply(v1, v2);
        Assertions.assertEquals(52.0f, res);
    }

    @Test
    void vectorMultiplyV3(){
        VectorDimThree v1 = new VectorDimThree(new float[]{1, 2, 3});
        VectorDimThree v2 = new VectorDimThree(new float[]{2, 1, -2});
        VectorDimThree v3 = VectorDimThree.vectorMultiplyV3(v1, v2);
        Assertions.assertEquals((float) -7.0, v3.getV()[0]);
        Assertions.assertEquals((float) 8.0, v3.getV()[1]);
        Assertions.assertEquals((float) -3.0, v3.getV()[2]);
    }
}
