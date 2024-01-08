package com.cgvsu.render_engine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.cgvsu.draw.light.LightUtils;
import com.cgvsu.draw.modes.interfaces.*;
import com.cgvsu.draw.rasterisation.DrawLine;
import com.cgvsu.draw.rasterisation.Interpolation;
import com.cgvsu.draw.rasterisation.TriangleRasterisationFull;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;
import com.cgvsu.math.matrix.MatrixDimFour;
import com.cgvsu.math.vector.VectorMethods;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.components.model.Model;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class RenderEngine {
    ////with fill
    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height,
            final PixelExtractorCreator pixelExtractorCreator,
            //final Lighter lighter,
            final BufferedImage image,
            final float[][] zBuffer,
            VectorDimThree lightSource,
            final LighterCreator lighterCreator) {

        MatrixDimFour modelMatrix = GraphicConveyor.rotateScaleTranslate();
        MatrixDimFour viewMatrix = camera.getViewMatrix();
        MatrixDimFour projectionMatrix = camera.getProjectionMatrix();

        MatrixDimFour modelViewProjectionMatrix = new MatrixDimFour(modelMatrix.getM1());
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(viewMatrix, modelViewProjectionMatrix);
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(projectionMatrix, modelViewProjectionMatrix);



        final int nPolygons = mesh.getPolygons().size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            VectorDimThree n1 = mesh.normals.get(mesh.getPolygons().get(polygonInd).getNormalIndices().get(0));
            VectorDimThree n2 = mesh.normals.get(mesh.getPolygons().get(polygonInd).getNormalIndices().get(1));
            VectorDimThree n3 = mesh.normals.get(mesh.getPolygons().get(polygonInd).getNormalIndices().get(2));

            VectorDimThree vertex1 = VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix,
                    mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(0)));
            VectorDimThree vertex2 = VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix,
                    mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(1)));
            VectorDimThree vertex3 = VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix,
                    mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(2)));

            VectorDimThree vertex1W = mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(0));
            VectorDimThree vertex2W = mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(1));
            VectorDimThree vertex3W = mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(2));

            VectorDimTwo vertex1T = mesh.getTextureVertices().get(mesh.getPolygons().get(polygonInd).getTextureVertexIndices().get(0));
            VectorDimTwo vertex2T = mesh.getTextureVertices().get(mesh.getPolygons().get(polygonInd).getTextureVertexIndices().get(1));
            VectorDimTwo vertex3T = mesh.getTextureVertices().get(mesh.getPolygons().get(polygonInd).getTextureVertexIndices().get(2));

            PixelExtractor pixelExtractor = pixelExtractorCreator.create(vertex1, vertex2, vertex3,
                    vertex1T, vertex2T, vertex3T,
                    image);
            Vector3Interpolator normalInterpolator = new Vector3Interpolator() {
                @Override
                public VectorDimThree interpolate(float x, float y) {
                    return VectorDimThree.normalize(Interpolation.interpolateVectorDimThree(vertex1, n1,
                            vertex2, n2, vertex3, n3, x, y));
                }
            };

            Vector3Interpolator rayInterpolator = new Vector3Interpolator() {
                @Override
                public VectorDimThree interpolate(float x, float y) {

                    return VectorDimThree.normalize(Interpolation.interpolateVectorDimThree(vertex1,
                            LightUtils.findRay(lightSource, vertex1W),
                            vertex2,
                            LightUtils.findRay(lightSource, vertex2W),
                            vertex3,
                            LightUtils.findRay(lightSource, vertex3W),
                            x, y));
                }
            };

            Lighter lighter = lighterCreator.create(rayInterpolator, normalInterpolator);


            TriangleRasterisationFull rasterisation = new TriangleRasterisationFull(graphicsContext.getPixelWriter(),
                    zBuffer, lighter, pixelExtractor, width, height);

            rasterisation.rasterizeTriangle(vertex1, vertex2, vertex3);
        }
    }

    ////mesh
    public static void renderTexture(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height,
            float[][] zBuffer,
            Color color) {
        PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        MatrixDimFour modelMatrix = GraphicConveyor.rotateScaleTranslate();
        MatrixDimFour viewMatrix = camera.getViewMatrix();
        MatrixDimFour projectionMatrix = camera.getProjectionMatrix();

        MatrixDimFour modelViewProjectionMatrix = new MatrixDimFour(modelMatrix.getM1());
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(viewMatrix, modelViewProjectionMatrix);
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(projectionMatrix, modelViewProjectionMatrix);


        final int nPolygons = mesh.getPolygons().size();
        for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
            final int nVerticesInPolygon = mesh.getPolygons().get(polygonInd).getVertexIndices().size();

            ArrayList<VectorDimThree> resultPoints = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                VectorDimThree vertex = mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                VectorDimThree vertexVecmath = new VectorDimThree(vertex.getX(), vertex.getY(), vertex.getZ());

                VectorDimThree resultPoint = VectorMethods.vertexToPoint3(VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                DrawLine.drawLine(zBuffer, (int) resultPoints.get(vertexInPolygonInd - 1).getX(),
                        (int) resultPoints.get(vertexInPolygonInd - 1).getY(),
                         resultPoints.get(vertexInPolygonInd - 1).getZ(),
                        (int) resultPoints.get(vertexInPolygonInd).getX(),
                        (int) resultPoints.get(vertexInPolygonInd).getY(),
                        resultPoints.get(vertexInPolygonInd).getZ(),
                        pixelWriter, color,
                        width, height);
/*                graphicsContext.strokeLine(
                        resultPoints.get(vertexInPolygonInd - 1).getX(),
                        resultPoints.get(vertexInPolygonInd - 1).getY(),
                        resultPoints.get(vertexInPolygonInd).getX(),
                        resultPoints.get(vertexInPolygonInd).getY());*/
            }

            if (nVerticesInPolygon > 0)
                DrawLine.drawLine(zBuffer, (int) resultPoints.get(nVerticesInPolygon - 1).getX(),
                        (int) resultPoints.get(nVerticesInPolygon - 1).getY(),
                        resultPoints.get(nVerticesInPolygon - 1).getZ(),
                        (int) resultPoints.get(0).getX(),
                        (int) resultPoints.get(0).getY(),
                        resultPoints.get(0).getZ(),
                        pixelWriter, color,
                        width, height);
/*                graphicsContext.strokeLine(
                        resultPoints.get(nVerticesInPolygon - 1).getX(),
                        resultPoints.get(nVerticesInPolygon - 1).getY(),
                        resultPoints.get(0).getX(),
                        resultPoints.get(0).getY());*/
        }
    }

}