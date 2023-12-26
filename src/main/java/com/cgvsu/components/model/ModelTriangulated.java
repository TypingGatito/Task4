package com.cgvsu.components.model;

import com.cgvsu.components.polygon.Polygon;
import com.cgvsu.components.polygon.TriangularPolygon;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;

import java.util.List;

public class ModelTriangulated extends Model {

    public ModelTriangulated(Model model) {
        for (VectorDimThree vertex: model.vertices) {
            vertices.add(new VectorDimThree(vertex.getV()));
        }
        for (VectorDimTwo textureVertex: model.textureVertices) {
            vertices.add(new VectorDimThree(textureVertex.getV()));
        }
        for (VectorDimThree normal: model.normals) {
            vertices.add(new VectorDimThree(normal.getV()));
        }

        //vertices.addAll(model.vertices);
        //textureVertices.addAll(model.textureVertices);
        //normals.addAll(model.normals);

        for (Polygon polygon : model.polygons) {
            List<Integer> vertexIndices = polygon.getVertexIndices();
            List<Integer> textureIndices = polygon.getTextureVertexIndices();
            List<Integer> normalIndices = polygon.getNormalIndices();

            if (vertexIndices.size() >= 3) {
                boolean hasTexture = textureIndices.size() >= 3;
                boolean hasNormal = normalIndices.size() >= 3;

                int firstVertex = vertexIndices.get(0);
                int firstTextureVertex = hasTexture ? textureIndices.get(0) : 0;
                int firstNormal = hasNormal ? normalIndices.get(0) : 0;
                for (int i = 1; i < vertexIndices.size() - 1; i++) {
                    TriangularPolygon triangle = new TriangularPolygon();
                    triangle.setVertexIndices(new int[]{firstVertex, vertexIndices.get(i), vertexIndices.get(i + 1)});

                    if (hasTexture) {
                        triangle.setTextureVertexIndices(new int[]{firstTextureVertex, textureIndices.get(i), textureIndices.get(i + 1)});
                    }

                    if (hasNormal) {
                        triangle.setNormalIndices(new int[]{firstNormal, normalIndices.get(i), normalIndices.get(i + 1)});
                    }

                    polygons.add(triangle);
                }
            }
        }
    }
}
