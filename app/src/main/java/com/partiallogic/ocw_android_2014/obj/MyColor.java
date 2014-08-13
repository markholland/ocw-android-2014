package com.partiallogic.ocw_android_2014.obj;

import android.graphics.Color;

/**
 * Created by markholland on 13/08/14.
 */

public class MyColor {
   private Double r;
   private Double g;
   private Double b;

    public MyColor(Double r, Double g, Double b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public String getColor() {

        Double auxR = r*10;
        Double auxG = g*10;
        Double auxB = b*10;

        return ""+Color.argb(127, auxR.intValue(), auxG.intValue(), auxB.intValue());
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Double getG() {
        return g;
    }

    public void setG(Double g) {
        this.g = g;
    }

    public Double getB() {
        return b;
    }

    public void setB(Double b) {
        this.b = b;
    }
}
