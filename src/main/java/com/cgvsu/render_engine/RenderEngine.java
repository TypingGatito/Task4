package com.cgvsu.render_engine;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.cgvsu.components.SceneModels;
import com.cgvsu.components.model.ModelTriangulated;
import com.cgvsu.draw.light.LightUtils;
import com.cgvsu.draw.modes.interfaces.Lighter;
import com.cgvsu.draw.modes.interfaces.PixelExtractorCreator;
import com.cgvsu.draw.modes.interfaces.Vector3Interpolator;
import com.cgvsu.draw.modes.interfaces.PixelExtractor;
import com.cgvsu.draw.predraw.ModelUtils;
import com.cgvsu.draw.rasterisation.Interpolation;
import com.cgvsu.draw.rasterisation.TriangleRasterisationFull;
import com.cgvsu.draw.rasterisation.ZBuffer;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;
import com.cgvsu.math.matrix.MatrixDimFour;
import com.cgvsu.math.vector.VectorMethods;
import javafx.scene.canvas.GraphicsContext;
import com.cgvsu.components.model.Model;
import javafx.scene.paint.Color;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh1,
            final int width,
            final int height) {

        float[][] zBuffer = new float[width][height];
        ZBuffer.setBig(zBuffer);

        Model mesh = new ModelTriangulated(mesh1);
        ModelUtils.updateNormals(mesh);

        MatrixDimFour modelMatrix = GraphicConveyor.rotateScaleTranslate();
        MatrixDimFour viewMatrix = camera.getViewMatrix();
        MatrixDimFour projectionMatrix = camera.getProjectionMatrix();


/*        MatrixDimFour modelViewProjectionMatrix = new MatrixDimFour(modelMatrix.getM1());
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(modelViewProjectionMatrix, viewMatrix);
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(modelViewProjectionMatrix, projectionMatrix);*/
        MatrixDimFour modelViewProjectionMatrix = new MatrixDimFour(modelMatrix.getM1());
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(viewMatrix, modelViewProjectionMatrix);
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(projectionMatrix, modelViewProjectionMatrix);


        Lighter lighter = new Lighter() {
            @Override
            public javafx.scene.paint.Color light(Color pixel, VectorDimThree ray, VectorDimThree normal) {
                float l = LightUtils.findL(ray, normal);
                float k = (float) 0.4;

                if (l < 0) l = 0;

                float red = (float) (pixel.getRed() * (1 - k) + pixel.getRed() * k * l);
                float green = (float) (pixel.getGreen() * (1 - k) + pixel.getGreen() * k * l);
                float blue = (float) (pixel.getBlue() * (1 - k) + pixel.getBlue() * k * l);

                return Color.rgb((int) (red * 255), (int) (green * 255), (int) (blue * 255));
            }
        };

        PixelExtractor pixelExtractor = new PixelExtractor() {
            @Override
            public Color getPixel(float x, float y) {
                return Color.BLUE;
            }
        };


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
/*
            System.out.println(vertex1.toStr() + " v1 " + n1.toStr() + " n1 ");

            System.out.println(VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex1)
                    .toStr() + " v2 " + VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, n1)
                    .toStr() + " n2 ");*/

            Vector3Interpolator vector3Interpolator = new Vector3Interpolator() {
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
                            LightUtils.findRay(camera.getPosition(), vertex1W),
                            vertex2,
                            LightUtils.findRay(camera.getPosition(), vertex2W),
                            vertex3,
                            LightUtils.findRay(camera.getPosition(), vertex3W),
                            x, y));
                }
            };


            TriangleRasterisationFull rasterisation = new TriangleRasterisationFull(graphicsContext.getPixelWriter(),
                    zBuffer, lighter, pixelExtractor, width, height, vector3Interpolator, rayInterpolator);

            rasterisation.rasterizeTriangle(vertex1, vertex2, vertex3);

/*            rasterisation.rasterizeTriangle(VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex1),
                    VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex2),
                    VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex3));*/


            final int nVerticesInPolygon = mesh.getPolygons().get(polygonInd).getVertexIndices().size();

            ArrayList<VectorDimTwo> resultPoints = new ArrayList<>();
            ArrayList<VectorDimThree> resultPoints3F = new ArrayList<>();
            for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                VectorDimThree vertex = mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                VectorDimThree vertexVecmath = new VectorDimThree(vertex.getX(), vertex.getY(), vertex.getZ());
                //resultPoints3F.add(vertexVecmath);
                resultPoints3F.add(VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath));

                VectorDimTwo resultPoint = VectorMethods.vertexToPoint(VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                resultPoints.add(resultPoint);
            }

            //rasterisation.rasterizeTriangle(resultPoints3F.get(0), resultPoints3F.get(1), resultPoints3F.get(2));


