package com.fox.myday.base;

import android.view.animation.Interpolator;

public class MyBounceInterpolator implements Interpolator {

    private double mAmplitude = 1.0;
    private double mFrequency = 10.0;

    public MyBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }

}
