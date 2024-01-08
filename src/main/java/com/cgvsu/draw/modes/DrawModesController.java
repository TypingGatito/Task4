package com.cgvsu.draw.modes;

import com.cgvsu.components.SceneModels;
import com.cgvsu.components.model.Model;
import com.cgvsu.draw.light.LightParams;
import com.cgvsu.draw.light.LightUtils;
import com.cgvsu.draw.modes.interfaces.*;
import com.cgvsu.draw.rasterisation.Interpolation;
import com.cgvsu.draw.rasterisation.ZBuffer;
import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class DrawModesController {
    private boolean drawLight;
    private boolean drawTexture;
    private boolean drawMesh;
    private LightParams lightParams;
    private Color defaultFillColor;
    private Color meshColor;

    public DrawModesController(Color defaultFillColor, Color meshColor) {
        this.defaultFillColor = defaultFillColor;
        this.meshColor = meshColor;
    }

    public void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final SceneModels scene,
            final int width,
            final int height,
            final LightParams lightParams) {
        this.lightParams = lightParams;
        float[][] zBuffer = new float[width][height];
        ZBuffer.setBig(zBuffer);

        LighterCreator lighterCreator = makeLighterCreator();

        for (Model model: scene.getVisibleModels()) {
            BufferedImage image = scene.getModelTextureMap().get(model);
            PixelExtractorCreator pixelExtractorCreator = createPixelExtractorForModel(model, scene);

            RenderEngine.render(graphicsContext, camera, model, width, height, pixelExtractorCreator,
                    image, zBuffer, lightParams.getLightSource(), lighterCreator);

            if (drawMesh) RenderEngine.renderTexture(graphicsContext, camera, model, width, height, zBuffer, meshColor);
        }
    }

    private LighterCreator makeLighterCreator() {
        LighterCreator lighterCreator = new LighterCreator() {
            @Override
            public Lighter create(Vector3Interpolator rayInter, Vector3Interpolator normalInt) {
                return new Lighter() {
                    @Override
                    public Color light(Color pixel, float x, float y) {
                        return pixel;
                    }
                };
            }
        };

        if (drawLight) {
            lighterCreator = new LighterCreator() {
                @Override
                public Lighter create(Vector3Interpolator rayInter, Vector3Interpolator normalInt) {
                    return new Lighter() {
                        @Override
                        public Color light(Color pixel, float x, float y) {
                            VectorDimThree ray = rayInter.interpolate(x, y);
                            VectorDimThree normal = normalInt.interpolate(x, y);
                            float l = LightUtils.findL(ray, normal);
                            float k = (float) lightParams.getK();

                            if (l < 0) l = 0;

                            float red = (float) (pixel.getRed() * (1 - k) + pixel.getRed() * k * l);
                            float green = (float) (pixel.getGreen() * (1 - k) + pixel.getGreen() * k * l);
                            float blue = (float) (pixel.getBlue() * (1 - k) + pixel.getBlue() * k * l);

                            return Color.rgb((int) (red * 255), (int) (green * 255), (int) (blue * 255));
                        }
                    };
                }
            };
        }

        return lighterCreator;
    }

/*    private Lighter createLighter() {
        Lighter lighter = new Lighter() {
            @Override
            public Color light(Color pixel, VectorDimThree point, VectorDimThree normal) {
                return pixel;
            }
        };

        if (drawLight) {
            lighter = new Lighter() {
                @Override
                public Color light(Color pixel, VectorDimThree ray, VectorDimThree normal) {
                    float l = LightUtils.findL(ray, normal);
                    float k = (float) lightParams.getK();

                    if (l < 0) l = 0;

                    float red = (float) (pixel.getRed() * (1 - k) + pixel.getRed() * k * l);
                    float green = (float) (pixel.getGreen() * (1 - k) + pixel.getGreen() * k * l);
                    float blue = (float) (pixel.getBlue() * (1 - k) + pixel.getBlue() * k * l);

                    return Color.rgb((int) (red * 255), (int) (green * 255), (int) (blue * 255));
                }
            };
        }

        return lighter;
    }*/

    private PixelExtractorCreator createPixelExtractorForModel(Model model, SceneModels scene) {
        PixelExtractorCreator creator = new PixelExtractorCreator() {
            @Override
            public PixelExtractor create(VectorDimThree vertex1,
                                         VectorDimThree vertex2,
                                         VectorDimThree vertex3,
                                         VectorDimTwo vertex1T,
                                         VectorDimTwo vertex2T,
                                         VectorDimTwo vertex3T,
                                         BufferedImage image) {
                return new PixelExtractor() {
                    @Override
                    public Color getPixel(float x, float y) {
                        return defaultFillColor;
                    }
                };
            }
        };

        if (drawTexture && scene.getModelTextureMap().get(model) != null) {
            creator = new PixelExtractorCreator() {
                @Override
                public PixelExtractor create(VectorDimThree vertex1,
                                             VectorDimThree vertex2,
                                             VectorDimThree vertex3,
                                             VectorDimTwo vertex1T,
                                             VectorDimTwo vertex2T,
                                             VectorDimTwo vertex3T,
                                             BufferedImage image) {
                    return new PixelExtractor() {
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

                            int p = image.getRGB((int)sum.getX(), (int)sum.getY());
                            int red = (p>>16) & 0xff;
                            int green = (p>>8) & 0xff;
                            int blue = p & 0xff;
                            return Color.rgb(red, green, blue);
                        }
                    };
                }
            };
        }

        return creator;
    }

    public LightParams getLightParams() {
        return lightParams;
    }

    public Color getDefaultFillColor() {
        return defaultFillColor;
    }

    public Color getMeshColor() {
        return meshColor;
    }

    public boolean isDrawLight() {
        return drawLight;
    }

    public boolean isDrawTexture() {
        return drawTexture;
    }

    public boolean isDrawMesh() {
        return drawMesh;
    }

    public void setDrawLight(boolean drawLight) {
        this.drawLight = drawLight;
    }

    public void setDrawTexture(boolean drawTexture) {
        this.drawTexture = drawTexture;
    }

    public void setDrawMesh(boolean drawMesh) {
        this.drawMesh = drawMesh;
    }

    public void setLightParams(LightParams lightParams) {
        this.lightParams = lightParams;
    }

    public void setDefaultFillColor(Color defaultFillColor) {
        this.defaultFillColor = defaultFillColor;
    }

    public void setMeshColor(Color meshColor) {
        this.meshColor = meshColor;
    }
}
