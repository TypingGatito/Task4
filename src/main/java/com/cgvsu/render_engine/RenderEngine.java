package com.cgvsu.render_engine;

import java.awt.*;
import java.util.ArrayList;

import com.cgvsu.components.Scene;
import com.cgvsu.draw.light.LightParams;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;
import com.cgvsu.math.matrix.MatrixDimFour;
import com.cgvsu.math.vector.VectorMethods;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.components.model.Model;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height) {
        MatrixDimFour modelMatrix = GraphicConveyor.rotateScaleTranslate();
        MatrixDimFour viewMatrix = camera.getViewMatrix();
        MatrixDimFour projectionMatrix = camera.getProjectionMatrix();

        MatrixDimFour modelViewProjectionMatrix = new MatrixDimFour(modelMatrix.getM1());
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(modelViewProjectionMatrix, viewMatrix);
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(modelViewProjectionMatrix, projectionMatrix);

        final int nPolygons = mesh.getPolygons().size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.getPolygons().get(polygonInd).getVertexIndices().size();

            ArrayList<VectorDimTwo> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                VectorDimThree vertex = mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                VectorDimThree vertexVecmath = new VectorDimThree(vertex.getX(), vertex.getY(), vertex.getZ());

                VectorDimTwo resultPoint = VectorMethods.vertexToPoint(VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).getX(),
                        resultPoints.get(vertexInPolygonInd - 1).getY(),
                        resultPoints.get(vertexInPolygonInd).getX(),
                        resultPoints.get(vertexInPolygonInd).getY());
            }

            if (nVerticesInPolygon > 0)
                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).getX(),
                        resultPoints.get(nVerticesInPolygon - 1).getY(),
                        resultPoints.get(0).getX(),
                        resultPoints.get(0).getY());
        }
    }

    private static void renderMesh (final GraphicsContext graphicsContext,
                                    final Camera camera,
                                    final Model model,
                                    final int width,
                                    final int height,
                                    final float[][] zBuffer,
                                    final LightParams lightParams) {}

    private static void renderColor (final GraphicsContext graphicsContext,
                                     final Camera camera,
                                     final Model model,
                                     final int width,
                                     final int height,
                                     final float[][] zBuffer,
                                     final LightParams lightParams) {}
    private static void renderTexture (final GraphicsContext graphicsContext,
                                       final Camera camera,
                                       final Model model,
                                       final int width,
                                       final int height,
                                       final String textureFile,
                                       final float[][] zBuffer,
                                       final LightParams lightParams) {}

    private Color addLight (Color color, LightParams lightParams) {
        Color colorWithLight = new Color(color.getRGB());
        //change it

        return colorWithLight;
    }

    private static void renderSceneMesh (final GraphicsContext graphicsContext,
                                         final Camera camera,
                                         final Scene scene,
                                         final int width,
                                         final int height,
                                         final LightParams lightParams,
                                         final float[][] zBuffer) {
    }

    private static void renderSceneColor (final GraphicsContext graphicsContext,
                                          final Camera camera,
                                          final Scene scene,
                                          final int width,
                                          final int height,
                                          final LightParams lightParams,
                                          final float[][] zBuffer) {}
    private static void renderSceneTexture (final GraphicsContext graphicsContext,
                                            final Camera camera,
                                            final Scene scene,
                                            final int width,
                                            final int height,
                                            final String textureFile,
                                            final LightParams lightParams,
                                            final float[][] zBuffer) {}
}