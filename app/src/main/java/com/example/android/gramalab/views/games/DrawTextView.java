package com.example.android.gramalab.views.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by jasc9 on 19/4/2016.
 */
public class DrawTextView extends View {

    private Context context;

    private String font;
    private String text;
    private float size;
    private boolean center;
    private int margin = 0;
    private float parentSize;
    private float parentInit;

    private float WIDTH_POSITON_PORCENTAGE;
    private float HEIGHT_POSITON_PORCENTAGE;


    public DrawTextView(Context context) {
        super(context);
        this.context = context;
    }

    public DrawTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setText(String pText, String pFont, float pSize, float pWidthPorcent, float pHeightPorcent, boolean pCenter, int pMargin, float pParentSize, float pParentInit) {
        this.font = pFont;
        this.text = pText;
        this.size = pSize;
        this.WIDTH_POSITON_PORCENTAGE = pWidthPorcent;
        this.HEIGHT_POSITON_PORCENTAGE = pHeightPorcent;
        this.center = pCenter;
        this.margin = pMargin;
        this.parentSize = pParentSize;
        this.parentInit = pParentInit;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public void onDraw(Canvas pCanvas) {
        super.onDraw(pCanvas);
        pCanvas.drawARGB(0, 255, 255, 255);

        Typeface newFont = Typeface.createFromAsset(context.getAssets(), font);
        Paint paint = new Paint();
        paint.setTypeface(newFont);
        paint.setTextSize(size);
        float textWidth = paint.measureText(text);
        Log.d("Parent Size: ", "" + parentSize);
        while (textWidth >= parentSize - margin) {
            size -= 1;
            paint.setTextSize(size);
            textWidth = paint.measureText(text);
        }
        float canvasPosX;
        if(center) {
            canvasPosX = (parentInit + parentSize/2) - textWidth/2;
        } else {
            canvasPosX = pCanvas.getWidth() - (pCanvas.getWidth() * WIDTH_POSITON_PORCENTAGE);
        }
        float canvasPosY = pCanvas.getHeight() - (pCanvas.getHeight() * HEIGHT_POSITON_PORCENTAGE);
        pCanvas.translate(canvasPosX, canvasPosY);
        pCanvas.drawText(text, 0, 0, paint);
    }
}
