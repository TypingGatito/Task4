package com.cgvsu.draw.modes.interfaces;

import com.cgvsu.draw.light.LightParams;
import com.cgvsu.math.vector.VectorDimThree;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;

import java.util.List;

public interface Lighter {
    //Color light(Color pixel, VectorDimThree point, List<Light> lights, VectorDimThree normal);
    Color light(Color pixel, VectorDimThree point, VectorDimThree normal);
}
