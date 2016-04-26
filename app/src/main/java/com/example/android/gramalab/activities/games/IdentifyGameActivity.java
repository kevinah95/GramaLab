package com.example.android.gramalab.activities.games;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.gramalab.R;
import com.example.android.gramalab.activities.MainActivity;
import com.example.android.gramalab.logic.DivideGame;
import com.example.android.gramalab.logic.IdentifyGame;
import com.example.android.gramalab.utils.Server;
import com.example.android.gramalab.utils.Timer;
import com.example.android.gramalab.views.games.DrawTextView;
import com.example.android.gramalab.views.games.DrawVectorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class IdentifyGameActivity extends AppCompatActivity
{
    ArrayList<IdentifyGame> identifyGames = new ArrayList<>();
    IdentifyGame actualGame;

    private Animation animBtnGame;

    private AbsoluteLayout absoluteLayout;
    private RelativeLayout relativeLayout;

    private DrawVectorView wordBox;
    private DrawTextView wordTextBox;

    private float wordBoxWidth;
    private float wordBoxMinWidth;

    EditText answerEditText;
    TextView triesTextView;

    DrawVectorView buttonNoun;
    DrawVectorView buttonVerb;
    DrawVectorView buttonAdjective;
    DrawVectorView buttonArticle;
    Activity context;

    int sucessValue = 1;
    int failValue = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        animBtnGame = AnimationUtils.loadAnimation(this, R.anim.anim_translate_x_infinite);

        try {
            String query = String.format("Table=%s", URLEncoder.encode("Identify", MainActivity.charset));

            JSONArray responseJSON = new Server().execute(MainActivity.ipAdress, query).get();

            if (responseJSON != null && responseJSON.length() > 0) {
                identifyGames.clear();
                JSONObject jsonObject;
                for (int i = 0; i < responseJSON.length(); i++) {
                    jsonObject = responseJSON.getJSONObject(i);
                    identifyGames.add(new IdentifyGame(jsonObject.getString("Word"), jsonObject.getString("Type")));
                }

                absoluteLayout = (AbsoluteLayout) findViewById(R.id.abs_layout);
                relativeLayout = (RelativeLayout) findViewById(R.id.rel_layout);

                wordBox = new DrawVectorView(this);
                wordBox.prepareCanvas(R.drawable.rectangle_game_identify);
                wordBox.setWIDTH_POSITON_PORCENTAGE(0.85f);
                wordBox.setHEIGHT_POSITON_PORCENTAGE(0.60f);
                wordBox.setVECTOR_SCALABLE_PORCENTAGE(0.70f);

                absoluteLayout.addView(wordBox);

                triesTextView = (TextView) findViewById(R.id.triesTextViewIdentify);

                buttonArticle = new DrawVectorView(this);
                buttonArticle.prepareCanvas(R.drawable.btn_game_identify_article);
                buttonArticle.setWIDTH_POSITON_PORCENTAGE(0.745f);
                buttonArticle.setHEIGHT_POSITON_PORCENTAGE(0.35f);
                buttonArticle.setVECTOR_SCALABLE_PORCENTAGE(0.50f);
                buttonArticle.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                absoluteLayout.addView(buttonArticle);

                buttonAdjective = new DrawVectorView(this);
                buttonAdjective.prepareCanvas(R.drawable.btn_game_identify_adjective);
                buttonAdjective.setWIDTH_POSITON_PORCENTAGE(0.745f);
                buttonAdjective.setHEIGHT_POSITON_PORCENTAGE(0.26f);
                buttonAdjective.setVECTOR_SCALABLE_PORCENTAGE(0.50f);
                buttonAdjective.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                absoluteLayout.addView(buttonAdjective);

                buttonNoun = new DrawVectorView(this);
                buttonNoun.prepareCanvas(R.drawable.btn_game_identify_noun);
                buttonNoun.setWIDTH_POSITON_PORCENTAGE(0.745f);
                buttonNoun.setHEIGHT_POSITON_PORCENTAGE(0.17f);
                buttonNoun.setVECTOR_SCALABLE_PORCENTAGE(0.50f);
                buttonNoun.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                absoluteLayout.addView(buttonNoun);

                buttonVerb = new DrawVectorView(this);
                buttonVerb.prepareCanvas(R.drawable.btn_game_identify_verb);
                buttonVerb.setWIDTH_POSITON_PORCENTAGE(0.745f);
                buttonVerb.setHEIGHT_POSITON_PORCENTAGE(0.08f);
                buttonVerb.setVECTOR_SCALABLE_PORCENTAGE(0.50f);
                buttonVerb.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
                absoluteLayout.addView(buttonVerb);


                buttonVerb.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                Log.d("MY X", " " + x);
                                if (x > buttonVerb.getCanvasX() && x < (buttonVerb.getCanvasX() + buttonVerb.getCanvasWidth()) && y > buttonVerb.getCanvasY() && y < (buttonVerb.getCanvasY() + buttonVerb.getCanvasHeight()))
                                    checkAnswer("Verbo");
                                else if (x > buttonArticle.getCanvasX() && x < (buttonArticle.getCanvasX() + buttonArticle.getCanvasWidth()) && y > buttonArticle.getCanvasY() && y < (buttonArticle.getCanvasY() + buttonArticle.getCanvasHeight()))
                                    checkAnswer("Articulo");
                                else if (x > buttonNoun.getCanvasX() && x < (buttonNoun.getCanvasX() + buttonNoun.getCanvasWidth()) && y > buttonNoun.getCanvasY() && y < (buttonNoun.getCanvasY() + buttonNoun.getCanvasHeight()))
                                    checkAnswer("Sustantivo");
                                else if (x > buttonAdjective.getCanvasX() && x < (buttonAdjective.getCanvasX() + buttonAdjective.getCanvasWidth()) && y > buttonAdjective.getCanvasY() && y < (buttonAdjective.getCanvasY() + buttonAdjective.getCanvasHeight()))
                                    checkAnswer("Adjetivo");
                                break;
                            case MotionEvent.ACTION_MOVE:
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                        }
                        return false;
                    }
                });

                context = this;
                new CountDownTimer(2000, 1) {
                    public void onFinish() {
                        wordBoxWidth = wordBox.getCanvasWidth();
                        wordBoxMinWidth = wordBox.getCanvasX();
                        triesTextView.bringToFront();
                        relativeLayout.setVisibility(View.INVISIBLE);
                        new Timer(MainActivity.gameSeconds, context);
                        setGame();
                    }

                    public void onTick(long millisUntilFinished) {
                    }
                }.start();
            }
            else
            {
                Toast.makeText(this, "Problema de conexión, no se puede acceder a la base de datos", Toast.LENGTH_LONG).show();
                finish();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    void setGame()
    {
        actualGame = identifyGames.get((int) (identifyGames.size() * Math.random()));
        if(wordTextBox == null) {
            wordTextBox = new DrawTextView(this);
        }
        else
            absoluteLayout.removeView(wordTextBox);
        wordTextBox.setText(actualGame.get_Word(), "Dosis-Regular.ttf", 80, 0.945f, 0.45f, true, 80, wordBoxWidth, wordBoxMinWidth);
        wordTextBox.bringToFront();
        absoluteLayout.addView(wordTextBox);
        triesTextView.setText(MainActivity.scoreText + MainActivity.score);
        triesTextView.bringToFront();
    }

    public void checkAnswer(String pType)
    {
        if (pType.matches(actualGame.get_Type()))
        {
            triesTextView.setText(MainActivity.scoreText +(MainActivity.score += sucessValue));
            setGame();
        }
        else
        {
            triesTextView.setText(MainActivity.scoreText + (MainActivity.score -= failValue));
        }
    }

    @Override
    public void onBackPressed() {
    }
}
