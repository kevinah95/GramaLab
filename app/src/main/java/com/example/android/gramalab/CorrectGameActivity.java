package com.example.android.gramalab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gramalab.Classes.CorrectGame;

import java.util.ArrayList;
import java.util.Random;

public class CorrectGameActivity extends AppCompatActivity
{
    ArrayList<CorrectGame> correctGames = new ArrayList<>();
    CorrectGame actualGame;

    EditText answerEditText;
    TextView triesTextView;

    int tries;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correct_game);

        /*
        Aqui haria como un ciclo donde agrega a correctGames lo que haya en la base de datos
        */
        correctGames.add(new CorrectGame("Dicen que va a ver mucha comida", "Dicen que va a haber mucha comida"));
        correctGames.add(new CorrectGame("Vamos para haya", "Vamos para alla"));
        correctGames.add(new CorrectGame("Cuando llegue a la tienda pregunte si ay sal","Cuando llegue a la tienda pregunte si hay sal"));
        //Aqui termina el ciclo

        answerEditText = (EditText)findViewById(R.id.answerEditTextCorrect);
        triesTextView = (TextView)findViewById(R.id.triesTextViewCorrect);

        setGame();
    }

    void setGame()
    {
        actualGame = correctGames.get(new Random().nextInt(correctGames.size()));

        answerEditText.setText(actualGame.get_WrongSentence().toString());
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
