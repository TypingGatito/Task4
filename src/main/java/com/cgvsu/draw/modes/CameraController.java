package com.cgvsu.draw.modes;

import com.cgvsu.math.vector.VectorDimThree;
import com.cgvsu.render_engine.Camera;

import java.util.ArrayList;
import java.util.List;

public class CameraController {
    private List<Camera> cameras;
    private Camera curCamera;

    public CameraController() {
        this.cameras = new ArrayList<>();
    }

    //outer methods
    public void addCamera(Camera camera) {
        if (cameras == null) cameras = new ArrayList<>();
        cameras.add(camera);
    }

    public void chooseCamera(Camera camera) {
        if (!cameras.contains(camera)) cameras.add(camera);
        curCamera = camera;
    }

    public void chooseCamera(int cameraInd) {
        if (cameraInd >= cameras.size()) return;
        curCamera = cameras.get(cameraInd);
    }

    public void removeCamera(Camera camera) {
        cameras.remove(camera);
        if (curCamera.equals(camera)) {
            curCamera = null;
        }
    }

    public void removeCamera(int cameraInd) {
        if (cameraInd >= cameras.size()) return;
        curCamera = cameras.remove(cameraInd);
    }

    //actions with cur camera
    public void moveCurCameraPosition(VectorDimThree translateVector) {
        curCamera.movePosition(translateVector);
    }

    public void moveCurCameraTarget(VectorDimThree translateVector) {
        curCamera.moveTarget(translateVector);
    }

    //getters
    public List<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(List<Camera> cameras) {
        if (cameras == null) return;
        this.cameras = cameras;
    }
    //setters

    public Camera getCurCamera() {
        return curCamera;
    }

    public void setCurCamera(Camera curCamera) {
        this.curCamera = curCamera;
    }
}
