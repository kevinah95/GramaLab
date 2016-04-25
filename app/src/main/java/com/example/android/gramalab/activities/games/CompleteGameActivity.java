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
import java.util.concurrent.ExecutionException;


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

    EditText answerEditText;
    TextView triesTextView;
    int sucessValue = 2;
    int failValue = 1;

    Activity context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        animBtn = AnimationUtils.loadAnimation(this, R.anim.btn_scale);

        try {
            String query = String.format("Table=%s", URLEncoder.encode("Complete", MainActivity.charset));

            JSONArray responseJSON = new Server().execute(MainActivity.ipAdress, query).get();

            if (responseJSON != null && responseJSON.length() > 0) {
                completeGames.clear();
                JSONObject jsonObject;
                for (int i = 0; i < responseJSON.length(); i++) {
                    jsonObject = responseJSON.getJSONObject(i);
                    completeGames.add(new CompleteGame(jsonObject.getString("Sentence"), jsonObject.getString("Word"), jsonObject.getString("Answer")));
                }

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

                context = this;

                new CountDownTimer(2000, 1) {
                    public void onFinish() {
                        sentenceBoxWidth = sentenceBox.getCanvasWidth();
                        sentenceBoxMinWidth = sentenceBox.getCanvasX();
                        wordTextBoxWidth = wordTextBox.getCanvasWidth();
                        wordTextBoxMinWidth = wordTextBox.getCanvasX();
                        setGame();
                        relativeLayout.setVisibility(View.INVISIBLE);
                        new Timer(MainActivity.gameSeconds, context);
                    }

                    public void onTick(long millisUntilFinished) {
                    }
                }.start();

                answerEditText = (EditText) findViewById(R.id.answer_edit_Text);
                triesTextView = (TextView) findViewById(R.id.triesTextViewComplete);
            } else {
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
        CompleteGame completeGame = completeGames.get((int) (completeGames.size() * Math.random()));
        if(sentenceText == null || tenceText == null) {
            sentenceText = new DrawTextView(this);
            tenceText = new DrawTextView(this);
        }
        else {
            absoluteLayout.removeView(sentenceText);
            absoluteLayout.removeView(tenceText);
        }
        sentenceText.setText(completeGame.get_Sentence(), "Dosis-Regular.ttf", 80, 0.945f, 0.62f, true, 80, sentenceBoxWidth, sentenceBoxMinWidth);
        sentenceText.bringToFront();
        tenceText.setText(completeGame.get_Word(), "Dosis-Regular.ttf", 60, 0.60f, 0.41f, true, 40, wordTextBoxWidth, wordTextBoxMinWidth);
        tenceText.bringToFront();

        absoluteLayout.addView(sentenceText);
        absoluteLayout.addView(tenceText);

        //drawText(sentenceText, R.id.sentence_text, completeGame.get_Sentence(), "Dosis-Regular.ttf", 80, 0.945f, 0.79f, true, 10, sentenceBoxMaxWidth - wordTextBoxMinWidth, wordTextBoxMinWidth);
        //drawText(tenceText, R.id.tence_text, completeGame.get_Word(), "Dosis-Regular.ttf", 60, 0.60f, 0.41f, true, 10, wordTextBoxMaxWidth - wordTextBoxMinWidth, wordTextBoxMinWidth);


        //sentenceEditText.setText(completeGame.get_Sentence());
        //wordTextView.setText(completeGame.get_Word());
        answerEditText.setText("");
        triesTextView.setText(MainActivity.scoreText + MainActivity.score);
        triesTextView.bringToFront();
        actualGame = completeGame;
    }

    public void checkAnswer()
    {
        if(!answerEditText.getText().toString().matches(""))
        {
            if (answerEditText.getText().toString().toLowerCase().matches(actualGame.get_Answer().toLowerCase())) {
                triesTextView.setText(MainActivity.scoreText +(MainActivity.score += sucessValue));
                setGame();
            }
            else
            {
                triesTextView.setText(MainActivity.scoreText + (MainActivity.score -= failValue));
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
