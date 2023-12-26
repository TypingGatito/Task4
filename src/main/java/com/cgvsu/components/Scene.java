package com.cgvsu.components;

import com.cgvsu.components.model.Model;

import java.util.List;
import java.util.Map;

public class Scene {
    private Map<Model, ModelMatrixInfo> modelMatrixInfo;
    private List<Model> chosenModels;

    //getters
    public Map<Model, ModelMatrixInfo> getModelMatrixInfo() {
        return modelMatrixInfo;
    }

    public List<Model> getChosenModels() {
        return chosenModels;
    }

    //setters
    public void setModelMatrixInfo(Map<Model, ModelMatrixInfo> modelMatrixInfo) {
        this.modelMatrixInfo = modelMatrixInfo;
    }

    public void setChosenModels(List<Model> chosenModels) {
        this.chosenModels = chosenModels;
    }
}
