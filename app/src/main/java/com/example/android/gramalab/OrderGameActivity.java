package com.example.android.gramalab;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gramalab.Classes.OrderGame;

import java.util.ArrayList;
import java.util.Random;

public class OrderGameActivity extends AppCompatActivity
{
    ArrayList<OrderGame> orderGames = new ArrayList<>();
    OrderGame actualGame;

    GridLayout gridViewSentence;
    GridLayout gridViewAnswer;
    TextView triesTextView;

    Context context;

    int tries;
    int gridIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_game);

        context = this;
        /*
        Aqui haria como un ciclo donde agrega a orderGames lo que haya en la base de datos
        */
        orderGames.add(new OrderGame("Dicen que va a haber mucha comida"));
        orderGames.add(new OrderGame("Vamos para alla"));
        orderGames.add(new OrderGame("Cuando llegue a la tienda pregunte si hay sal"));
        //Aqui termina el ciclo

        triesTextView = (TextView)findViewById(R.id.triesTextViewOrder);

        gridViewSentence = (GridLayout) findViewById(R.id.gridViewSentence);
        gridViewAnswer = (GridLayout) findViewById(R.id.gridViewAnswer);
        gridIndex = 0;
        setGame();
    }

    void setGame()
    {
        actualGame = orderGames.get(new Random().nextInt(orderGames.size()));

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

            textViewSentence.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    gridViewSentence.removeView(view);

                    TextView textViewAnswer = new TextView(context);
                    textViewAnswer.setText(((TextView)view).getText());
                    textViewAnswer.setTextSize(30);
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
        tries = 0;
        triesTextView.setText("Número de fallos: " + tries);
    }

    public void checkAnswer(View v)
    {
        String answer = getGridAnswerText();
        if(answer.matches(""))
            Toast.makeText(this, "No has seleccionado ninguna palabra", Toast.LENGTH_SHORT).show();
        else
        {
            if(answer.matches(actualGame.get_CorrectSentece().trim()))
            {
                Toast.makeText(this, "Respuesta correcta!", Toast.LENGTH_SHORT).show();
                setGame();
            }
            else
            {
                Toast.makeText(this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                triesTextView.setText("Número de fallos: " + ++tries);
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
}
