package com.cgvsu.render_engine;

import java.util.ArrayList;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;
import com.cgvsu.math.matrix.MatrixDimFour;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.modelcomponents.model.Model;
import static com.cgvsu.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh,
            final int width,
            final int height)
    {
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

                VectorDimTwo resultPoint = GraphicConveyor.vertexToPoint(GraphicConveyor.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
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
}