package com.example.android.gramalab.logic;

/**
 * Created by jasc9 on 22/4/2016.
 */
public class CorrectGame
{
    private String _WrongSentence;
    private String _CorrectSentece;

    public CorrectGame(String _WrongSentence, String _CorrectSentece) {
        this._WrongSentence = _WrongSentence;
        this._CorrectSentece = _CorrectSentece;
    }

    public String get_WrongSentence() {
        return _WrongSentence;
    }

    public void set_WrongSentence(String _WrongSentence) {
        this._WrongSentence = _WrongSentence;
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
