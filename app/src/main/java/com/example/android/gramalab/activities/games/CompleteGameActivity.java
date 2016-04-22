package com.example.android.gramalab.activities.games;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
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
import com.example.android.gramalab.logic.CompleteGame;
import com.example.android.gramalab.utils.Timer;
import com.example.android.gramalab.views.games.DrawTextView;
import com.example.android.gramalab.views.games.DrawVectorView;

import java.util.ArrayList;
import java.util.Random;


public class CompleteGameActivity extends AppCompatActivity
{
    ArrayList<CompleteGame> completeGames = new ArrayList<>();
    CompleteGame actualGame;

    private Animation animBtn;
    private ImageButton checkButton;

    DrawVectorView sentenceBox;
    DrawVectorView wordTextBox;
    DrawTextView sentenceText;
    DrawTextView tenceText;

    AbsoluteLayout absoluteLayout;
    RelativeLayout relativeLayout;

    private float sentenceBoxWidth;
    private float sentenceBoxMinWidth;
    private float wordTextBoxWidth;
    private float wordTextBoxMinWidth;

    EditText sentenceEditText;
    TextView wordTextView;
    EditText answerEditText;
    TextView triesTextView;

    int tries;
    Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        animBtn = AnimationUtils.loadAnimation(this, R.anim.btn_scale);

        /*
        Aqui haria como un ciclo donde agrega a completeGames lo que haya en la base de datos
        */
        completeGames.add(new CompleteGame("Rodrigo [Verbo] todo el día", "Verbo/Presente: Correr", "corre"));
        completeGames.add(new CompleteGame("Carlos esta [Verbo] en este momento", "Verbo/Gerundio: Nadar", "nadando"));
        completeGames.add(new CompleteGame("Tienen que [Verbo] mucho para este examen","Verbo/Infinitivo: Estudiar","estudiar"));
        //Aqui termina el ciclo

        //Cosas de Diseño
        //drawVector(sentenceBox, R.id.cloud_box, 0.945f, 0.85f, 0.90f, "Sentence");
        //drawVector(wordTextBox, R.id.tense_box, 0.60f, 0.53f, 0.50f, "Word");

        checkButton = (ImageButton) findViewById(R.id.btn_check_answer);

        relativeLayout = (RelativeLayout) findViewById(R.id.rel_layout);
        absoluteLayout = (AbsoluteLayout) findViewById(R.id.abs_layout);

        sentenceBox = new DrawVectorView(this);
        sentenceBox.prepareCanvas(R.drawable.game_complete_cloud);
        sentenceBox.setWIDTH_POSITON_PORCENTAGE(0.945f);
        sentenceBox.setHEIGHT_POSITON_PORCENTAGE(0.85f);
        sentenceBox.setVECTOR_SCALABLE_PORCENTAGE(0.90f);

        wordTextBox = new DrawVectorView(this);
        wordTextBox.prepareCanvas(R.drawable.game_complete_tense);
        wordTextBox.setWIDTH_POSITON_PORCENTAGE(0.60f);
        wordTextBox.setHEIGHT_POSITON_PORCENTAGE(0.53f);
        wordTextBox.setVECTOR_SCALABLE_PORCENTAGE(0.50f);

        absoluteLayout.addView(sentenceBox);
        absoluteLayout.addView(wordTextBox);


        //////------------------------- Aquí se deben esperar al menos 1 segundo antes de cargar el juego, para poder calcular posiciones y dimensiones-------------------//////////////
        new CountDownTimer(2000, 1) {
            public void onFinish() {
                sentenceBoxWidth = sentenceBox.getCanvasWidth();
                sentenceBoxMinWidth = sentenceBox.getCanvasX();
                wordTextBoxWidth = wordTextBox.getCanvasWidth();
                wordTextBoxMinWidth = wordTextBox.getCanvasX();
                setGame();
                relativeLayout.setVisibility(View.INVISIBLE);
                new Timer(10, context);
            }
            public void onTick(long millisUntilFinished) {}
        }.start();
        //drawText(sentenceText, R.id.sentence_text, "Aquí va el texto", "Dosis-Regular.ttf", 80, 0.945f, 0.79f, true);
        //sentenceEditText = (EditText)findViewById(R.id.sentenceEditTextComplete);
        //wordTextView = (TextView)findViewById(R.id.wordTextViewComplete);
        answerEditText = (EditText)findViewById(R.id.answer_edit_Text);
        //triesTextView = (TextView)findViewById(R.id.triesTextViewComplete);
        //sentenceEditText.setKeyListener(null);

        context = this;

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
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    void setGame()
    {
        CompleteGame completeGame = completeGames.get(new Random().nextInt(completeGames.size()));
        sentenceText = new DrawTextView(this);
        sentenceText.setText(completeGame.get_Sentence(), "Dosis-Regular.ttf", 80, 0.945f, 0.62f, true, 80, sentenceBoxWidth, sentenceBoxMinWidth);
        sentenceText.bringToFront();

        tenceText = new DrawTextView(this);
        tenceText.setText(completeGame.get_Word(), "Dosis-Regular.ttf", 60, 0.60f, 0.41f, true, 40, wordTextBoxWidth, wordTextBoxMinWidth);
        tenceText.bringToFront();

        absoluteLayout.addView(sentenceText);
        absoluteLayout.addView(tenceText);

        //drawText(sentenceText, R.id.sentence_text, completeGame.get_Sentence(), "Dosis-Regular.ttf", 80, 0.945f, 0.79f, true, 10, sentenceBoxMaxWidth - wordTextBoxMinWidth, wordTextBoxMinWidth);
        //drawText(tenceText, R.id.tence_text, completeGame.get_Word(), "Dosis-Regular.ttf", 60, 0.60f, 0.41f, true, 10, wordTextBoxMaxWidth - wordTextBoxMinWidth, wordTextBoxMinWidth);


        //sentenceEditText.setText(completeGame.get_Sentence());
        //wordTextView.setText(completeGame.get_Word());
        answerEditText.setText("");
        //tries = 0;
        //triesTextView.setText("Número de fallos: " + tries);
        actualGame = completeGame;
    }

    public void checkAnswer()
    {
        if(answerEditText.getText().toString().matches("")) {
            //Toast.makeText(this, "Debes escribir una respuesta", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (answerEditText.getText().toString().matches(actualGame.get_Answer())) {
                //Toast.makeText(this, "Respuesta correcta!", Toast.LENGTH_SHORT).show();
                MainActivity.score++;
                setGame();
            }
            else
            {
                MainActivity.score--;
                //Toast.makeText(this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                //triesTextView.setText("Número de fallos: " + ++tries);
            }
        }
    }

    public void onClick(final View v) {
        v.startAnimation(animBtn);
        new CountDownTimer(250, 1) {
            public void onFinish() {
                    checkAnswer();
            }
            public void onTick(long millisUntilFinished) {}
        }.start();
    }

    @Override
    public void onBackPressed() {
    }
}
