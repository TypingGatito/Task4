package com.cgvsu.components;

import com.cgvsu.components.model.Model;
import com.cgvsu.infoclasses.ModelMatrixInfo;

import java.util.List;
import java.util.Map;

public class Scene {
    private Map<Model, ModelMatrixInfo> modelMatrixInfoMap;
    private List<Model> chosenModels;
    //methods to chosen
    public void scaleModels(final float sX, final float sY, final float sZ) {
        ModelMatrixInfo curMatrix;
        for (Model model: chosenModels) {
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
        for (Model model: chosenModels) {
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
        for (Model model: chosenModels) {
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

    public void chooseModel(final Model model) {
        if (!modelMatrixInfoMap.keySet().contains(model)) modelMatrixInfoMap.put(model, new ModelMatrixInfo());
        chosenModels.add(model);
    }

    public void unChooseModel(final Model model) {
        if (!chosenModels.contains(model)) return;
        chosenModels.remove(model);
    }

    //getters
    public Map<Model, ModelMatrixInfo> getModelMatrixInfoMap() {
        return modelMatrixInfoMap;
    }

    public List<Model> getChosenModels() {
        return chosenModels;
    }

    //setters
    public void setModelMatrixInfoMap(final Map<Model, ModelMatrixInfo> modelMatrixInfoMap) {
        this.modelMatrixInfoMap = modelMatrixInfoMap;
    }

    public void setChosenModels(final List<Model> chosenModels) {
        this.chosenModels = chosenModels;
    }
}
