package com.example.android.gramalab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gramalab.Classes.IdentifyGame;

import java.util.ArrayList;
import java.util.Random;

public class IdentifyGameActivity extends AppCompatActivity
{
    ArrayList<IdentifyGame> identifyGames = new ArrayList<>();
    IdentifyGame actualGame;

    EditText answerEditText;
    TextView triesTextView;

    Button buttonNoun;
    Button buttonVerb;
    Button buttonAdjective;
    Button buttonArticle;

    int tries;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_game);

        /*
        Aqui haria como un ciclo donde agrega a identifyGames lo que haya en la base de datos
        */
        identifyGames.add(new IdentifyGame("Correr", "Verbo"));
        identifyGames.add(new IdentifyGame("Casa", "Sustantivo"));
        identifyGames.add(new IdentifyGame("El","Articulo"));
        //Aqui termina el ciclo

        answerEditText = (EditText)findViewById(R.id.wordEditTextIdentity);
        triesTextView = (TextView)findViewById(R.id.triesTextViewIdentify);

        buttonArticle = (Button) findViewById(R.id.buttonArticle);
        buttonAdjective = (Button) findViewById(R.id.buttonAdjective);
        buttonNoun = (Button) findViewById(R.id.buttonNoun);
        buttonVerb = (Button) findViewById(R.id.buttonVerb);

        buttonVerb.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer("Verbo");
            }
        });

        buttonNoun.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer("Sustantivo");
            }
        });

        buttonAdjective.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer("Adjetivo");
            }
        });

        buttonArticle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                checkAnswer("Articulo");
            }
        });

        answerEditText.setKeyListener(null);
        setGame();
    }

    void setGame()
    {
        actualGame = identifyGames.get(new Random().nextInt(identifyGames.size()));

        answerEditText.setText(actualGame.get_Word());
        tries = 0;
        triesTextView.setText("Número de fallos: " + tries);
    }

    public void checkAnswer(String pType)
    {
        if (pType.matches(actualGame.get_Type()))
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
