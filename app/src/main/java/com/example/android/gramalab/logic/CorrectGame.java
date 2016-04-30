package com.example.android.gramalab.logic;

/**
 * Created by jasc9 on 22/4/2016.
 */
public class CorrectGame
{
    private String _Sentence;
    private String _BadWord;
    private String _CorrectWord;

    public CorrectGame(String _Sentence, String _BadWord, String _CorrectWord)
    {
        this._Sentence = _Sentence;
        this._BadWord = _BadWord;
        this._CorrectWord = _CorrectWord;
    }

    public String get_Sentence() {
        return _Sentence;
    }

    public void set_Sentence(String _Sentence) {
        this._Sentence = _Sentence;
    }

    public String get_CorrectWord() {
        return _CorrectWord;
    }

    public void set_CorrectSentece(String _CorrectSentece) {
        this._CorrectWord = _CorrectSentece;
    }

    public String get_BadWord() {
        return _BadWord;
    }
}
