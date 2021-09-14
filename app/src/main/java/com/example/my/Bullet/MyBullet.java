package com.example.my.Bullet;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.my.Base.BaseObj;
import com.example.my.Interface.BaseInface;


public class MyBullet extends BaseObj implements BaseInface {

    public MyBullet(Resources resources, int pic) {
        super(resources, pic);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        this.setY(this.getY()-this.getSpeed());
        canvas.drawBitmap(bitmap, this.getX(), this.getY(), paint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
