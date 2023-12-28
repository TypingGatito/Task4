package com.cgvsu.gui;

import com.cgvsu.components.model.Model;
import com.cgvsu.infoclasses.*;

public class ModelData {
    private String modelName;

    private Model model;
    private boolean active;
    private ModelMatrixInfo info;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ModelMatrixInfo getInfo() {
        return info;
    }

    public void setInfo(ModelMatrixInfo info) {
        this.info = info;
    }

    public ModelData(Model model, String modelName, boolean active) {
        this.model = model;
        this.modelName = modelName;
        this.active = active;
        this.info = new ModelMatrixInfo();
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}