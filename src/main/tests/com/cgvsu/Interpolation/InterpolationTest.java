package com.cgvsu.Interpolation;

import com.cgvsu.components.model.Model;
import com.cgvsu.components.polygon.Polygon;
import com.cgvsu.components.polygon.RegularPolygon;
import com.cgvsu.draw.rasterisation.Interpolation;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objwriter.ObjWriter;
import com.cgvsu.objwriter.ObjWriterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

class InterpolationTest {
    @Test
    public void testBarycentric() throws IOException {
        VectorDimThree ans = new VectorDimThree((float) 2/5, (float) 1/5, (float) 2/5);
        VectorDimTwo v1 = new VectorDimTwo(3, 2);
        VectorDimTwo v2 = new VectorDimTwo(5, 3);
        VectorDimTwo v3 = new VectorDimTwo(2, 4);
        VectorDimTwo v = new VectorDimTwo(3, 3);

        Assertions.assertTrue(Interpolation.calculateBarycentric(v1, v2, v3, v).equals(ans));
    }
}
