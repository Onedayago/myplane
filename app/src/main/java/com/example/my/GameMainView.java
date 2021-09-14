/*
    游戏主界面
 */
package com.example.my;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import com.example.my.Enemy.EnemySmall;
import com.example.my.Plane.MyPlane;

import java.util.ArrayList;

public class GameMainView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private Paint paint;  //画笔

    private SurfaceHolder surfaceHolder;

    private Thread thread; //绘制线程

    private Canvas canvas; //

    private MyPlane myPlane; //我的飞机

    private  long startTime = 0; //这次绘制的开始时间
    private long endTime = 0; //这次绘制的结束时间

    private Bitmap bg;

    private float screen_width;
    private float screen_height;
    private float scale_x = 1;
    private float scale_y = 1;

    private float bg_y = 0;
    private float by_y2 = 0;

    private ArrayList<EnemySmall> enemySmalls = new ArrayList<>();

    public GameMainView(Context context) {
        super(context);
        init();
    }

    private void init() {
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();
        thread = new Thread(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        screen_width = this.getWidth();
        screen_height = this.getHeight();

        //我的飞机初始化
        myPlane = new MyPlane(getResources(), R.drawable.myplane);
        myPlane.setX((screen_width-myPlane.getW())/2);
        myPlane.setY(screen_height-myPlane.getH());
        myPlane.setScreen_width(screen_width);
        myPlane.setScreen_height(screen_height);

        //背景初始化
        bg = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);

        this.scale_x = screen_width / bg.getWidth();
        this.scale_y = screen_height / bg.getHeight();

        this.by_y2 = this.bg_y - screen_height;

        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(myPlane.onTouchEvent(event)){
            return true;
        }

        return false;
    }

    @Override
    public void run() {
        while (true){
            startTime = System.currentTimeMillis();
            try {
                canvas = surfaceHolder.lockHardwareCanvas();
                canvas.drawColor(Color.BLACK);
                this.drawBg();
                this.drawMyPlane();
                this.drawEnemySmall();
                this.boom();
            }catch (Exception e){

            }finally {
                if (canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            }


            if(startTime - endTime >2000){
                endTime = startTime;

                //生产敌机
                EnemySmall enemySmall = new EnemySmall(getResources(), R.drawable.small);
                enemySmall.setScreen_width(screen_width);
                enemySmall.setScreen_height(screen_height);
                enemySmall.setX((float) (Math.random()*screen_width));
                enemySmall.setY(0);
                enemySmalls.add(enemySmall);
            }

        }
    }

    //我的子弹打中小型敌机
    public void boom () {
        for(int i = 0; i < enemySmalls.size(); i++){
            for(int j= 0; j< myPlane.MyBulletArr.size(); j++){
                enemySmalls.get(i).boom(myPlane.MyBulletArr.get(j));
            }
        }
    }

    //绘制小型敌机
    public void drawEnemySmall () {
        for(int i = 0; i < enemySmalls.size(); i++){
            if(enemySmalls.get(i).getY() > screen_height || !enemySmalls.get(i).isLive()){
                enemySmalls.get(i).release();
                enemySmalls.remove(i);
            }
            enemySmalls.get(i).draw(canvas, paint);
        }
    }

    //画背景
    public void drawBg() {

        if (bg_y > by_y2) {
            bg_y += 1;
            by_y2 = bg_y - bg.getHeight();
        } else {
            by_y2 += 1;
            bg_y = by_y2 - bg.getHeight();
        }
        if (bg_y >= bg.getHeight()) {
            bg_y = by_y2 - bg.getHeight();
        } else if (by_y2 >= bg.getHeight()) {
            by_y2 = bg_y - bg.getHeight();
        }

        canvas.save();
        canvas.scale(this.scale_x,this.scale_y, 0, 0);
        canvas.drawBitmap(bg, 0, this.by_y2, paint);
        canvas.drawBitmap(bg, 0, this.bg_y, paint);
        canvas.restore();
    }

    //画我的飞机
    public void drawMyPlane() {
        myPlane.draw(canvas, paint);
    }
}
