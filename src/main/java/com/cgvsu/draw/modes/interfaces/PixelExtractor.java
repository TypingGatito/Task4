package com.cgvsu.draw.modes.interfaces;

import com.cgvsu.components.model.Model;

import java.awt.*;

public interface PixelExtractor {
    Color getPixel(Model m, int x, int y);
}
