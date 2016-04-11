package com.example.android.gramalab;

/**
 * Created by Farcem on 10-Apr-16.
 */
public class CompleteGame
{
    private String _Sentence;
    private String _Word;
    private String _Answer;

    public CompleteGame(String _Sentence, String _Word, String _Answer)
    {
        this._Sentence = _Sentence;
        this._Word = _Word;
        this._Answer = _Answer;
    }

    public String get_Word() {
        return _Word;
    }

    public void set_Word(String _Word) {
        this._Word = _Word;
    }

    public String get_Sentence() {
        return _Sentence;
    }

    public void set_Sentence(String _Sentence) {
        this._Sentence = _Sentence;
    }

    public String get_Answer() {
        return _Answer;
    }

    public void set_Answer(String _Answer) {
        this._Answer = _Answer;
    }

    boolean isCorrect(String pAnswer)
    {
        if(pAnswer.matches(this._Answer))
            return true;
        else
            return false;
    }
}
