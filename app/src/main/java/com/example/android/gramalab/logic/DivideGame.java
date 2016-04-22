package com.example.android.gramalab.logic;

/**
 * Created by jasc9 on 22/4/2016.
 */
public class DivideGame
{
    private String _Word;
    private String _DividedWord;
    private int _SysAmount;

    public DivideGame(String _Word, String _DividedWord, int sylAmount) {
        this._Word = _Word;
        this._DividedWord = _DividedWord;
        this._SysAmount = sylAmount;
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

    public int get_SysAmount() {
        return _SysAmount;
    }

    public void set_SysAmount(int _SysAmount) {
        this._SysAmount = _SysAmount;
    }

    boolean isCorrect(String pAnswer)
    {
        if(pAnswer.matches(this._DividedWord))
            return true;
        else
            return false;
    }
}
