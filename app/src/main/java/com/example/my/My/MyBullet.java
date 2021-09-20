package com.example.my.My;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.my.Base.BaseBullet;
import com.example.my.GameMainView;

public class MyBullet extends BaseBullet {

    private Rect src;
    private RectF des;

    public MyBullet(Resources resources, int pic, float screenW, float screenH) {
        super(resources, pic, screenW, screenH);

        this.src = new Rect(0, 0, (int)(this.getW()), (int) (this.getH()));
        this.setW(this.getW()/2);
        this.setH(this.getH()/2);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, GameMainView gameMainView) {
        this.setY(this.getY()-this.getSpeed());

        if(!this.isLive()){
            return;
        }

        if(this.getY() < - this.getH()){
            this.setLive(false);
        }

        this.des = new RectF(this.getX(), this.getY(), this.getX()+this.getW(), this.getY()+this.getH());

        canvas.save();
        canvas.drawBitmap(this.getBitmap(), this.src, this.des, paint);
        canvas.restore();
    }

    public void release() {
        if (!this.getBitmap().isRecycled()) {
            this.getBitmap().recycle();
        }
    }
}
