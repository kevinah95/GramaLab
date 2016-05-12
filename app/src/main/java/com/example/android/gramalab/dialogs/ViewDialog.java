package com.example.android.gramalab.dialogs;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.example.android.gramalab.R;

/**
 * Created by kevinah95 on 23/4/2016.
 */
public class ViewDialog {
    private ImageView imageViewRes;
    private ScrollView scrollView2;
    private ImageView imageView4;

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
        ImageView imageView4 = (ImageView) dialog.findViewById(R.id.imageView4);
        //ScrollView scrollView2 = (ScrollView) dialog.findViewById(R.id.scrollView2);
        //scrollView2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageView4.getHeight()));


        dialog.show();

    }
}
