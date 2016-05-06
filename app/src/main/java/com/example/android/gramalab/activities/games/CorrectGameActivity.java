package com.example.android.gramalab.activities.games;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gramalab.R;
import com.example.android.gramalab.activities.MainActivity;
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

    TextView triesTextView;

    DrawTextView sentenceBox;
    DrawTextView optionOneText;
    DrawTextView optionTwoText;

    DrawVectorView sentenceBoardBox;
    private float sentenceBoxWidth;
    private float sentenceBoxMinWidth;

    RelativeLayout relativeLayout;
    AbsoluteLayout absoluteLayout;
    Activity context;

    DrawVectorView buttonOption;
    private float option1Width;
    private float option1MinWidth;
    DrawVectorView buttonOption2;
    private float option2Width;
    private float option2MinWidth;

    int randomOption;
    int successValue = 10;
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
                    correctGames.add(new CorrectGame(jsonObject.getString("Sentence"), jsonObject.getString("BadWord"), jsonObject.getString("CorrectWord")));
                }

                sentenceBoardBox = new DrawVectorView(this);
                sentenceBoardBox.prepareCanvas(R.drawable.correct_board);
                sentenceBoardBox.setWIDTH_POSITON_PORCENTAGE(0.875f);
                sentenceBoardBox.setHEIGHT_POSITON_PORCENTAGE(0.75f);
                sentenceBoardBox.setVECTOR_SCALABLE_PORCENTAGE(0.75f);

                buttonOption = new DrawVectorView(this);
                buttonOption.prepareCanvas(R.drawable.btn_game_correct_empty);
                buttonOption.setWIDTH_POSITON_PORCENTAGE(0.745f);
                buttonOption.setHEIGHT_POSITON_PORCENTAGE(0.35f);
                buttonOption.setVECTOR_SCALABLE_PORCENTAGE(0.50f);

                buttonOption2 = new DrawVectorView(this);
                buttonOption2.prepareCanvas(R.drawable.btn_game_correct_empty);
                buttonOption2.setWIDTH_POSITON_PORCENTAGE(0.745f);
                buttonOption2.setHEIGHT_POSITON_PORCENTAGE(0.26f);
                buttonOption2.setVECTOR_SCALABLE_PORCENTAGE(0.50f);

                absoluteLayout.addView(sentenceBoardBox);
                absoluteLayout.addView(buttonOption);
                absoluteLayout.addView(buttonOption2);


                buttonOption.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                Log.d("MY X", " " + x);
                                if (x > buttonOption.getCanvasX() && x < (buttonOption.getCanvasX() + buttonOption.getCanvasWidth()) && y > buttonOption.getCanvasY() && y < (buttonOption.getCanvasY() + buttonOption.getCanvasHeight()))
                                    checkAnswer(optionOneText.getText());
                                else if (x > buttonOption2.getCanvasX() && x < (buttonOption2.getCanvasX() + buttonOption2.getCanvasWidth()) && y > buttonOption2.getCanvasY() && y < (buttonOption2.getCanvasY() + buttonOption2.getCanvasHeight()))
                                    checkAnswer(optionTwoText.getText());

                                break;
                            case MotionEvent.ACTION_MOVE:
                                break;
                            case MotionEvent.ACTION_UP:
                                break;
                        }
                        return false;
                    }
                });

                triesTextView = (TextView) findViewById(R.id.triesTextViewCorrect);
                triesTextView.setText(MainActivity.scoreText + MainActivity.score);
                context = this;
                new CountDownTimer(2000, 1) {
                    public void onFinish() {
                        Log.d("ENTRO", "Entro aquí");
                        sentenceBoxWidth = sentenceBoardBox.getCanvasWidth();
                        sentenceBoxMinWidth = sentenceBoardBox.getCanvasX();
                        option1Width = buttonOption.getCanvasWidth();
                        option1MinWidth = buttonOption.getCanvasX();
                        option2Width = buttonOption2.getCanvasWidth();
                        option2MinWidth = buttonOption2.getCanvasX();
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

        if(sentenceBox == null) {
            sentenceBox = new DrawTextView(this);
            optionOneText = new DrawTextView(this);
            optionTwoText = new DrawTextView(this);
        }
        else {
            absoluteLayout.removeView(sentenceBox);
            absoluteLayout.removeView(optionOneText);
            absoluteLayout.removeView(optionTwoText);
        }
        sentenceBox.setText(actualGame.get_Sentence(), "Dosis-Regular.ttf", 80, 0.945f, 0.60f, true, 80, sentenceBoxWidth, sentenceBoxMinWidth);
        //Make Random this
        String firstOption = actualGame.get_BadWord();
        String secondOption = actualGame.get_CorrectWord();
        randomOption = randInt(1, 2);
        if(randomOption == 1) {
            Log.d("Arriba", "Mala");
            optionOneText.setText(firstOption, "Dosis-Regular.ttf", 80, 0.945f, 0.30f, true, 80, option1Width, option1MinWidth);
            optionTwoText.setText(secondOption, "Dosis-Regular.ttf", 80, 0.945f, 0.20f, true, 80, option2Width, option2MinWidth);
        }
        else {
            Log.d("Arriba", "Buena");
            optionOneText.setText(secondOption, "Dosis-Regular.ttf", 80, 0.945f, 0.30f, true, 80, option1Width, option1MinWidth);
            optionTwoText.setText(firstOption, "Dosis-Regular.ttf", 80, 0.945f, 0.20f, true, 80, option2Width, option2MinWidth);
        }
        sentenceBox.bringToFront();
        optionOneText.bringToFront();
        optionTwoText.bringToFront();
        absoluteLayout.addView(sentenceBox);
        absoluteLayout.addView(optionOneText);
        absoluteLayout.addView(optionTwoText);
    }

    public void checkAnswer(String pAnswer)
    {
        if (pAnswer.matches(actualGame.get_CorrectWord())) {
            triesTextView.setText(MainActivity.scoreText +(MainActivity.score += successValue));
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

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
