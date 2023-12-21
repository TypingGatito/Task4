package com.cgvsu.modeltools;

import com.cgvsu.math.Vector3f;
import com.cgvsu.modelcomponents.model.Model;
import com.cgvsu.modelcomponents.polygon.Polygon;
import com.cgvsu.modelcomponents.polygon.RegularPolygon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ModelDeleteTest {

    @Test
    void testDeleteVertexes() {
        //only vertexes, no polygons removed
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                new Vector3f(5, 8, 9),
                new Vector3f(7, 7, 8),
                new Vector3f(7, 3, 0),
                new Vector3f(8, 9, 3)));

        Model expectedModel = new Model();
        expectedModel.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                new Vector3f(5, 8, 9),
                new Vector3f(7, 3, 0)));

        ModelDelete.deleteVertexes(model, new ArrayList<>(Arrays.asList(2, 4)));

        assertTrue(model.equals(expectedModel));
    }

    @Test
    void testDeleteAllVertex() {
        //remove all vertexes
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                new Vector3f(5, 8, 9),
                new Vector3f(7, 7, 8),
                new Vector3f(7, 3, 0),
                new Vector3f(8, 9, 3)));

        Model expectedModel = new Model();
        expectedModel.vertices = new ArrayList<>(Arrays.asList());

        ModelDelete.deleteVertexes(model, new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4)));

        assertTrue(model.equals(expectedModel));
    }

    @Test
    void testDeleteVertexesAndPolygon1() {
        //remove 1 vertex so that 1 polygon is deleted
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                new Vector3f(5, 8, 9),
                new Vector3f(7, 7, 8),
                new Vector3f(7, 3, 0),
                new Vector3f(8, 9, 3),
                new Vector3f(8, 7, 3)));
        Polygon polygon = new RegularPolygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 2, 4)));
        model.polygons.add(polygon);
        Polygon polygon2 = new RegularPolygon();
        polygon2.setVertexIndices(new ArrayList<>(Arrays.asList(1, 3, 5)));
        model.polygons.add(polygon2);


        Model expectedModel = new Model();
        expectedModel.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                new Vector3f(5, 8, 9),
                new Vector3f(7, 3, 0),
                new Vector3f(8, 9, 3),
                new Vector3f(8, 7, 3)));
        Polygon polygon3 = new RegularPolygon();
        polygon3.setVertexIndices(new ArrayList<>(Arrays.asList(1, 2, 4)));
        expectedModel.polygons.add(polygon3);

        ModelDelete.deleteVertexes(model, new ArrayList<>(Arrays.asList(2)));

        assertTrue(model.equals(expectedModel));
    }

    @Test
    void testDeleteVertexesAndPolygon2() {
        //remove all vertexes which belong to 1 polygon
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                new Vector3f(5, 8, 9),
                new Vector3f(7, 7, 8),
                new Vector3f(7, 3, 0),
                new Vector3f(8, 9, 3),
                new Vector3f(8, 7, 3)));
        Polygon polygon = new RegularPolygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 2, 4)));
        model.polygons.add(polygon);

        Polygon polygon2 = new RegularPolygon();
        polygon2.setVertexIndices(new ArrayList<>(Arrays.asList(1, 3, 5)));
        model.polygons.add(polygon2);


        Model expectedModel = new Model();
        expectedModel.vertices = new ArrayList<>(Arrays.asList(new Vector3f(5, 8, 9),
                new Vector3f(7, 3, 0),
                new Vector3f(8, 7, 3)));
        Polygon polygon3 = new RegularPolygon();
        polygon3.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        expectedModel.polygons.add(polygon3);

        ModelDelete.deleteVertexes(model, new ArrayList<>(Arrays.asList(0, 2, 4)));

        assertTrue(model.equals(expectedModel));
    }

    @Test
    void testDeleteVertexesAndAllPolygons() {
        //remove 2 vertexes which belong to different polygons
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                new Vector3f(5, 8, 9),
                new Vector3f(7, 7, 8),
                new Vector3f(7, 3, 0),
                new Vector3f(8, 9, 3),
                new Vector3f(8, 7, 3)));
        Polygon polygon = new RegularPolygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 2, 4)));
        model.polygons.add(polygon);

        Polygon polygon2 = new RegularPolygon();
        polygon2.setVertexIndices(new ArrayList<>(Arrays.asList(1, 3, 5)));
        model.polygons.add(polygon2);


        Model expectedModel = new Model();
        expectedModel.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                new Vector3f(7, 3, 0),
                new Vector3f(8, 9, 3),
                new Vector3f(8, 7, 3)));

        ModelDelete.deleteVertexes(model, new ArrayList<>(Arrays.asList(1, 2)));

        assertTrue(model.equals(expectedModel));
    }

    @Test
    void testDeleteAllVertexesAndAllPolygons() {
        //remove all vertexes
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                new Vector3f(5, 8, 9),
                new Vector3f(7, 7, 8),
                new Vector3f(7, 3, 0),
                new Vector3f(8, 9, 3),
                new Vector3f(8, 7, 3)));
        Polygon polygon = new RegularPolygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 2, 4)));
        model.polygons.add(polygon);

        Polygon polygon2 = new RegularPolygon();
        polygon2.setVertexIndices(new ArrayList<>(Arrays.asList(1, 3, 5)));
        model.polygons.add(polygon2);


        Model expectedModel = new Model();
        expectedModel.vertices = new ArrayList<>();

        ModelDelete.deleteVertexes(model, new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5)));

        assertTrue(model.equals(expectedModel));
    }

    @Test
    void testDeleteVertexesAndNoPolygons() {
        //remove vertexes which do not belong to polygons
        Model model = new Model();
        model.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                 new Vector3f(5, 8, 9),
                new Vector3f(7, 7, 8),
                 new Vector3f(7, 3, 0),
                new Vector3f(8, 9, 3),
                new Vector3f(8, 7, 3),
                 new Vector3f(1, 7, 3),
                new Vector3f(99, 7, 3)));
        Polygon polygon = new RegularPolygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 2, 4)));
        model.polygons.add(polygon);

        Polygon polygon2 = new RegularPolygon();
        polygon2.setVertexIndices(new ArrayList<>(Arrays.asList(0, 2, 5)));
        model.polygons.add(polygon2);

        Polygon polygon3 = new RegularPolygon();
        polygon3.setVertexIndices(new ArrayList<>(Arrays.asList(0, 2, 7)));
        model.polygons.add(polygon3);


        Model expectedModel = new Model();
        expectedModel.vertices = new ArrayList<>(Arrays.asList(new Vector3f(2, 5, 7),
                new Vector3f(7, 7, 8),
                new Vector3f(8, 9, 3),
                new Vector3f(8, 7, 3),
                new Vector3f(99, 7, 3)));
        Polygon polygon4 = new RegularPolygon();
        polygon4.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        expectedModel.polygons.add(polygon4);

        Polygon polygon5 = new RegularPolygon();
        polygon5.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 3)));
        expectedModel.polygons.add(polygon5);

        Polygon polygon6 = new RegularPolygon();
        polygon6.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 4)));
        expectedModel.polygons.add(polygon6);

        ModelDelete.deleteVertexes(model, new ArrayList<>(Arrays.asList(1, 3, 6)));

        assertTrue(model.equals(expectedModel));
    }

}