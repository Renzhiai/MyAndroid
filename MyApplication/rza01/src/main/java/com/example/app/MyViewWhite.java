package com.example.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by zhiai.ren on 2016/11/7.
 */
public class MyViewWhite extends View{

    Paint p=new Paint();
    public MyViewWhite(Context context) {
        super(context);
    }
    public void onDraw(Canvas c){
        p.setColor(Color.WHITE);
        c.drawRect(100,100,400,400,p);
        invalidate();
    }
}
