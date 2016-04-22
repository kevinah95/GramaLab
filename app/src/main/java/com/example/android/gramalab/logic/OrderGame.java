package com.example.android.gramalab.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by jasc9 on 22/4/2016.
 */
public class OrderGame
{
    private ArrayList<String> _Words;
    private String _CorrectSentece;

    public OrderGame(String _CorrectSentece)
    {
        this._Words = new ArrayList<>(Arrays.asList(_CorrectSentece.split(" ")));
        Collections.shuffle(this._Words);
        this._CorrectSentece = _CorrectSentece;
    }


    public ArrayList<String> get_Words()
    {
        return _Words;
    }

    public String get_ShuffleWords()
    {
        String shuffleSentence = "";
        Collections.shuffle(this._Words);
        for (String word : this._Words)
        {
            shuffleSentence += word + " ";
        }
        return shuffleSentence;
    }

    public void set_Words(ArrayList<String> _Words) {
        this._Words = _Words;
    }

    public String get_CorrectSentece() {
        return _CorrectSentece;
    }

    public void set_CorrectSentece(String _CorrectSentece) {
        this._CorrectSentece = _CorrectSentece;
    }

    boolean isCorrect(String pAnswer)
    {
        if(pAnswer.matches(this._CorrectSentece))
            return true;
        else
            return false;
    }
}