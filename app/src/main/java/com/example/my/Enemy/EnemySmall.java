package com.example.my.Enemy;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.my.Base.BaseObj;
import com.example.my.Constant.Obj;
import com.example.my.Interface.BaseInface;


public class EnemySmall extends BaseObj implements BaseInface {

    private int index = 0;

    public boolean isLive() {
        return isLive;
    }
    private boolean isLive = true;

    private boolean isBoom = false;

    public EnemySmall(Resources resources, int pic) {
        super(resources, pic);
    }

    @Override
    public void initBipMap() {
        this.bitmap = BitmapFactory.decodeResource(resources, this.getPic());
        this.setW(bitmap.getWidth());
        this.setH(bitmap.getHeight()/3);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();

        if(this.isBoom && index <=2){
            this.setStartTime(System.currentTimeMillis());
            if(this.getStartTime() - this.getEndTime() > 100){
                this.setEndTime(this.getStartTime());
                index++;
            }
            int y = (int) (index * this.getH());
            canvas.clipRect(this.getX(), this.getY(), this.getX()+this.getW(), this.getY()+this.getH());
            canvas.drawBitmap(bitmap, this.getX(), this.getY()-y, paint);
            canvas.restore();
        }else{
            this.setY(this.getY()+Obj.ENEMYSMALL_SPEED);
            int y = (int) (index * this.getH());
            canvas.clipRect(this.getX(), this.getY(), this.getX()+this.getW(), this.getY()+this.getH());
            canvas.drawBitmap(bitmap, this.getX(), this.getY()-y, paint);
            canvas.restore();
        }

        if(index >2){
            this.isLive = false;
            this.release();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public boolean CheckRectCollsion(float x1, float y1, float w1, float h1, float x2, float y2, float w2, float h2) {
        if (x1 >= x2 && x1 >= x2 + w2) {
            return false;
        } else if (x1 <= x2 && x1 + w1 <= x2) {
            return false;
        } else if (y1 >= y2 && y1 >= y2 + h2) {
            return false;
        } else if (y1 <= y2 && y1 + h1 <= y2) {
            return false;
        }
        return true;
    }

    public void boom (BaseObj baseObj) {
        if(this.CheckRectCollsion(this.getX(), this.getY(), this.getW(), this.getH(), baseObj.getX(), baseObj.getY(), baseObj.getW(), baseObj.getH())){
            this.isBoom = true;
        }
    }

}
