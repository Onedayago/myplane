package com.example.my.Enemy;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.my.Base.BaseBullet;
import com.example.my.GameMainView;

public class EnemyBullet extends BaseBullet {

    private Rect src;
    private RectF des;

    public EnemyBullet(Resources resources, int pic, float screenW, float screenH) {
        super(resources, pic, screenW, screenH);
        this.src = new Rect(0, 0, (int)(this.getW()), (int) (this.getH()));
        this.setW(this.getW()/3);
        this.setH(this.getH()/3);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, GameMainView gameMainView) {

        this.setY(this.getY()+this.getSpeed());

        if(this.getY()>this.getScreenH()){
            this.setLive(false);
        }

        this.des = new RectF(this.getX(), this.getY(), this.getX()+this.getW(), this.getY()+this.getH());

        canvas.save();
        canvas.drawBitmap(this.getBitmap(), this.src, this.des, paint);
        canvas.restore();
    }
}
