package com.cgvsu.components.model;

import com.cgvsu.components.polygon.Group;
import com.cgvsu.components.polygon.Polygon;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;

import java.util.ArrayList;
import java.util.List;

public class Model {

    public ArrayList<VectorDimThree> vertices = new ArrayList<>();
    public ArrayList<VectorDimTwo> textureVertices = new ArrayList<>();
    public ArrayList<VectorDimThree> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();
    private final List<Group> groups = new ArrayList<>();

    public void addVertex(VectorDimThree vertex) {
        vertices.add(vertex);
    }

    public void addTextureVertex(VectorDimTwo textureVertex) {
        textureVertices.add(textureVertex);
    }

    public void addNormal(VectorDimThree normal) {
        normals.add(normal);
    }

    public void addPolygon(Polygon polygon) {
        polygons.add(polygon);
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public Polygon getFirstPolygon() {
        return polygons.get(0);
    }


    public int getVerticesSize() {
        return vertices.size();
    }

    public int getTextureVerticesSize() {
        return textureVertices.size();
    }

    public int getNormalsSize() {
        return normals.size();
    }

    public int getPolygonsSize() {
        return polygons.size();
    }

    public List<VectorDimThree> getVertices() {
        return vertices;
    }

    public List<VectorDimTwo> getTextureVertices() {
        return textureVertices;
    }

    public List<VectorDimThree> getNormals() {
        return normals;
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
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
