package com.cgvsu.model.polygon;

import java.util.List;

public interface Polygon {
    List<Integer> getVertexIndices();

    void setVertexIndices(List<Integer> vertexIndices);

    List<Integer> getTextureVertexIndices();

    void setTextureVertexIndices(List<Integer> textureVertexIndices);

    List<Integer> getNormalIndices();

    void setNormalIndices(List<Integer> normalIndices);

    boolean hasTexture();

    void checkIndices(int verticesSize, int textureVerticesSize, int normalsSize);

    boolean equals(Object o);

    int hashCode();
}
