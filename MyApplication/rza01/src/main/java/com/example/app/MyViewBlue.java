package com.example.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by zhiai.ren on 2016/11/7.
 */
public class MyViewBlue extends View{

    Paint p=new Paint();
    public MyViewBlue(Context context) {
        super(context);
    }
    public void onDraw(Canvas c){
        p.setColor(Color.BLUE);
        c.drawRect(50,50,450,450,p);
        invalidate();
    }
}
