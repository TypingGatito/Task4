package com.cgvsu.draw.modes.interfaces;

import javafx.scene.effect.Light;
import javafx.scene.paint.Color;

import java.util.List;

public interface Lighter {
    Color light(Color pixel, int x, int y, int z, List<Light> lights);
}
