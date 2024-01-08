package com.cgvsu.draw.modes.interfaces;


public interface LighterCreator {
    Lighter create(Vector3Interpolator rayInter, Vector3Interpolator normalInt);
}
