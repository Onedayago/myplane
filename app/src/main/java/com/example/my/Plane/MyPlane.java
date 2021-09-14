

package com.example.my.Plane;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.my.Base.BaseObj;
import com.example.my.Bullet.MyBullet;
import com.example.my.Constant.Obj;
import com.example.my.Interface.BaseInface;
import com.example.my.R;

import java.util.ArrayList;

public class MyPlane extends BaseObj implements BaseInface {

    public int index = 0;
    private Boolean isTouch = false;


    public ArrayList<MyBullet> MyBulletArr = new ArrayList();

    public MyPlane(Resources resources, int pic) {
        super(resources, pic);
    }

    @Override
    public void initBipMap(){
        this.bitmap = BitmapFactory.decodeResource(resources, this.getPic());
        this.setW(bitmap.getWidth()/3);
        this.setH(bitmap.getHeight());
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        int x = (int) (index * this.getW());
        canvas.clipRect(this.getX(), this.getY(), this.getX()+this.getW(), this.getY()+this.getH());
        canvas.drawBitmap(bitmap, this.getX()-x, this.getY(), paint);
        canvas.restore();

        this.setStartTime(System.currentTimeMillis());

        if(this.getStartTime() - this.getEndTime()>Obj.MYPLANESHOOT_SPEED){
            this.setEndTime(this.getStartTime());
            shoot();
        }
        this.drowBullet(canvas, paint);
    }

    //绘制子弹
    private void drowBullet(Canvas canvas, Paint paint) {
        for(int i = 0; i < MyBulletArr.size(); i++){
            if(MyBulletArr.get(i).getY() < -MyBulletArr.get(i).getH()){
                MyBulletArr.get(i).release();
                MyBulletArr.remove(i);
            }
            MyBulletArr.get(i).draw(canvas, paint);
        }
    }

    private void shoot() {
        MyBullet bullet = new MyBullet(this.getResources(), R.drawable.my_bullet_blue);
        bullet.setSpeed(Obj.MYBULLET_SPEED);
        bullet.setX(this.getX()+this.getW()/2-bullet.getW()/2);
        bullet.setY(this.getY()+this.getH()/2-bullet.getH());
        MyBulletArr.add(bullet);
    }

    @Override
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

                if(tempX >= this.getScreen_width()-this.getW()){
                    tempX = this.getScreen_width() - this.getW();
                }

                if(tempY<= 0){
                    tempY = 0;
                }

                if(tempY>= this.getScreen_height()-this.getH()){
                    tempY = this.getScreen_height()-this.getH();
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
