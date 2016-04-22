package com.example.android.gramalab.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

import com.example.android.gramalab.activities.MainActivity;

/**
 * Created by Farcem on 21-Apr-16.
 */
public class Timer
{
    public Timer(int Seconds, final Activity context)
    {
        new CountDownTimer(Seconds * 1000, 1*1000)
        {
            public void onTick(long millisUntilFinished)
            {
                final Toast toast = Toast.makeText(context, (millisUntilFinished / 1000) + "", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP| Gravity.RIGHT, 0, 0);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 1000);
            }

            public void onFinish()
            {
                if(MainActivity.isCompletePlayed && MainActivity.isIdentifyPlayed)
                {
                    MainActivity.isCompletePlayed = false;
                    MainActivity.isIdentifyPlayed = false;
                    new AlertDialog.Builder(context)
                            .setTitle("Fin del juego")
                            .setMessage("Puntuación final: " + MainActivity.score)
                            .setPositiveButton("Volver a jugar", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    Intent intent = new Intent(context, MainActivity.class);
                                    context.startActivity(intent);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();
                }
                else
                {
                    context.finish();
                }
            }
        }.start();
    }
}
