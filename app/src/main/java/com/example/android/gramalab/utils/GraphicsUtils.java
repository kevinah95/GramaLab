package com.example.android.gramalab.utils;

/**
 * Created by jasc9 on 3/4/2016.
 */
public class GraphicsUtils {

    public static int calculateAspectRatio(int pOldSize, int pNewSize, int pOriginalOtherSize) {
        float floatOldSize = (float) (pOldSize);
        float floatNewSize = (float) (pNewSize);
        float floatOriginalOtherSize = (float) (pOriginalOtherSize);
        float precisionOtherSize =  floatOriginalOtherSize * (floatNewSize / floatOldSize);
        int newOtherSize = Math.round(precisionOtherSize);
        return newOtherSize;
    }
}
