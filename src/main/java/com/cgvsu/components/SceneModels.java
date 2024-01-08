package com.cgvsu.components;

import com.cgvsu.components.model.Model;
import com.cgvsu.infoclasses.ModelMatrixInfo;

import java.awt.image.BufferedImage;
import java.util.*;

public class SceneModels {
    private Map<Model, ModelMatrixInfo> modelMatrixInfoMap;
    private Map<Model, BufferedImage> modelTextureMap;
    private List<Model> activeModels;
    private List<Model> visibleModels;

    public SceneModels() {
        modelMatrixInfoMap = new HashMap<>();
        activeModels = new ArrayList<>();
        visibleModels = new ArrayList<>();
        modelTextureMap = new HashMap<>();
    }

    //methods to chosen
    public void scaleModels(final float sX, final float sY, final float sZ) {
        ModelMatrixInfo curMatrix;
        for (Model model : activeModels) {
            curMatrix = modelMatrixInfoMap.get(model);

            //maybe add smth in case curMatrix is null but that should not happen

            //change cur matrix
            curMatrix.increaseSX(sX);
            curMatrix.increaseSY(sY);
            curMatrix.increaseSZ(sZ);
        }
    }

    public void rotateModels(final float angleX, final float angleY, final float angleZ) {
        ModelMatrixInfo curMatrix;
        for (Model model : activeModels) {
            curMatrix = modelMatrixInfoMap.get(model);

            //maybe add smth in case curMatrix is null but that should not happen

            //change cur matrix
            curMatrix.increaseAngleX(angleX);
            curMatrix.increaseAngleY(angleY);
            curMatrix.increaseAngleZ(angleZ);
        }
    }

    public void translateModels(final float dX, final float dY, final float dZ) {
        ModelMatrixInfo curMatrix;
        for (Model model : activeModels) {
            curMatrix = modelMatrixInfoMap.get(model);

            //maybe add smth in case curMatrix is null but that should not happen

            //change cur matrix
            curMatrix.increaseDX(dX);
            curMatrix.increaseDY(dY);
            curMatrix.increaseDZ(dZ);
        }
    }

    //outer methods
    public void addModelMatrixInfo(final Model model, final ModelMatrixInfo modelMatrixInfo) {
        modelMatrixInfoMap.put(model, modelMatrixInfo);
    }

    public void addModel(final Model model) {
        modelMatrixInfoMap.put(model, new ModelMatrixInfo());
    }

    public void makeModelActive(final Model model) {
        if (!modelMatrixInfoMap.keySet().contains(model)) modelMatrixInfoMap.put(model, new ModelMatrixInfo());
        activeModels.add(model);
    }
    public void seeModel(final Model model) {
        if (!modelMatrixInfoMap.keySet().contains(model)) modelMatrixInfoMap.put(model, new ModelMatrixInfo());
        visibleModels.add(model);
    }

    public void unChooseModel(final Model model) {
        if (!activeModels.contains(model)) return;
        activeModels.remove(model);
    }

    public void addTexture(Model model, BufferedImage image) {
        modelTextureMap.put(model, image);
    }

    //getters

    public Map<Model, BufferedImage> getModelTextureMap() {
        return modelTextureMap;
    }

    public Map<Model, ModelMatrixInfo> getModelMatrixInfoMap() {
        return modelMatrixInfoMap;
    }

    public List<Model> getActiveModels() {
        return activeModels;
    }

    public List<Model> getVisibleModels() {
        return visibleModels;
    }

    //setters

    public void setModelMatrixInfoMap(final Map<Model, ModelMatrixInfo> modelMatrixInfoMap) {
        this.modelMatrixInfoMap = modelMatrixInfoMap;
    }
}
