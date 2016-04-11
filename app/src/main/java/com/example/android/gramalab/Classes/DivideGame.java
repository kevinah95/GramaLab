package com.example.android.gramalab.Classes;

/**
 * Created by Farcem on 10-Apr-16.
 */
public class DivideGame
{
    private String _Word;
    private String _DividedWord;

    public DivideGame(String _Word, String _DividedWord) {
        this._Word = _Word;
        this._DividedWord = _DividedWord;
    }

    public String get_Word() {
        return _Word;
    }

    public void set_Word(String _Word) {
        this._Word = _Word;
    }

    public String get_DividedWord() {
        return _DividedWord;
    }

    public void set_DividedWord(String _DividedWord) {
        this._DividedWord = _DividedWord;
    }

    boolean isCorrect(String pAnswer)
    {
        if(pAnswer.matches(this._DividedWord))
            return true;
        else
            return false;
    }
}
