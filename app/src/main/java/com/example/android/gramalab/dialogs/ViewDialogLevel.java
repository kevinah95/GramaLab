package com.example.android.gramalab.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.android.gramalab.R;
import com.example.android.gramalab.activities.MainActivity;

/**
 * Created by kevinah95 on 23/4/2016.
 */
public class ViewDialogLevel {

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_level);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageButton btn_level_easy = (ImageButton) dialog.findViewById(R.id.btn_level_easy);
        btn_level_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.gameSeconds = 100;
                dialog.dismiss();
            }
        });
        ImageButton btn_level_medium = (ImageButton) dialog.findViewById(R.id.btn_level_medium);
        btn_level_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.gameSeconds = 60;
                dialog.dismiss();
            }
        });

        ImageButton btn_level_advance = (ImageButton) dialog.findViewById(R.id.btn_level_advance);
        btn_level_advance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.gameSeconds = 30;
                dialog.dismiss();
            }
        });

        ImageButton dialogButton = (ImageButton) dialog.findViewById(R.id.btn_about_close);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
