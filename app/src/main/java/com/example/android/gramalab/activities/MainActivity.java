package com.example.android.gramalab.activities;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.android.gramalab.views.CustomPagerAdapter;
import com.example.android.gramalab.views.CustomViewPager;
import com.example.android.gramalab.fragments.HomeFragment;
import com.example.android.gramalab.R;
import com.example.android.gramalab.fragments.SelectGameFragment;
import com.example.android.gramalab.fragments.SelectLevelFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        SelectLevelFragment.OnFragmentInteractionListener,
        SelectGameFragment.OnFragmentInteractionListener
        {

    public static final String TAG = MainActivity.class.getName();

    private MediaPlayer mainScreenSound;
    private int whenStop;

    private CustomViewPager customViewPager;
    private PagerAdapter customPagerAdapter;
    private Stack<Integer> stackCustomViewPager = new Stack<>(); // Edited

    public static Activity context;
    public static String scoreText = "Puntaje: ";
    public static int gameSeconds = 10;
    public static int score;

    public static boolean isCompletePlayed = false;
    public static boolean isIdentifyPlayed = false;
    public static boolean isDividePlayed = false;
    public static boolean isOrderPlayed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Activity FullScreen
        score = 0;
        context = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        startMusic();
        //--------------------------Prueba Parallax
        customViewPager = (CustomViewPager) findViewById(R.id.custom_view_pager);
        List<Fragment> fragments = new ArrayList<>();
        /**
         * Lista de Fragments
         */
        fragments.add(Fragment.instantiate(this, HomeFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, SelectLevelFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, SelectGameFragment.class.getName()));
        customPagerAdapter = new CustomPagerAdapter(super.getSupportFragmentManager(), fragments);
        customViewPager.setAdapter(customPagerAdapter);

        // Solution-> http://stackoverflow.com/a/33028501/4752488
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                if(stackCustomViewPager.empty()){
                    stackCustomViewPager.push(0);
                }
                if (stackCustomViewPager.contains(position)) {
                    stackCustomViewPager.remove(stackCustomViewPager.indexOf(position));
                    stackCustomViewPager.push(position);
                } else {
                    stackCustomViewPager.push(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    /**
     * Change Screen Mode: IMMERSIVE http://developer.android.com/intl/es/training/system-ui/immersive.html
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

    public void startMusic(){
        mainScreenSound = MediaPlayer.create(this, R.raw.maintheme);
        mainScreenSound.start();
        mainScreenSound.setLooping(true);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backStateViewPager();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * Change ViewPager on back button from its stack
     */
    public void backStateViewPager(){
        if (stackCustomViewPager.size() > 1) {
            stackCustomViewPager.pop();
            customViewPager.setCurrentItem(stackCustomViewPager.lastElement());
        }
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
