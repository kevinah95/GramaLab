package com.example.android.gramalab.activities.games;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gramalab.R;
import com.example.android.gramalab.activities.MainActivity;
import com.example.android.gramalab.logic.IdentifyGame;
import com.example.android.gramalab.logic.OrderGame;
import com.example.android.gramalab.utils.Server;
import com.example.android.gramalab.utils.Timer;

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
public class OrderGameActivity extends AppCompatActivity
{
    ArrayList<OrderGame> orderGames = new ArrayList<>();
    OrderGame actualGame;

    GridLayout gridViewSentence;
    GridLayout gridViewAnswer;
    TextView triesTextView;

    RelativeLayout relativeLayout;

    Activity context;

    int sucessValue = 5;
    int failValue = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_game);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        relativeLayout = (RelativeLayout) findViewById(R.id.rel_layout);

        context = this;
        try {
            String query = String.format("Table=%s", URLEncoder.encode("OrderSentence", MainActivity.charset));

            JSONArray responseJSON = new Server().execute(MainActivity.ipAdress, query).get();

            if (responseJSON != null && responseJSON.length() > 0) {
                orderGames.clear();
                JSONObject jsonObject;
                for (int i = 0; i < responseJSON.length(); i++) {
                    jsonObject = responseJSON.getJSONObject(i);
                    orderGames.add(new OrderGame(jsonObject.getString("CorrectSentence")));
                }

                gridViewSentence = (GridLayout) findViewById(R.id.gridViewSentence);
                gridViewAnswer = (GridLayout) findViewById(R.id.gridViewAnswer);

                triesTextView = (TextView) findViewById(R.id.triesTextViewOrder);
                triesTextView.setText(MainActivity.scoreText + MainActivity.score);
                new CountDownTimer(2000, 1) {
                    public void onFinish() {
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
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    void setGame()
    {
        actualGame = orderGames.get((int) (orderGames.size() * Math.random()));

        gridViewSentence.removeAllViews();
        gridViewAnswer.removeAllViews();

        int column = actualGame.get_Words().size();
        int row = 1;
        gridViewSentence.setColumnCount(column);
        gridViewSentence.setRowCount(row);

        gridViewAnswer.setColumnCount(column);
        gridViewAnswer.setRowCount(row);

        for (int i = 0; i < column; i++)
        {
            final TextView textViewSentence = new TextView(context);
            textViewSentence.setText(actualGame.get_Words().get(i) + " ");
            textViewSentence.setTextSize(30);
            textViewSentence.setTextColor(getResources().getColor(R.color.white));
            textViewSentence.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            textViewSentence.setOnClickListener(new View.OnClickListener()
            {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onClick(View view)
                {
                    gridViewSentence.removeView(view);

                    TextView textViewAnswer = new TextView(context);
                    textViewAnswer.setText(((TextView)view).getText());
                    textViewAnswer.setTextSize(30);
                    textViewAnswer.setTextColor(getResources().getColor(R.color.white));
                    textViewAnswer.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    textViewAnswer.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            gridViewAnswer.removeView(view);
                            gridViewSentence.addView(textViewSentence);
                        }
                    });
                    gridViewAnswer.addView(textViewAnswer);
                }
            });
            gridViewSentence.addView(textViewSentence);
        }
    }

    public void checkAnswer(View v)
    {
        String answer = getGridAnswerText();
        if(!answer.matches(""))
        {
            if(answer.matches(actualGame.get_CorrectSentece().trim()))
            {
                triesTextView.setText(MainActivity.scoreText +(MainActivity.score += sucessValue));
                setGame();
            }
            else
            {
                triesTextView.setText(MainActivity.scoreText + (MainActivity.score -= failValue));
            }
        }
    }

    public String getGridAnswerText()
    {
        if(gridViewAnswer.getChildCount() > 0)
        {
            String answer = "";
            for (int i = 0; i < gridViewAnswer.getChildCount(); i++)
            {
                answer += ((TextView) gridViewAnswer.getChildAt(i)).getText();
            }
            return answer.trim();
        }
        else
            return "";
    }

    @Override
    public void onBackPressed() {
    }
}