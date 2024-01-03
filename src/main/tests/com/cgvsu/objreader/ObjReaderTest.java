package com.cgvsu.objreader;

import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.cgvsu.components.model.Model;
import com.cgvsu.objreader.exceptions.ArgumentsSizeException;
import com.cgvsu.objreader.exceptions.FaceWordTypeException;

public class ObjReaderTest {
    @Test
    void testTooFewVectorDimThreeArguments() {
        ObjReader objReader = new ObjReader();
        try {
            objReader.parseVectorDimThree(new String[]{"3", "2"});
            Assertions.fail();
        } catch (ArgumentsSizeException exception) {
            String expectedMessage = "Error parsing OBJ file on line: 0. Too few arguments.";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Test
    void testTooManyVectorDimThreeArguments() {
        ObjReader objReader = new ObjReader();
        objReader.isSoft = false;
        try {
            objReader.parseVectorDimThree(new String[]{"3", "2", "1", "0"});
            Assertions.fail();
        } catch (ArgumentsSizeException exception) {
            String expectedMessage = "Error parsing OBJ file on line: 0. Too many arguments.";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Test
    void testTooManyVectorDimThreeArgumentsSoft() {
        ObjReader objReader = new ObjReader();
        VectorDimThree actual = objReader.parseVectorDimThree(new String[]{"3", "2", "1", "0"});
        VectorDimThree expected = new VectorDimThree(3, 2, 1);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testVectorDimThree() {
        ObjReader objReader = new ObjReader();
        VectorDimThree expected = new VectorDimThree(2.5F, 8, 0);
        VectorDimThree actual = objReader.parseVectorDimThree(new String[]{"2,5", "8", "0"});

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testTooFewVector2fArguments() {
        ObjReader objReader = new ObjReader();
        try {
            objReader.parseVectorDimTwo(new String[]{"3"});
            Assertions.fail();
        } catch (ArgumentsSizeException exception) {
            String expectedMessage = "Error parsing OBJ file on line: 0. Too few arguments.";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Test
    void testTooManyVector2fArguments() {
        ObjReader objReader = new ObjReader();
        objReader.isSoft = false;
        try {
            objReader.parseVectorDimTwo(new String[]{"3", "2", "1"});
            Assertions.fail();
        } catch (ArgumentsSizeException exception) {
            String expectedMessage = "Error parsing OBJ file on line: 0. Too many arguments.";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Test
    void testTooManyVector2fArgumentsSoft() {
        ObjReader objReader = new ObjReader();
        VectorDimTwo actual = objReader.parseVectorDimTwo(new String[]{"3", "2", "1"});
        VectorDimTwo expected = new VectorDimTwo(3, 2);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testVector2f() {
        ObjReader objReader = new ObjReader();
        VectorDimTwo expected = new VectorDimTwo(2.5F, 8);
        VectorDimTwo actual = objReader.parseVectorDimTwo(new String[]{"2,5", "8"});

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testParseFaceArgumentsSizeException() {
        ObjReader objReader = new ObjReader();
        try {
            objReader.parseFace(new String[]{"1/1", "2/2"});
            Assertions.fail();
        } catch (ArgumentsSizeException exception) {
            String expectedMessage = "Error parsing OBJ file on line: 0. Too few face arguments.";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Test
    void testParseFaceWordTypeException() {
        ObjReader objReader = new ObjReader();
        try {
            objReader.parseFace(new String[]{"1", "2/2", "3/3/3"});
            Assertions.fail();
        } catch (FaceWordTypeException exception) {
            String expectedMessage = "Error parsing OBJ file on line: 0. Several argument types in one polygon.";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }
    }


    @Test
    void testParseVertex() {
        Model model = ObjReader.read("v 0.5 0 1.1");
        VectorDimThree actualVector = model.getVertices().get(0);

        VectorDimThree expectedVector = new VectorDimThree(0.5F, 0F, 1.1F);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedVector, actualVector),
                () -> Assertions.assertEquals(1, model.getVerticesSize()),
                () -> Assertions.assertEquals(0, model.getTextureVerticesSize()),
                () -> Assertions.assertEquals(0, model.getNormalsSize())
        );
    }

    @Test
    void testParseTextureVertex() {
        Model model = ObjReader.read("vt 0 0.7");
        VectorDimTwo actualVector = model.getTextureVertices().get(0);

        VectorDimTwo expectedVector = new VectorDimTwo(0F, 0.7F);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedVector, actualVector),
                () -> Assertions.assertEquals(0, model.getVerticesSize()),
                () -> Assertions.assertEquals(1, model.getTextureVerticesSize()),
                () -> Assertions.assertEquals(0, model.getNormalsSize())
        );
    }

    @Test
    void testParseNormal() {
        Model model = ObjReader.read("vn 0.0 0 -1.1");
        VectorDimThree actualVector = model.getNormals().get(0);

        VectorDimThree expectedVector = new VectorDimThree(0F, 0F, -1.1F);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expectedVector, actualVector),
                () -> Assertions.assertEquals(0, model.getVerticesSize()),
                () -> Assertions.assertEquals(0, model.getTextureVerticesSize()),
                () -> Assertions.assertEquals(1, model.getNormalsSize())
        );
    }


    @Test
    void testDecimalSeparator() {
        Model model = ObjReader.read("v 0.5 0 1.1");
        VectorDimThree actual = model.getVertices().get(0);

        VectorDimThree expected = new VectorDimThree(0.5F, 0F, 1.1F);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testCommaSeparator() {
        Model model = ObjReader.read("v 0,5 0 1,1");
        VectorDimThree actual = model.getVertices().get(0);

        VectorDimThree expected = new VectorDimThree(0.5F, 0F, 1.1F);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testBothSeparators() {
        try {
            Model model = ObjReader.read("v 0.5 0 1,1");
            Assertions.fail();
        } catch (RuntimeException exception) {
            String expectedMessage = "Two different decimal separators used in one file.";
            Assertions.assertEquals(expectedMessage, exception.getMessage());
        }
    }

    @Test
    void testSeparatorInComments() {
        Model model = ObjReader.read("v 0.5 0 1.1#1,6");
        VectorDimThree actual = model.getVertices().get(0);

        VectorDimThree expected = new VectorDimThree(0.5F, 0F, 1.1F);
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected, actual),
                () -> Assertions.assertEquals(1, model.getVerticesSize()),
                () -> Assertions.assertEquals(0, model.getTextureVerticesSize()),
                () -> Assertions.assertEquals(0, model.getNormalsSize())
        );
    }
}
