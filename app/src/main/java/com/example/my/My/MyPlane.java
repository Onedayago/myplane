package com.example.my.My;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.my.Base.BasePlane;
import com.example.my.Constant.Game;
import com.example.my.Constant.GameSound;
import com.example.my.GameMainView;
import com.example.my.GameSoundPool;
import com.example.my.R;

public class MyPlane extends BasePlane {

    private boolean isTouch;

    public MyPlane(Resources resources, int pic, float screenW, float screenH) {
        super(resources, pic, screenW, screenH);
    }

    @Override
    public void init() {
        this.setBitmap(BitmapFactory.decodeResource(this.getResources(), this.getPic()));
        this.setW(this.getBitmap().getWidth()/3);
        this.setH(this.getBitmap().getHeight());
    }

    @Override
    public void draw(Canvas canvas, Paint paint, GameMainView gameMainView) {
        canvas.save();

        canvas.clipRect(this.getX(), this.getY(), this.getX()+this.getW(), this.getY()+this.getH());
        canvas.drawBitmap(this.getBitmap(), this.getX(), this.getY(), paint);
        canvas.restore();


        this.setEndTime(System.currentTimeMillis());

        if(this.getEndTime() - this.getStartTime() > this.getShootSpeed()){
            this.setStartTime(this.getEndTime());
            this.shoot(gameMainView);
        }
    }

    @Override
    public void shoot(GameMainView gameMainView) {
        MyBullet myBullet = new MyBullet(this.getResources(), R.drawable.my_bullet_blue, this.getScreenW(), this.getScreenH());
        myBullet.setX(this.getX()+(this.getW()/2-myBullet.getW()/2));
        myBullet.setY(this.getY()-myBullet.getH());
        myBullet.setSpeed(Game.MYBULLETSPEED);
        gameMainView.myBullets.add(myBullet);
        GameSoundPool.play(GameSound.SHOOT,0);
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(x>= this.getX() && x<=this.getX()+this.getW() && y>= this.getY() && y<=this.getY()+this.getH()){
                this.isTouch = true;
                return true;
            }
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            if(this.isTouch){

                float tempX = x - this.getW()/2;
                float tempY = y - this.getH()/2;
                if(tempX <= 0){
                    tempX = 0;
                }

                if(tempX >= this.getScreenW()-this.getW()){
                    tempX = this.getScreenW() - this.getW();
                }

                if(tempY<= 0){
                    tempY = 0;
                }

                if(tempY>= this.getScreenH()-this.getH()){
                    tempY = this.getScreenH()-this.getH();
                }

                this.setX(tempX);
                this.setY(tempY);
                return true;
            }

        }else if(event.getAction() == MotionEvent.ACTION_UP){
            if(this.isTouch){
                this.isTouch = false;
                return true;
            }

        }
        return false;
    }

}
