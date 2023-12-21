package com.cgvsu.drawmodes.predraw;

import com.cgvsu.modelcomponents.model.Model;
import com.cgvsu.modelcomponents.model.ModelTriangulated;

public class ModelUtils {
    public static ModelTriangulated triangulateModel(Model model) {
        return new ModelTriangulated(model);
    }

    public static void updateNormals(Model model) {

    }
}
