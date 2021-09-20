package com.example.my;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.my.Base.BaseObj;

public class Back extends BaseObj {

    private float scale_x = 1;
    private float scale_y = 1;

    private float bg_y = 0;
    private float by_y2 = 0;

    public Back(Resources resources, int pic, float screenW, float screenH) {
        super(resources, pic, screenW, screenH);
        this.scale_x = this.getScreenW() / this.getBitmap().getWidth();
        this.scale_y = this.getScreenH() / this.getBitmap().getHeight();
        this.by_y2 = this.bg_y - this.getScreenH();
    }

    @Override
    public void init() {
        this.setBitmap(BitmapFactory.decodeResource(this.getResources(), this.getPic()));
    }

    @Override
    public void draw(Canvas canvas, Paint paint, GameMainView gameMainView) {

        if (bg_y > by_y2) {
            bg_y += 1;
            by_y2 = bg_y - this.getBitmap().getHeight();
        } else {
            by_y2 += 1;
            bg_y = by_y2 - this.getBitmap().getHeight();
        }
        if (bg_y >= this.getBitmap().getHeight()) {
            bg_y = by_y2 - this.getBitmap().getHeight();
        } else if (by_y2 >= this.getBitmap().getHeight()) {
            by_y2 = bg_y - this.getBitmap().getHeight();
        }

        canvas.save();
        canvas.scale(this.scale_x,this.scale_y, 0, 0);
        canvas.drawBitmap(this.getBitmap(), this.getX(), this.by_y2, paint);
        canvas.drawBitmap(this.getBitmap(), this.getX(), this.bg_y, paint);
        canvas.restore();
    }
}
