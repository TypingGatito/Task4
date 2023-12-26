package com.cgvsu.infoclasses;

import com.cgvsu.components.model.Model;
import com.cgvsu.components.model.ModelTriangulated;

import java.util.HashMap;
import java.util.Map;

public class ModelsInfo {
    private Map<Model, ModelTriangulated> madelTriangulatedModelMap;
    private Map<Model, String> madelFilenameMap;

    public ModelsInfo() {
        madelTriangulatedModelMap = new HashMap<>();
        madelFilenameMap = new HashMap<>();
    }

    //outer methods
    public void addModel(final Model model) {
        if (!madelTriangulatedModelMap.keySet().contains(model)) madelTriangulatedModelMap.put(model, null);
        if (!madelFilenameMap.keySet().contains(model)) madelFilenameMap.put(model, null);
    }

    public void addModelTriangulated(final Model model, final ModelTriangulated modelTriangulated) {
        if (!madelTriangulatedModelMap.keySet().contains(model)) madelTriangulatedModelMap.put(model, modelTriangulated);
        if (!madelFilenameMap.keySet().contains(model)) madelFilenameMap.put(model, null);
    }

    public void addModelFilename(final Model model, final String filename) {
        if (!madelTriangulatedModelMap.keySet().contains(model)) madelTriangulatedModelMap.put(model, null);
        if (!madelFilenameMap.keySet().contains(model)) madelFilenameMap.put(model, filename);
    }

    //getters
    public Map<Model, ModelTriangulated> getMadelTriangulatedModelMap() {
        return madelTriangulatedModelMap;
    }

    public Map<Model, String> getMadelFilenameMap() {
        return madelFilenameMap;
    }

    //setters
    public void setMadelTriangulatedModelMap(final Map<Model, ModelTriangulated> madelTriangulatedModelMap) {
        this.madelTriangulatedModelMap = madelTriangulatedModelMap;
    }

    public void setMadelFilenameMap(final Map<Model, String> madelFilenameMap) {
        this.madelFilenameMap = madelFilenameMap;
    }
}
