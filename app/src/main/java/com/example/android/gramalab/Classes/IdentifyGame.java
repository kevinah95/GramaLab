package com.example.android.gramalab.Classes;

import java.util.ArrayList;

/**
 * Created by Farcem on 12-Apr-16.
 */
public class IdentifyGame
{
    private String _Word;
    private String _Type;

    public IdentifyGame(String _Word, String _Type) {
        this._Word = _Word;
        this._Type = _Type;
    }

    public String get_Word() {
        return _Word;
    }

    public void set_Word(String _Word) {
        this._Word = _Word;
    }

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }
}
