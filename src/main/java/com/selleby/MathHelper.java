package com.selleby;

public class MathHelper {

    public static double normalizeToRange(double val,double min,double max) {
        return MathHelper.clamp(((val - min) / (max - min)),0,1);
    }

    public static double clamp(double value,double min,double max) {
        return Math.max(Math.min(value,max),min );
    }


    /** Linear conversion. */
    static double changeToNewRange(double val, double oldMin,double oldMax,double newMin,double newMax) {
        val = MathHelper.clamp(val,oldMin, oldMax);
            double oldRange = oldMax - oldMin;
            double newRange = newMax - newMin;
        if (oldMin == oldMax){
            return MathHelper.clamp(val,newMin,newMax); //could go with any value tbh.
        }
        if (newMin == newMax) {
            return newMin;
        }
        return ((val - oldMin) * newRange) / oldRange + newMin;
    }

    public static double lerpNumber(double v0,double v1,double t ) {
        return v0 + t * (v1 - v0);
    }

}
