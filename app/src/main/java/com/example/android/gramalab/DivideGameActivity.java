package com.example.android.gramalab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gramalab.Classes.DivideGame;

import java.util.ArrayList;
import java.util.Random;

public class DivideGameActivity extends AppCompatActivity
{
    ArrayList<DivideGame> divideGames = new ArrayList<>();
    DivideGame actualGame;

    EditText answerEditText;
    TextView triesTextView;

    int tries;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide_game);

        /*
        Aqui haria como un ciclo donde agrega a divideGames lo que haya en la base de datos
        */
        divideGames.add(new DivideGame("atleta", "a tle ta"));
        divideGames.add(new DivideGame("corazón", "co ra zón"));
        divideGames.add(new DivideGame("módulo","mó du lo"));
        //Aqui termina el ciclo

        answerEditText = (EditText)findViewById(R.id.answerEditTextDivide);
        triesTextView = (TextView)findViewById(R.id.triesTextViewDivide);

        setGame();
    }

    void setGame()
    {
        actualGame = divideGames.get(new Random().nextInt(divideGames.size()));

        answerEditText.setText(actualGame.get_Word());
        tries = 0;
        triesTextView.setText("Número de fallos: " + tries);
    }

    public void checkAnswer(View v)
    {
        if(answerEditText.getText().toString().matches(""))
            Toast.makeText(this, "Debes escribir una respuesta", Toast.LENGTH_SHORT).show();
        else
        {
            if (answerEditText.getText().toString().matches(actualGame.get_DividedWord())) {
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
