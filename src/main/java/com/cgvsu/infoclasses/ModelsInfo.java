package com.cgvsu.infoclasses;

import com.cgvsu.components.model.Model;

import java.util.HashMap;
import java.util.Map;

public class ModelsInfo {
    private Map<Model, Model> modelTriangulatedModelMap;
    private Map<Model, String> madelFilenameMap;

    public ModelsInfo() {
        modelTriangulatedModelMap = new HashMap<>();
        madelFilenameMap = new HashMap<>();
    }

    //outer methods
    public void addModel(final Model model) {
        if (!modelTriangulatedModelMap.containsKey(model)) modelTriangulatedModelMap.put(model, null);
        if (!madelFilenameMap.containsKey(model)) madelFilenameMap.put(model, null);
    }

    public void addModelTriangulated(final Model model, final Model modelTriangulated) {
        if (!modelTriangulatedModelMap.containsKey(model)) modelTriangulatedModelMap.put(model, modelTriangulated);
//        if (!madelFilenameMap.containsKey(model)) madelFilenameMap.put(model, null); // из-за этой строчки вылезали ошибки
    }

    public void addModelFilename(final Model model, final String filename) {
//        if (!modelTriangulatedModelMap.containsKey(model)) modelTriangulatedModelMap.put(model, null); // и из-за этой
        if (!madelFilenameMap.containsKey(model)) madelFilenameMap.put(model, filename);
    }

    //getters
    public Map<Model, Model> getModelTriangulatedModelMap() {
        return modelTriangulatedModelMap;
    }

    public Map<Model, String> getMadelFilenameMap() {
        return madelFilenameMap;
    }

    //setters
    public void setModelTriangulatedModelMap(final Map<Model, Model> modelTriangulatedModelMap) {
        this.modelTriangulatedModelMap = modelTriangulatedModelMap;
    }

    public void setMadelFilenameMap(final Map<Model, String> madelFilenameMap) {
        this.madelFilenameMap = madelFilenameMap;
    }
}