/*            for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
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
                        resultPoints.get(0).getY());*/
        }
    }

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh1,
            final int width,
            final int height, BufferedImage image) {

        float[][] zBuffer = new float[width][height];
        ZBuffer.setBig(zBuffer);

        Model mesh = new ModelTriangulated(mesh1);
        ModelUtils.updateNormals(mesh);

        MatrixDimFour modelMatrix = GraphicConveyor.rotateScaleTranslate();
        MatrixDimFour viewMatrix = camera.getViewMatrix();
        MatrixDimFour projectionMatrix = camera.getProjectionMatrix();


/*        MatrixDimFour modelViewProjectionMatrix = new MatrixDimFour(modelMatrix.getM1());
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(modelViewProjectionMatrix, viewMatrix);
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(modelViewProjectionMatrix, projectionMatrix);*/
        MatrixDimFour modelViewProjectionMatrix = new MatrixDimFour(modelMatrix.getM1());
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(viewMatrix, modelViewProjectionMatrix);
        modelViewProjectionMatrix = MatrixDimFour.multMatrix(projectionMatrix, modelViewProjectionMatrix);


        Lighter lighter = new Lighter() {
            @Override
            public javafx.scene.paint.Color light(Color pixel, VectorDimThree ray, VectorDimThree normal) {
                float l = LightUtils.findL(ray, normal);
                float k = (float) 0.4;

                if (l < 0) l = 0;

                float red = (float) (pixel.getRed() * (1 - k) + pixel.getRed() * k * l);
                float green = (float) (pixel.getGreen() * (1 - k) + pixel.getGreen() * k * l);
                float blue = (float) (pixel.getBlue() * (1 - k) + pixel.getBlue() * k * l);

                return Color.rgb((int) (red * 255), (int) (green * 255), (int) (blue * 255));
            }
        };


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

/*
            System.out.println(vertex1.toStr() + " v1 " + n1.toStr() + " n1 ");

            System.out.println(VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex1)
                    .toStr() + " v2 " + VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, n1)
                    .toStr() + " n2 ");*/
            PixelExtractor pixelExtractor = new PixelExtractor() {
                @Override
                public Color getPixel(float x, float y) {
                    VectorDimTwo v1 = new VectorDimTwo(vertex1.getX(), vertex1.getY());
                    VectorDimTwo v2 = new VectorDimTwo(vertex2.getX(), vertex2.getY());
                    VectorDimTwo v3 = new VectorDimTwo(vertex3.getX(), vertex3.getY());

                    VectorDimThree b = Interpolation.calculateBarycentric(v1,
                            v2,
                            v3,
                            new VectorDimTwo(x, y));


                    VectorDimTwo v1T = VectorDimTwo.multiplyByScalar(b.getX(), vertex1T);
                    VectorDimTwo v2T = VectorDimTwo.multiplyByScalar(b.getY(), vertex2T);
                    VectorDimTwo v3T = VectorDimTwo.multiplyByScalar(b.getZ(), vertex3T);

                    VectorDimTwo sum = VectorDimTwo.sumVector(v1T, VectorDimTwo.sumVector(v2T, v3T));

/*                    System.out.println(v2.toString());
                    System.out.println(v3.toString());*/
                    //System.out.println(new VectorDimTwo(x, y).toString());
                    //System.out.println(image.getWidth() + " W" + image.getHeight() + " H");
                    //System.out.println(sum.toString());
                    //System.out.println(b.getX() + " " + b.getY() + " " + b.getZ());
                    sum.setX(sum.getX() * image.getWidth());
                    sum.setY(image.getHeight() - sum.getY() * image.getHeight());

                    if (sum.getX() >= image.getWidth() || sum.getX() <= 0 ||
                            sum.getY() >= image.getHeight() || sum.getY() <= 0) {
                        if (b.getX() < 0 || b.getY() < 0 || b.getZ() < 0) {
                            b.setX((float) 1/3);
                            b.setY((float) 1/3);
                            b.setZ((float) 1/3);
                        }

                        v1T = VectorDimTwo.multiplyByScalar(b.getX(), vertex1T);
                        v2T = VectorDimTwo.multiplyByScalar(b.getY(), vertex2T);
                        v3T = VectorDimTwo.multiplyByScalar(b.getZ(), vertex3T);

                        sum = VectorDimTwo.sumVector(v1T, VectorDimTwo.sumVector(v2T, v3T));
                    }


             /*       System.out.println(image.getWidth() + " W " + image.getHeight() + " H");
                    System.out.println(sum.toString());
*/
                    int p = image.getRGB((int)sum.getX(), (int)sum.getY());
                    int red = (p>>16) & 0xff;
                    int green = (p>>8) & 0xff;
                    int blue = p & 0xff;
                    return Color.rgb(red, green, blue);
                }
            };

            Vector3Interpolator vector3Interpolator = new Vector3Interpolator() {
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
                            LightUtils.findRay(camera.getPosition(), vertex1W),
                            vertex2,
                            LightUtils.findRay(camera.getPosition(), vertex2W),
                            vertex3,
                            LightUtils.findRay(camera.getPosition(), vertex3W),
                            x, y));
                }
            };


            TriangleRasterisationFull rasterisation = new TriangleRasterisationFull(graphicsContext.getPixelWriter(),
                    zBuffer, lighter, pixelExtractor, width, height, vector3Interpolator, rayInterpolator);

            rasterisation.rasterizeTriangle(vertex1, vertex2, vertex3);

/*            rasterisation.rasterizeTriangle(VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex1),
                    VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex2),
                    VectorMethods.multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertex3));*/


            final int nVerticesInPolygon = mesh.getPolygons().get(polygonInd).getVertexIndices().size();

            ArrayList<VectorDimTwo> resultPoints = new ArrayList<>();
            ArrayList<VectorDimThree> resultPoints3F = new ArrayList<>();

        }
    }

    ////
    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Model mesh1,
            final int width,
            final int height,
            final PixelExtractorCreator pixelExtractorCreator,
            final Lighter lighter,
            final BufferedImage image,
            final float[][] zBuffer,
            VectorDimThree lightSource) {

        Model mesh = new ModelTriangulated(mesh1);
        ModelUtils.updateNormals(mesh);

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
            Vector3Interpolator vector3Interpolator = new Vector3Interpolator() {
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


            TriangleRasterisationFull rasterisation = new TriangleRasterisationFull(graphicsContext.getPixelWriter(),
                    zBuffer, lighter, pixelExtractor, width, height, vector3Interpolator, rayInterpolator);

            rasterisation.rasterizeTriangle(vertex1, vertex2, vertex3);
        }
    }

}