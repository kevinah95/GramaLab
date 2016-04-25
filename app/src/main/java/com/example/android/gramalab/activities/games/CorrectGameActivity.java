package com.example.android.gramalab.activities.games;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gramalab.R;
import com.example.android.gramalab.activities.MainActivity;
import com.example.android.gramalab.logic.CompleteGame;
import com.example.android.gramalab.logic.CorrectGame;
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

/**
 * Created by jasc9 on 22/4/2016.
 */
public class CorrectGameActivity extends AppCompatActivity
{
    ArrayList<CorrectGame> correctGames = new ArrayList<>();
    CorrectGame actualGame;

    EditText answerEditText;
    TextView triesTextView;

    DrawTextView sentenceBox;
    DrawVectorView sentenceBoardBox;
    private float senteceBoxWidth;
    private float senteceBoxMinWidth;

    RelativeLayout relativeLayout;
    AbsoluteLayout absoluteLayout;
    Activity context;

    int sucessValue = 10;
    int failValue = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        relativeLayout = (RelativeLayout) findViewById(R.id.rel_layout);
        absoluteLayout = (AbsoluteLayout) findViewById(R.id.abs_layout);
        try {
            String query = String.format("Table=%s", URLEncoder.encode("Correct", MainActivity.charset));

            JSONArray responseJSON = new Server().execute(MainActivity.ipAdress, query).get();

            if (responseJSON != null && responseJSON.length() > 0) {
                correctGames.clear();
                JSONObject jsonObject;
                for (int i = 0; i < responseJSON.length(); i++) {
                    jsonObject = responseJSON.getJSONObject(i);
                    correctGames.add(new CorrectGame(jsonObject.getString("Sentence"), jsonObject.getString("Words"), jsonObject.getString("CorrectWord")));
                }

                sentenceBoardBox = new DrawVectorView(this);
                sentenceBoardBox.prepareCanvas(R.drawable.correct_board);
                sentenceBoardBox.setWIDTH_POSITON_PORCENTAGE(0.875f);
                sentenceBoardBox.setHEIGHT_POSITON_PORCENTAGE(0.75f);
                sentenceBoardBox.setVECTOR_SCALABLE_PORCENTAGE(0.75f);

                absoluteLayout.addView(sentenceBoardBox);
                answerEditText = (EditText) findViewById(R.id.answerEditTextCorrect);

                triesTextView = (TextView) findViewById(R.id.triesTextViewCorrect);
                triesTextView.setText(MainActivity.scoreText + MainActivity.score);
                context = this;
                new CountDownTimer(2000, 1) {
                    public void onFinish() {
                        senteceBoxWidth = sentenceBoardBox.getCanvasWidth();
                        senteceBoxMinWidth = sentenceBoardBox.getCanvasX();
                        relativeLayout.setVisibility(View.INVISIBLE);
                        setGame();
                        new Timer(MainActivity.gameSeconds, context);
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
            Toast.makeText(this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        } catch (UnsupportedEncodingException e) {
            Toast.makeText(this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    void setGame()
    {
        actualGame = correctGames.get((int) (correctGames.size() * Math.random()));

        if(sentenceBox == null)
            sentenceBox = new DrawTextView(this);
        else {
            answerEditText.getText().clear();
            absoluteLayout.removeView(sentenceBox);
        }
        sentenceBox.setText(actualGame.get_Sentence(), "Dosis-Regular.ttf", 80, 0.945f, 0.65f, true, 80, senteceBoxWidth, senteceBoxMinWidth);
        sentenceBox.bringToFront();
        answerEditText.bringToFront();
        absoluteLayout.addView(sentenceBox);
    }

    public void checkAnswer(View v)
    {
        if(!answerEditText.getText().toString().matches(""))
        {
            if (answerEditText.getText().toString().toLowerCase().matches(actualGame.get_CorrectWord().toLowerCase())) {
                triesTextView.setText(MainActivity.scoreText +(MainActivity.score += sucessValue));
                setGame();
            }
            else
            {
                triesTextView.setText(MainActivity.scoreText + (MainActivity.score -= failValue));
            }
        }
    }

    @Override
    public void onBackPressed() {
    }
}
