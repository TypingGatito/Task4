package com.cgvsu.model;
import com.cgvsu.Main;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

public class Model {

    private List<Vector3f> vertices;
    private List<Vector2f> textureVertices;
    private List<Vector3f> normals;
    private List<Polygon> polygons;
    public Model() {
        vertices = new ArrayList<>();
        textureVertices = new ArrayList<>();
        normals = new ArrayList<>();
        polygons = new ArrayList<>();
    }

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public List<Vector2f> getTextureVertices() {
        return textureVertices;
    }

    public List<Vector3f> getNormals() {
        return normals;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setVertices(List<Vector3f> vertices) {
        this.vertices = vertices;
    }

    public void setTextureVertices(List<Vector2f> textureVertices) {
        this.textureVertices = textureVertices;
    }

    public void setNormals(List<Vector3f> normals) {
        this.normals = normals;
    }

    public void setPolygons(ArrayList<Polygon> polygons) {
        this.polygons = polygons;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Model)) {
            return false;
        }

        return vertices.equals(((Model) o).vertices) &&
                textureVertices.equals(((Model) o).textureVertices) &&
                normals.equals(((Model) o).normals) &&
                polygons.equals(((Model) o).polygons);
    }
}
