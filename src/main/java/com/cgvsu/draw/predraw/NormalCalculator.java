package com.cgvsu.draw.predraw;

import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.components.model.Model;
import com.cgvsu.components.polygon.Polygon;

import java.util.ArrayList;
import java.util.List;

public class NormalCalculator {

    public static void recalculateNormals(Model model) {
        ArrayList<VectorDimThree> vertices = model.vertices;
        ArrayList<Polygon> polygons = model.polygons;
        model.normals = new ArrayList<>(vertices.size());

        ArrayList<ArrayList<VectorDimThree>> vertexNormalAccumulator = new ArrayList<>(vertices.size());

        for (int i = 0; i < vertices.size(); i++) {
            vertexNormalAccumulator.add(new ArrayList<>());
        }

        for (int i = 0; i < polygons.size(); i++) {
            Polygon polygon = polygons.get(i);
            VectorDimThree normal = calculateNormalForPolygon(polygon, vertices);
            for (Integer verId : polygon.getVertexIndices()) {
                vertexNormalAccumulator.get(verId).add(normal);
            }
        }

        for (ArrayList<VectorDimThree> normalsAround : vertexNormalAccumulator) {
            VectorDimThree vertexNormal = VectorDimThree.normalize(VectorDimThree.multiplyByScalar(1f / normalsAround.size(),
                    normalsAround.stream().
                    collect(() -> new VectorDimThree(0, 0, 0),
                            (v1, v2) ->
                            {VectorDimThree temp = VectorDimThree.sumVector(v1, v2);
                                v1.setX(temp.getX());
                                v1.setY(temp.getY());
                                v1.setZ(temp.getZ());},
                            (v1, v2) -> {VectorDimThree temp = VectorDimThree.sumVector(v1, v2);
                                v1.setX(temp.getX());
                                v1.setY(temp.getY());
                                v1.setZ(temp.getZ());})));

            model.normals.add(vertexNormal);
        }

        for (Polygon polygon : model.polygons) {
            polygon.setNormalIndices(new ArrayList<>(polygon.getVertexIndices()));
        }
    }

    public static VectorDimThree calculateNormalForPolygon(Polygon polygon, List<VectorDimThree> vertices) {
        int ver1Id = polygon.getVertexIndices().get(0);
        int ver2Id = polygon.getVertexIndices().get(1);
        int ver3Id = polygon.getVertexIndices().get(2);
        VectorDimThree vec1 = VectorDimThree.subtractVector(vertices.get(ver2Id), vertices.get(ver1Id));
        VectorDimThree vec2 = VectorDimThree.subtractVector(vertices.get(ver3Id), vertices.get(ver1Id));

        return VectorDimThree.normalize(VectorDimThree.vectorMultiplyV3(vec1, vec2));
    }
}
