package com.example.android.gramalab;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.AttributeSet;
import android.view.View;

import Utils.GraphicsUtils;

/**
 * Created by jasc9 on 3/4/2016.
 */
public class PetsView extends View {

    private VectorDrawableCompat svgFromSrc;
    private int svgWidth;
    private int svgHeight;

    private float WIDTH_POSITON_PORCENTAGE;
    private float HEIGHT_POSITON_PORCENTAGE;
    private float VECTOR_SCALABLE_PORCENTAGE;

    public PetsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PetsView(Context context, AttributeSet attribute) {
        super(context, attribute);
        int src_attr = attribute.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "src", 0);
        prepareCanvas(src_attr);
    }

    public PetsView(Context context) {
        super(context);
    }

    public void prepareCanvas(int pSrcAttr) {
        Resources res = getResources();
        svgFromSrc = VectorDrawableCompat.create(res, pSrcAttr, null);
        if (svgFromSrc != null) {
            svgWidth = svgFromSrc.getMinimumWidth() / 2;
            svgHeight = svgFromSrc.getMinimumHeight() / 2;
        }
    }

    public void setWIDTH_POSITON_PORCENTAGE(float pWeightPorcentage) {
        this.WIDTH_POSITON_PORCENTAGE = pWeightPorcentage;
    }

    public void setHEIGHT_POSITON_PORCENTAGE(float pHeightPorcentage) {
        this.HEIGHT_POSITON_PORCENTAGE = pHeightPorcentage;
    }

    public void setVECTOR_SCALABLE_PORCENTAGE(float pVectorScalablePorcetage) {
        this.VECTOR_SCALABLE_PORCENTAGE = pVectorScalablePorcetage;
    }

    @Override
    public void onDraw(Canvas pCanvas) {
        super.onDraw(pCanvas);
        pCanvas.drawARGB(0, 255, 255, 255);
        float canvasPosX = pCanvas.getWidth() - (pCanvas.getWidth() * WIDTH_POSITON_PORCENTAGE);
        float canvasPosY = pCanvas.getHeight() - (pCanvas.getHeight() * HEIGHT_POSITON_PORCENTAGE);
        pCanvas.translate(canvasPosX, canvasPosY);

        int newSvgWidth = Math.round(pCanvas.getWidth() * VECTOR_SCALABLE_PORCENTAGE);
        int newSvgHeight = GraphicsUtils.calculateAspectRatio(svgWidth, newSvgWidth, svgHeight);

        svgFromSrc.setBounds(0, 0, newSvgWidth, newSvgHeight);
        svgFromSrc.draw(pCanvas);
    }
}
