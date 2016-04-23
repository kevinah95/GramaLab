package com.example.android.gramalab.activities.games;

import android.app.Activity;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.android.gramalab.R;
import com.example.android.gramalab.activities.MainActivity;
import com.example.android.gramalab.logic.IdentifyGame;
import com.example.android.gramalab.utils.Timer;
import com.example.android.gramalab.views.games.DrawTextView;
import com.example.android.gramalab.views.games.DrawVectorView;

import java.util.ArrayList;
import java.util.Random;

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

    ImageButton buttonNoun;
    ImageButton buttonVerb;
    ImageButton buttonAdjective;
    ImageButton buttonArticle;
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
        /*
        Aqui haria como un ciclo donde agrega a identifyGames lo que haya en la base de datos
        */
        identifyGames.add(new IdentifyGame("Correr", "Verbo"));
        identifyGames.add(new IdentifyGame("Casa", "Sustantivo"));
        identifyGames.add(new IdentifyGame("El","Articulo"));
        //Aqui termina el ciclo
        absoluteLayout = (AbsoluteLayout) findViewById(R.id.abs_layout);
        relativeLayout = (RelativeLayout) findViewById(R.id.rel_layout);

        wordBox = new DrawVectorView(this);
        wordBox.prepareCanvas(R.drawable.rectangle_game_identify);
        wordBox.setWIDTH_POSITON_PORCENTAGE(0.85f);
        wordBox.setHEIGHT_POSITON_PORCENTAGE(0.60f);
        wordBox.setVECTOR_SCALABLE_PORCENTAGE(0.70f);

        absoluteLayout.addView(wordBox);
        context = this;
        new CountDownTimer(2000, 1) {
            public void onFinish() {
                wordBoxWidth = wordBox.getCanvasWidth();
                wordBoxMinWidth = wordBox.getCanvasX();
                triesTextView.bringToFront();
                setGame();
                relativeLayout.setVisibility(View.INVISIBLE);
                new Timer(MainActivity.gameSeconds, context);
            }
            public void onTick(long millisUntilFinished) {}
        }.start();

//        answerEditText = (EditText)findViewById(R.id.wordEditTextIdentity);
        triesTextView = (TextView)findViewById(R.id.triesTextViewIdentify);

        buttonArticle = (ImageButton) findViewById(R.id.article_btn);
        buttonAdjective = (ImageButton) findViewById(R.id.adjective_btn);
        buttonNoun = (ImageButton) findViewById(R.id.noun_btn);
        buttonVerb = (ImageButton) findViewById(R.id.verb_btn);

        buttonVerb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer("Verbo");
            }
        });
        buttonNoun.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer("Sustantivo");
            }
        });
        buttonAdjective.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer("Adjetivo");
            }
        });
        buttonArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer("Articulo");
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    void setGame()
    {
        actualGame = identifyGames.get(new Random().nextInt(identifyGames.size()));
        if(wordTextBox == null)
            wordTextBox = new DrawTextView(this);
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