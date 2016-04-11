package com.example.android.gramalab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gramalab.Classes.OrderGame;

import java.util.ArrayList;
import java.util.Random;

public class OrderGameActivity extends AppCompatActivity
{
    ArrayList<OrderGame> orderGames = new ArrayList<>();
    OrderGame actualGame;

    EditText answerEditText;
    EditText sentenceEditText;
    TextView triesTextView;

    int tries;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_game);

        /*
        Aqui haria como un ciclo donde agrega a orderGames lo que haya en la base de datos
        */
        orderGames.add(new OrderGame("Dicen que va a haber mucha comida"));
        orderGames.add(new OrderGame("Vamos para alla"));
        orderGames.add(new OrderGame("Cuando llegue a la tienda pregunte si hay sal"));
        //Aqui termina el ciclo

        answerEditText = (EditText)findViewById(R.id.answerEditTextOrder);
        sentenceEditText = (EditText)findViewById(R.id.sentenceEditTextOrder);
        triesTextView = (TextView)findViewById(R.id.triesTextViewOrder);

        sentenceEditText.setKeyListener(null);
        setGame();
    }

    void setGame()
    {
        actualGame = orderGames.get(new Random().nextInt(orderGames.size()));

        sentenceEditText.setText(actualGame.get_Words().toString());
        answerEditText.setText("");
        tries = 0;
        triesTextView.setText("Número de fallos: " + tries);
    }

    public void checkAnswer(View v)
    {
        if(answerEditText.getText().toString().matches(""))
            Toast.makeText(this, "Debes escribir una respuesta", Toast.LENGTH_SHORT).show();
        else
        {
            if (answerEditText.getText().toString().matches(actualGame.get_CorrectSentece())) {
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
}
