package com.example.android.gramalab.views.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import com.example.android.gramalab.R;

/**
 * Created by jasc9 on 22/4/2016.
 */
public class DrawButtonView extends View {

    private Context context;

    private ImageButton buttonImg;

    private int width;
    private int heigth;

    private float widthPosPorcent;
    private float heightPosPorcent;

    public DrawButtonView(Context context) {
        super(context);
        this.context = context;
    }

    public DrawButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setButton(int pImage, float pXPos, float pYPos) {
        widthPosPorcent = pXPos;
        heightPosPorcent = pYPos;

        buttonImg = new ImageButton(context);
        buttonImg.setImageResource(pImage);
        width = buttonImg.getWidth();
        heigth = buttonImg.getHeight();
    }

    public int getButtonWidth() {
        return width;
    }

    public int getButtonHeigth() {
        return heigth;
    }

    @Override
    protected void onDraw(Canvas pCanvas) {
        super.onDraw(pCanvas);

        pCanvas.drawARGB(255, 255, 255, 255);
        float canvasPosX = pCanvas.getWidth() - (pCanvas.getWidth() * widthPosPorcent);
        float canvasPosY = pCanvas.getHeight() - (pCanvas.getHeight() * heightPosPorcent);
        pCanvas.translate(canvasPosX, canvasPosY);

        buttonImg.draw(pCanvas);
    }
}
