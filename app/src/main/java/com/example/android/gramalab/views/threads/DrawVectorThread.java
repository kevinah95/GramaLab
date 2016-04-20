package com.example.android.gramalab.views.threads;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.android.gramalab.views.games.DrawVectorView;

/**
 * Created by jasc9 on 19/4/2016.
 */
public class DrawVectorThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private DrawVectorView customView;
    private boolean run;

    public DrawVectorThread(DrawVectorView pCustomView, SurfaceHolder pSurfaceHolder) {
        this.customView = pCustomView;
        this.surfaceHolder = pSurfaceHolder;
        run = false;
    }

    public void setRunning(boolean run) {
        this.run = run;
    }

    public void run() {
        Canvas canvas;
        while(run) {
            canvas = new Canvas();
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    if(canvas != null) {
                        customView.draw(canvas);
                    }
                }
            } finally {
                if(canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
