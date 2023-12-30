package java.com.cgvsu.mathvector;

import com.cgvsu.math.vector.VectorDimFour;
import com.cgvsu.math.vector.VectorDimThree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VectorDimFourTest {
    @Test
    void sumVector(){
        VectorDimFour v1 = new VectorDimFour(new float[]{-24, 23, 1.5f, 2.8f});
        VectorDimFour v2 = new VectorDimFour(new float[]{24, -23, 2.3f, 3.2f});
        VectorDimFour v3 = VectorDimFour.sumVector(v1, v2);
        Assertions.assertEquals(0.0f, v3.getV()[0]);
        Assertions.assertEquals(0.0f, v3.getV()[1]);
        Assertions.assertEquals(3.8f, v3.getV()[2]);
        Assertions.assertEquals(6.0f, v3.getV()[3]);
    }

    @Test
    void subtractVector(){
        VectorDimFour v1 = new VectorDimFour(new float[]{-24, 23, 1, 10});
        VectorDimFour v2 = new VectorDimFour(new float[]{24, -23, 2.3f, 2.3f});
        VectorDimFour v3 = VectorDimFour.subtractVector(v1, v2);
        Assertions.assertEquals(-48.0f, v3.getV()[0]);
        Assertions.assertEquals(46.0f, v3.getV()[1]);
        Assertions.assertEquals(-1.3f, v3.getV()[2]);
        Assertions.assertEquals(7.7f, v3.getV()[3]);
    }

    @Test
    void multiplyByScalar(){
        VectorDimFour v1 = new VectorDimFour(new float[]{-24, 23, 1, -7.7f});
        VectorDimFour v3 = VectorDimFour.multiplyByScalar((float) -10.0, v1);
        Assertions.assertEquals(240.0f, v3.getV()[0]);
        Assertions.assertEquals(-230.0f, v3.getV()[1]);
        Assertions.assertEquals(-10.0f, v3.getV()[2]);
        Assertions.assertEquals(77f, v3.getV()[3]);
    }

    @Test
    void divideByScalar(){
        VectorDimFour v1 = new VectorDimFour(new float[]{-25, 15, 45, 23});
        VectorDimFour v3 = VectorDimFour.divideByScalar((float) -10.0, v1);
        Assertions.assertEquals((float) 2.5, v3.getV()[0]);
        Assertions.assertEquals((float) -1.5, v3.getV()[1]);
        Assertions.assertEquals((float) -4.5, v3.getV()[2]);
        Assertions.assertEquals(-2.3f, v3.getV()[3]);
    }

    @Test
    void getVectorLength(){
        VectorDimFour v1 = new VectorDimFour(new float[]{-6, 8, 0, 0});
        Assertions.assertEquals((float) 10.0, VectorDimFour.getVectorLength(v1));
    }

    @Test
    void normalize(){
        VectorDimFour v1 = new VectorDimFour(new float[]{-6, 8, 0, 0});
        VectorDimFour v3 = VectorDimFour.normalize(v1);
        Assertions.assertEquals((float) -0.6, v3.getV()[0]);
        Assertions.assertEquals((float) 0.8, v3.getV()[1]);
        Assertions.assertEquals((float) 0.0, v3.getV()[2]);
        Assertions.assertEquals(0.0f, v3.getV()[3]);
    }

    @Test
    void scaleMultiply(){
        VectorDimFour v1 = new VectorDimFour(new float[]{-12, 3.5f, 2.3f, 0});
        VectorDimFour v2 = new VectorDimFour(new float[]{0.5F, 10, 10, 25});
        float res = VectorDimFour.scaleMultiply(v1, v2);
        Assertions.assertEquals(52.0f, res);
    }

    @Test
    void normalizeByW(){
        VectorDimFour v = new VectorDimFour(new float[]{-12, 7, 4.6f, 2});
        VectorDimThree v1 = new VectorDimThree(new float[]{-6, 3.5f, 2.3f});
        VectorDimThree v2 = new VectorDimThree(VectorDimFour.normalizeByW(v).getV());
        Assertions.assertEquals((float) -6, v2.getV()[0]);
        Assertions.assertEquals(3.5f, v2.getV()[1]);
        Assertions.assertEquals(2.3f, v2.getV()[2]);
    }
}
