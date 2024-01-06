package com.cgvsu.normal_calc;

import com.cgvsu.components.model.Model;
import com.cgvsu.components.polygon.Polygon;
import com.cgvsu.components.polygon.RegularPolygon;
import com.cgvsu.draw.predraw.NormalCalculator;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.objreader.ObjReader;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class NormalCalcTest {
    @Test
    public void normalForPolygon1() {
        Polygon polygon = new RegularPolygon();
        //polygon.setVertexIndices(new ArrayList<>(Arrays.asList(1, 2, 3)));
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        ArrayList<VectorDimThree> vertices = new ArrayList<>(Arrays.asList(new VectorDimThree(101, -119, 275), new VectorDimThree(106, -115, 269), new VectorDimThree(103, -121, 289)));
        assertEquals(new VectorDimThree(0.46421355f, -0.86512524f, -0.18990554f), NormalCalculator.calculateNormalForPolygon(polygon, vertices));
    }

    @Test
    public void normalForPolygon1Translated() {
        Polygon polygon = new RegularPolygon();
        //polygon.setVertexIndices(new ArrayList<>(Arrays.asList(1, 2, 3)));
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        ArrayList<VectorDimThree> vertices = new ArrayList<>(Arrays.asList(new VectorDimThree(1, 4, 1), new VectorDimThree(6, 8, -5), new VectorDimThree(3, 2, 15)));
        assertEquals(new VectorDimThree(0.46421355f, -0.86512524f, -0.18990554f), NormalCalculator.calculateNormalForPolygon(polygon, vertices));
    }

    @Test
    public void normalForPolygon1Scaled() {
        Polygon polygon = new RegularPolygon();
        //polygon.setVertexIndices(new ArrayList<>(Arrays.asList(1, 2, 3)));
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        ArrayList<VectorDimThree> vertices = new ArrayList<>(Arrays.asList(new VectorDimThree(0.0769230769f, 0.3076923077f, 0.0769230769f),
                new VectorDimThree(0.4615384615f, 0.6153846154f, -0.3846153846f),
                new VectorDimThree(0.2307692308f, 0.1538461538f, 1.1538461538f)));
        VectorDimThree v = NormalCalculator.calculateNormalForPolygon(polygon, vertices);
        assertEquals(new VectorDimThree(0.46421355f, -0.86512524f, -0.18990554f), NormalCalculator.calculateNormalForPolygon(polygon, vertices));
    }

    @Test
    public void normalForPolygon2() {
        Polygon polygon = new RegularPolygon();
        //polygon.setVertexIndices(new ArrayList<>(Arrays.asList(1, 2, 3)));
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        ArrayList<VectorDimThree> vertices = new ArrayList<>(Arrays.asList(new VectorDimThree(8, 1, 0),
                new VectorDimThree(0, 0, 0),
                new VectorDimThree(1163, 0.1538461538f, 0)));
        assertEquals(new VectorDimThree(0, 0, 1), NormalCalculator.calculateNormalForPolygon(polygon, vertices));
    }

    @Test
    public void normalForPolygon3() {
        Polygon polygon = new RegularPolygon();
        //polygon.setVertexIndices(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)));
        ArrayList<VectorDimThree> vertices = new ArrayList<>(Arrays.asList(new VectorDimThree(8, 1, 0),
                new VectorDimThree(0, 0, 0),
                new VectorDimThree(1163, 0.1538461538f, 0)));
        assertEquals(new VectorDimThree(0, 0, 1), NormalCalculator.calculateNormalForPolygon(polygon, vertices));
    }

    @Test
    public void recalculateNormalsCube() {
        try{
            String fileContent = Files.readString(
                    Path.of("C:\\University\\2year_part1\\Graphics\\Task4\\models\\test\\cube.obj"));
            Model cubeToCalc = ObjReader.read(fileContent);

            fileContent = Files.readString(
                    Path.of("C:\\University\\2year_part1\\Graphics\\Task4\\models\\test\\cubeOutput.obj"));
            Model cubePreCalc = ObjReader.read(fileContent);
            NormalCalculator.recalculateNormals(cubeToCalc);

            assertTrue(cubePreCalc.equals(cubeToCalc));
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void recalculateNormalsTeapot() {
        try{

            String fileContent = Files.readString(
                    Path.of("C:\\University\\2year_part1\\Graphics\\Task4\\models\\test\\teapot.obj"));
            Model teapotToCalc = ObjReader.read(fileContent);

            fileContent = Files.readString(
                    Path.of("C:\\University\\2year_part1\\Graphics\\Task4\\models\\test\\teapotOutput.obj"));
            Model teapotPreCalc = ObjReader.read(fileContent);
            NormalCalculator.recalculateNormals(teapotToCalc);

            assertEquals(teapotToCalc, teapotPreCalc);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
