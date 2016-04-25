package com.example.android.gramalab.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.android.gramalab.R;

/**
 * Created by kevinah95 on 23/4/2016.
 */
public class ViewDialog {
    private ImageView imageViewRes;

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_about);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

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
