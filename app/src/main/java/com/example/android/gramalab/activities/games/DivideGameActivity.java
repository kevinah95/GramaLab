package com.example.android.gramalab.activities.games;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gramalab.R;
import com.example.android.gramalab.activities.MainActivity;
import com.example.android.gramalab.logic.DivideGame;
import com.example.android.gramalab.utils.Timer;
import com.example.android.gramalab.views.games.DrawTextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by jasc9 on 22/4/2016.
 */
public class DivideGameActivity extends AppCompatActivity
{
    ArrayList<DivideGame> divideGames = new ArrayList<>();
    DivideGame actualGame;

    LinearLayout linearLayout;
    AbsoluteLayout absoluteLayout;
    RelativeLayout relativeLayout;

    DrawTextView wordBox;
    EditText answerEditText;
    TextView triesTextView;

    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linearLayout = (LinearLayout) findViewById(R.id.linear_Word);
        absoluteLayout = (AbsoluteLayout) findViewById(R.id.abs_layout);
        relativeLayout = (RelativeLayout) findViewById(R.id.rel_layout);

        /*
        Aqui haria como un ciclo donde agrega a divideGames lo que haya en la base de datos
        */
        divideGames.add(new DivideGame("atleta", "a tle ta", 3));
        divideGames.add(new DivideGame("corazón", "co ra zón", 3));
        divideGames.add(new DivideGame("módulo","mó du lo", 3));
        //Aqui termina el ciclo

        context = this;
        new CountDownTimer(2000, 1) {
            public void onFinish() {
                relativeLayout.setVisibility(View.INVISIBLE);
                new Timer(MainActivity.gameSeconds, context);
            }
            public void onTick(long millisUntilFinished) {}
        }.start();

        triesTextView = (TextView)findViewById(R.id.triesTextViewDivide);

        setGame();
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


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void setGame()
    {
        actualGame = divideGames.get(new Random().nextInt(divideGames.size()));
        EditText editText;
        if(wordBox == null) {
            wordBox = new DrawTextView(this);
            for(int i = 0; i < actualGame.get_SysAmount(); i++)
            {
                editText = new EditText(this);
                if(i < actualGame.get_SysAmount() - 1)
                    editText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                else
                    editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
                editText.setSingleLine(true);
                LinearLayout.LayoutParams editTextSize = new LinearLayout.LayoutParams(180,110);
                editText.setBackground(getResources().getDrawable(R.drawable.edit_text_custom));
                editText.setTextSize(30);
                editText.setLayoutParams(editTextSize);
                switch (i) {
                    case(1):
                        editText.setId(R.id.edit_text_div_1);
                        break;
                    case(2):
                        editText.setId(R.id.edit_text_div_2);
                        break;
                    default:
                        editText.setId(R.id.edit_text_div_3);
                        break;
                }
                linearLayout.addView(editText);
            }
        } else {
            answerEditText = (EditText) findViewById(R.id.edit_text_div_1);
            answerEditText.getText().clear();
            answerEditText = (EditText) findViewById(R.id.edit_text_div_2);
            answerEditText.getText().clear();
            answerEditText = (EditText) findViewById(R.id.edit_text_div_3);
            answerEditText.getText().clear();
            absoluteLayout.removeView(wordBox);
        }

        wordBox.setText(actualGame.get_Word(), "Dosis-Regular.ttf", 80, 0.945f, 0.35f, true, 80, 1200, 0);
        wordBox.bringToFront();

        absoluteLayout.addView(wordBox);
        triesTextView.setText(MainActivity.scoreText + MainActivity.score);
    }

    public void checkAnswer(View v)
    {
        String answer = "";
        if (findViewById(R.id.edit_text_div_3) != null) {
            answerEditText = (EditText) findViewById(R.id.edit_text_div_3);
            answer += answerEditText.getText().toString() + " ";
        }
        if (findViewById(R.id.edit_text_div_1) != null) {
            answerEditText = (EditText) findViewById(R.id.edit_text_div_1);
            answer += answerEditText.getText().toString() + " ";
        }
        if (findViewById(R.id.edit_text_div_2) != null) {
            answerEditText = (EditText) findViewById(R.id.edit_text_div_2);
            answer += answerEditText.getText().toString();
        }
        else {
            answer.substring(0, answer.length() - 1);
        }

        Log.d("RESPUESTA", answer);

        if(!answer.matches(""))
        {
            if (answer.matches(actualGame.get_DividedWord())) {
                triesTextView.setText(MainActivity.scoreText + ++MainActivity.score);
                setGame();
            }
            else
            {
                triesTextView.setText(MainActivity.scoreText + --MainActivity.score);
            }
        }
    }

    @Override
    public void onBackPressed() {
    }
}
