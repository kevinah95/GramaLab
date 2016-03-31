package com.example.android.gramalab;

import android.media.Image;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private Animation anim;

    private ImageButton startButton;
    private ImageButton optionsButton;
    private ImageButton exitButton;

    private ImageView pointImage;
    private ImageView commaImage;
    private ImageView accentImage;

    private Animation animTranslate;
    private Animation animTranslateInverted;
    private Animation animTranslateY;
    private Animation floatingBtn;

    private MediaPlayer mainScreenSound;
    private int whenStop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        anim = AnimationUtils.loadAnimation(this, R.anim.btn_scale);

        mainScreenSound = MediaPlayer.create(this, R.raw.maintheme);
        mainScreenSound.start();
        mainScreenSound.setLooping(true);

        startButton = (ImageButton) findViewById(R.id.btn_jugar);
        optionsButton = (ImageButton) findViewById(R.id.btn_opciones);
        exitButton = (ImageButton) findViewById(R.id.btn_salir);

        pointImage = (ImageView) findViewById(R.id.pointImg);
        commaImage = (ImageView) findViewById(R.id.commaImg);
        accentImage = (ImageView) findViewById(R.id.accentImg);

        animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        animTranslateInverted = AnimationUtils.loadAnimation(this, R.anim.anim_translate_reverse);
        animTranslateY = AnimationUtils.loadAnimation(this, R.anim.anim_translate_y);
        floatingBtn = AnimationUtils.loadAnimation(this, R.anim.floating_button);

        showAnimation(startButton, animTranslate);
        showAnimation(optionsButton, animTranslateInverted);
        showAnimation(exitButton, animTranslateY);

        pointImage.startAnimation(floatingBtn);
        commaImage.startAnimation(floatingBtn);
        accentImage.startAnimation(floatingBtn);
    }

    /**
     * Cambia el modo de pantalla a IMMERSIVE http://developer.android.com/intl/es/training/system-ui/immersive.html
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    public void onClick(final View v) {
        v.startAnimation(anim);
        new CountDownTimer(250, 1) {
            public void onFinish() {
                switch (v.getId()) {
                    case R.id.btn_jugar:
                        break;
                    case R.id.btn_opciones:
                        break;
                    case R.id.btn_salir:
                        finish();
                        break;
                    default:
                        break;
                }
            }
            public void onTick(long millisUntilFinished) {}
        }.start();
    }

    public void showAnimation(final ImageButton pButton, final Animation pAnimationType) {

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

    @Override
    protected void onPause() {
        super.onPause();
        mainScreenSound.pause();
        whenStop = mainScreenSound.getCurrentPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainScreenSound.seekTo(whenStop);
        mainScreenSound.start();
    }
}
