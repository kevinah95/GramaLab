package games.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.gramalab.R;

import java.util.ArrayList;
import java.util.Random;

import games.logic.CompleteGame;


public class CompleteGameActivity extends AppCompatActivity
{
    ArrayList<CompleteGame> completeGames = new ArrayList<>();
    CompleteGame actualGame;

    EditText sentenceEditText;
    TextView wordTextView;
    EditText answerEditText;
    TextView triesTextView;

    int tries;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_game);

        /*
        Aqui haria como un ciclo donde agrega a completeGames lo que haya en la base de datos
        */
        completeGames.add(new CompleteGame("Rodrigo [Verbo] todo el día", "Verbo: Correr", "corre"));
        completeGames.add(new CompleteGame("Carlos esta [Verbo] en este momento", "Verbo: Nadar", "nadando"));
        completeGames.add(new CompleteGame("Tienen que [Verbo] mucho para este examen","Verbo: Estudiar","estudiar"));
        //Aqui termina el ciclo

        sentenceEditText = (EditText)findViewById(R.id.sentenceEditTextComplete);
        wordTextView = (TextView)findViewById(R.id.wordTextViewComplete);
        answerEditText = (EditText)findViewById(R.id.answerEditTextComplete);
        triesTextView = (TextView)findViewById(R.id.triesTextViewComplete);

        sentenceEditText.setKeyListener(null);

        setGame();
    }

    void setGame()
    {
        CompleteGame completeGame = completeGames.get(new Random().nextInt(completeGames.size()));
        sentenceEditText.setText(completeGame.get_Sentence());
        wordTextView.setText(completeGame.get_Word());
        answerEditText.setText("");
        tries = 0;
        triesTextView.setText("Número de fallos: " + tries);
        actualGame = completeGame;
    }

    public void checkAnswer(View v)
    {
        if(answerEditText.getText().toString().matches(""))
            Toast.makeText(this, "Debes escribir una respuesta", Toast.LENGTH_SHORT).show();
        else
        {
            if (answerEditText.getText().toString().matches(actualGame.get_Answer())) {
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
