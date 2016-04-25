package com.example.android.gramalab.logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jasc9 on 22/4/2016.
 */
public class CorrectGame
{
    private String _Sentence;
    private ArrayList<String> _Words;
    private String _CorrectWord;

    public CorrectGame(String _Sentence, String _Words, String _CorrectWord)
    {
        this._Sentence = _Sentence;
        this._Words = new ArrayList<>(Arrays.asList(_Words.split(" ")));
        this._CorrectWord = _CorrectWord;
    }

    public String get_Sentence() {
        return _Sentence;
    }

    public void set_Sentence(String _Sentence) {
        this._Sentence = _Sentence;
    }

    public ArrayList<String> get_Words() {
        return _Words;
    }

    public void set_Words(ArrayList<String> _Words) {
        this._Words = _Words;
    }

    public String get_CorrectWord() {
        return _CorrectWord;
    }

    public void set_CorrectSentece(String _CorrectSentece) {
        this._CorrectWord = _CorrectSentece;
    }
}
