package com.example.android.gramalab.Classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Farcem on 10-Apr-16.
 */
public class OrderGame
{
    private ArrayList<String> _Words;
    private String _CorrectSentece;

    public OrderGame(String _CorrectSentece)
    {
        this._Words = new ArrayList<>(Arrays.asList(_CorrectSentece.split(" ")));
        this._CorrectSentece = _CorrectSentece;
    }

    public String get_Words()
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
