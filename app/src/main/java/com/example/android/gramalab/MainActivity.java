package com.example.android.gramalab;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {

    private Animation animBtn;

    private ImageButton startButton;
    private ImageButton optionsButton;
    private ImageButton exitButton;

    private Animation animTranslate;
    private Animation animTranslateInverted;
    private Animation animTranslateY;

    private MediaPlayer mainScreenSound;
    private int whenStop;

    private PetsView petsViewPoint;
    private PetsView petsViewAccent;
    private PetsView petsViewComma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        animBtn = AnimationUtils.loadAnimation(this, R.anim.btn_scale);

        createPets();

        mainScreenSound = MediaPlayer.create(this, R.raw.maintheme);
        mainScreenSound.start();
        mainScreenSound.setLooping(true);

        startButton = (ImageButton) findViewById(R.id.btn_jugar);
        optionsButton = (ImageButton) findViewById(R.id.btn_opciones);
        exitButton = (ImageButton) findViewById(R.id.btn_salir);

        animTranslate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);
        animTranslateInverted = AnimationUtils.loadAnimation(this, R.anim.anim_translate_reverse);
        animTranslateY = AnimationUtils.loadAnimation(this, R.anim.anim_translate_y);

        showAnimation(startButton, animTranslate);
        showAnimation(optionsButton, animTranslateInverted);
        showAnimation(exitButton, animTranslateY);
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
        v.startAnimation(animBtn);
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

    public void createPets() {
        petsViewPoint = (PetsView) findViewById(R.id.petsViewPoint);
        petsViewPoint.setWIDTH_POSITON_PORCENTAGE(0.70f);
        petsViewPoint.setHEIGHT_POSITON_PORCENTAGE(0.13f);
        petsViewPoint.setVECTOR_SCALABLE_PORCENTAGE(0.17F);

        petsViewAccent = (PetsView) findViewById(R.id.petsViewAccent);
        petsViewAccent.setWIDTH_POSITON_PORCENTAGE(0.45f);
        petsViewAccent.setHEIGHT_POSITON_PORCENTAGE(0.26f);
        petsViewAccent.setVECTOR_SCALABLE_PORCENTAGE(0.105f);

        petsViewComma = (PetsView) findViewById(R.id.petsViewComma);
        petsViewComma.setWIDTH_POSITON_PORCENTAGE(0.24f);
        petsViewComma.setHEIGHT_POSITON_PORCENTAGE(0.15f);
        petsViewComma.setVECTOR_SCALABLE_PORCENTAGE(0.105f);
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
