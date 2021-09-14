package com.example.my.Interface;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

public interface BaseInface {

    void draw(Canvas canvas, Paint paint);

    boolean onTouchEvent(MotionEvent event);
}
