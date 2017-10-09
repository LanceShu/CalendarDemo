package com.example.lance.calenderdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Lance on 2017/10/9.
 */

public class Calendar_day_TextView extends TextView {
    public boolean isToday = false;
    private Paint paint = new Paint();

    public Calendar_day_TextView(Context context) {
        super(context);
    }

    public Calendar_day_TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context);
    }

    public Calendar_day_TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context);
    }

    private void initControl(Context context){
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ff0000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(isToday){
            canvas.translate(getWidth()/2,getHeight()/2);
            canvas.drawCircle(0,0,getWidth()/2,paint);
        }

    }
}
