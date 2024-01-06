package com.cgvsu.draw.modes.interfaces;

import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.math.vector.VectorDimTwo;

import java.awt.image.BufferedImage;

public interface PixelExtractorCreator {
    public PixelExtractor create(VectorDimThree vertex1,
                                 VectorDimThree vertex2,
                                 VectorDimThree vertex3,
                                 VectorDimTwo vertex1T,
                                 VectorDimTwo vertex2T,
                                 VectorDimTwo vertex3T,
                                 BufferedImage image);
}
