package com.example.my.Enemy;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.my.Base.BasePlane;
import com.example.my.Constant.Game;
import com.example.my.Constant.GameSound;
import com.example.my.GameMainView;
import com.example.my.GameSoundPool;
import com.example.my.My.MyBullet;
import com.example.my.R;

public class EnemyPlane extends BasePlane {

    private int index = 0;
    private static int boomTime = 100;
    private int moveDirection = 1;

    public EnemyPlane(Resources resources, int pic, float screenW, float screenH) {
        super(resources, pic, screenW, screenH);
    }

    @Override
    public void init() {
        this.setBitmap(BitmapFactory.decodeResource(this.getResources(), this.getPic()));
        this.setW(this.getBitmap().getWidth());
        this.setH(this.getBitmap().getHeight()/3);
    }

    @Override
    public void draw(Canvas canvas, Paint paint, GameMainView gameMainView) {

        if(!this.isLive()){
            return;
        }

        if(this.getY() > this.getScreenH()) {
            this.setLive(false);
        }

        int y = 0;

        if(this.isBoom() && index <=2){
            this.setEndTime(System.currentTimeMillis());
            if(this.getEndTime() - this.getStartTime() > boomTime){
                this.setStartTime(this.getEndTime());
                index++;
            }
            y = (int) (index * this.getH());
        }else{
            this.setY(this.getY()+this.getSpeed());

            this.setX(this.getX()+this.moveDirection*this.getSpeed());

            if(this.getX()<0){
                this.setX(0);
                this.moveDirection = 1;
            }else if(this.getX() > this.getScreenW()-this.getW()){
                this.setX(this.getScreenW()-this.getW());
                this.moveDirection = -1;
            }
        }

        canvas.save();
        canvas.clipRect(this.getX(), this.getY(), this.getX()+this.getW(), this.getY()+this.getH());
        canvas.drawBitmap(this.getBitmap(), this.getX(), this.getY()-y, paint);
        canvas.restore();

        if(index > 2){
            this.setLive(false);
        }

        this.setEndTime(System.currentTimeMillis());

        if(this.getEndTime() - this.getStartTime() > this.getShootSpeed()){
            this.setStartTime(this.getEndTime());
            this.shoot(gameMainView);
        }
    }

    @Override
    public void shoot(GameMainView gameMainView) {
        EnemyBullet enemyBullet = new EnemyBullet(this.getResources(), R.drawable.bossbullet_default, this.getScreenW(), this.getScreenH());
        enemyBullet.setX(this.getX()+(this.getW()/2-enemyBullet.getW()/2));
        enemyBullet.setY(this.getY()+enemyBullet.getH()/2);
        enemyBullet.setSpeed(Game.ENEMYBULLETSPEED);
        gameMainView.enemyBullets.add(enemyBullet);
    }
}
