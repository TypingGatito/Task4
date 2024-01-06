package com.cgvsu.draw.modes.interfaces;

import com.cgvsu.components.model.Model;

import java.awt.*;
import javafx.scene.paint.Color;

public interface PixelExtractor {
    Color getPixel(float x, float y);
}
