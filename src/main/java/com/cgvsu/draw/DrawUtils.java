package com.cgvsu.draw;

public class DrawUtils {
    public static float xToDraw(float x, final int width) {
        return x * (width - 1)/2F + (width-1) / 2.0F;
    }
    public static float xToBack(float x, final int width) {
        return (x - (width-1) / 2.0F) * 2F/ (width - 1);
    }

    public static float yToDraw(float y, final int height) {
        return y * (1 - height)/2F + (height - 1) / 2.0F;
    }

    public static float yToBack(float y, final int height) {
        return (y - (height - 1) / 2.0F) * 2F / (1 - height);
    }
}
