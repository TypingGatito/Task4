package com.cgvsu.infoclasses;

import com.cgvsu.components.model.Model;

public class ModelData {
    private String modelName;

    private Model model;
    private boolean active;
    private boolean visible;
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
        this.visible = true;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}