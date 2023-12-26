package com.cgvsu.draw.predraw;

import com.cgvsu.components.model.Model;
import com.cgvsu.components.model.ModelTriangulated;

public class ModelUtils {
    public static ModelTriangulated triangulateModel(Model model) {
        return new ModelTriangulated(model);
    }

    public static void updateNormals(Model model) {

    }
}
