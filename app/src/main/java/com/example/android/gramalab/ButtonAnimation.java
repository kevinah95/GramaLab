package com.example.android.gramalab;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

/**
 * Created by jasc9 on 19/4/2016.
 */
public abstract class ButtonAnimation {

    protected Animation animTranslate;
    protected Animation animTranslateInverted;
    protected Animation animTranslateY;
    protected Animation animTranslateYInverted;

    protected ButtonAnimation(Context pContext) {
        animTranslate = AnimationUtils.loadAnimation(pContext, R.anim.anim_translate);
        animTranslateInverted = AnimationUtils.loadAnimation(pContext, R.anim.anim_translate_reverse);
        animTranslateY = AnimationUtils.loadAnimation(pContext, R.anim.anim_translate_y);
        animTranslateYInverted = AnimationUtils.loadAnimation(pContext, R.anim.anim_translate_y_inverted);
    }

    protected void showAnimation(final ImageButton pButton, final Animation pAnimationType) {

        new CountDownTimer(500, 1) {
            public void onFinish() {
                pButton.startAnimation(pAnimationType);
                new CountDownTimer(500, 1) {
                    public void onFinish() {
                        pButton.setVisibility(View.VISIBLE);
                    }
                    public void onTick(long millisUntilFinished) {}
                }.start();
            }
            public void onTick(long millisUntilFinished) {}
        }.start();

    }

}
