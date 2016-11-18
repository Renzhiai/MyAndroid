package com.example.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhiai.ren on 2016/11/14.
 */
public class MyView extends View{

    public int currentX,currentY;
    Bitmap plane;
    Paint paint=new Paint();

    public MyView(Context context) {
        super(context);
        plane= BitmapFactory.decodeResource(context.getResources(),
                R.drawable.plane);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        canvas.drawBitmap(plane,currentX,currentY,paint);

    }
}
