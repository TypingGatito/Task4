package com.cgvsu.draw.modes;

import com.cgvsu.components.Scene;
import com.cgvsu.render_engine.Camera;
import javafx.scene.canvas.GraphicsContext;

public class DrawModesController {
    private boolean drawLight;
    private boolean drawTexture;
    private boolean drawMesh;

    public void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final Scene scene,
            final int width,
            final int height) {

/*        interface PixelExtractor{
            Color getPixel(Model m, int x, int y);
        }
        interface Lighter{
            Color light(Color pixel, int x, int y, int z, List<Light> lights);
        }*/

        //вызов методов рендера в зависимотси от параметров
    }
}
