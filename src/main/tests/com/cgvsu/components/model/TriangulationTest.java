package com.cgvsu.components.model;

import com.cgvsu.components.polygon.Polygon;
import com.cgvsu.components.polygon.RegularPolygon;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;

public class TriangulationTest {
    public Model generateNVerticesOnePolygonModel(int n) {
        Model model = new Model();
        ArrayList<VectorDimThree> vertices = new ArrayList<>();
        ArrayList<VectorDimThree> normals = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            VectorDimThree v = new VectorDimThree(i, i + 1, i + 2);
            vertices.add(v);
            normals.add(v);
        }
        ArrayList<VectorDimTwo> textureVertices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            VectorDimTwo vt = new VectorDimTwo(i, i + 1);
            textureVertices.add(vt);
        }
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            indices.add(i);
        }
        Polygon polygon = new RegularPolygon();
        polygon.setVertexIndices(indices);
        polygon.setTextureVertexIndices(indices);
        polygon.setNormalIndices(indices);

        model.vertices = vertices;
        model.textureVertices = textureVertices;
        model.normals = normals;
        model.polygons.add(polygon);

        return model;
    }

    @Test
    public void testTriangulateModel() {
        for (int i = 3; i < 30; i++) {
            Model test = generateNVerticesOnePolygonModel(i);
            test = new ModelTriangulated(test);
            Assertions.assertTrue(test.polygons.size() != i - 2);
        }
    }

    @Test
    public void testRectangularModel() {
        Model source = new Model();
        ArrayList<Integer> indices = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        Polygon p = new RegularPolygon();
        p.setVertexIndices(indices);
        p.setTextureVertexIndices(indices);
        p.setNormalIndices(indices);

        source.polygons.add(p);
        source = new ModelTriangulated(source);

        Model expected = new Model();
        Polygon p1 = new RegularPolygon();
        ArrayList<Integer> indices1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        p1.setVertexIndices(indices1);
        p1.setTextureVertexIndices(indices1);
        p1.setNormalIndices(indices1);
        Polygon p2 = new RegularPolygon();
        ArrayList<Integer> indices2 = new ArrayList<>(Arrays.asList(1, 3, 4));
        p2.setVertexIndices(indices2);
        p2.setTextureVertexIndices(indices2);
        p2.setNormalIndices(indices2);

        expected.polygons.add(p1);
        expected.polygons.add(p2);

        boolean test = source.polygons.get(0).getVertexIndices().toString().equals(expected.polygons.get(0).getVertexIndices().toString()) &&
                source.polygons.get(1).getVertexIndices().toString().equals(expected.polygons.get(1).getVertexIndices().toString());
        Assertions.assertTrue(test);
    }
}