
package com.example.my;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.my.Constant.Game;
import com.example.my.Constant.GameSound;
import com.example.my.Enemy.EnemyBullet;
import com.example.my.Enemy.EnemyPlane;
import com.example.my.My.MyBullet;
import com.example.my.My.MyPlane;

import java.util.ArrayList;

public class GameMainView extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private Canvas canvas;  //画布
    private SurfaceHolder surfaceHolder;
    private float screenW;
    private float screenH;
    private Paint paint;
    private Thread thread;
    private Back back;
    private MyPlane myPlane;
    public ArrayList<MyBullet> myBullets = new ArrayList<MyBullet>();
    public ArrayList<EnemyPlane> enemyPlanes = new ArrayList<>();
    public ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();
    private long startTime = 0;
    private long endtTime = 0;


    public GameMainView(Context context) {
        super(context);
        this.surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.screenW = this.getWidth();
        this.screenH = this.getHeight();
        paint = new Paint();
        thread = new Thread(this);

        this.initMyPlane();
        this.initBack();

        if(!thread.isAlive()){
            thread.start();
        }
    }

    private void initBack() {
        back = new Back(this.getResources(), R.drawable.bg_01, this.screenW, this.screenH);
    }

    private void initMyPlane() {
        myPlane = new MyPlane(this.getResources(), R.drawable.myplane, this.screenW, this.screenH);
        myPlane.setX(this.screenW/2-myPlane.getW()/2);
        myPlane.setY(this.screenH-myPlane.getH());
        myPlane.setShootSpeed(Game.MYPLANESHOOTSPEED);
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

            this.endtTime = System.currentTimeMillis();

            if(this.endtTime - this.startTime > Game.PRODUCEENEMYSPEED){
                this.startTime = this.endtTime;
                this.produceEnemy();
            }

            try {
                canvas = surfaceHolder.lockHardwareCanvas();
                canvas.drawColor(Color.BLACK);
                this.drawBack();
                this.drawMyPlane();
                this.drawMyBullet();
                this.drawEnemy();
                this.boom();
                this.drawEnemyBullets();
            }catch (Exception e){

            }finally {
                if(canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void drawEnemyBullets() {
        for(int i = 0; i < enemyBullets.size(); i++){
            if(!enemyBullets.get(i).isLive()){
                enemyBullets.remove(i);
            }
        }

        for(int i = 0; i < enemyBullets.size(); i++){
            enemyBullets.get(i).draw(this.canvas, this.paint, this);
        }
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

    private void boom() {
        for(int i = 0; i < enemyPlanes.size(); i++){
            EnemyPlane enemyPlane = enemyPlanes.get(i);
            for(int j = 0; j < myBullets.size(); j++){
                MyBullet myBullet = myBullets.get(j);
                if(this.CheckRectCollsion(
                        enemyPlane.getX(),
                        enemyPlane.getY(),
                        enemyPlane.getW(),
                        enemyPlane.getH(),
                        myBullet.getX(),
                        myBullet.getY(),
                        myBullet.getW(),
                        myBullet.getH()
                )){
                    enemyPlane.setBoom(true);
                    myBullet.setLive(false);
                    GameSoundPool.play(GameSound.EXPLOSION,0);
                    break;
                }
            }

            if(this.CheckRectCollsion(
                    enemyPlane.getX(),
                    enemyPlane.getY(),
                    enemyPlane.getW(),
                    enemyPlane.getH(),
                    myPlane.getX(),
                    myPlane.getY(),
                    myPlane.getW(),
                    myPlane.getH()
            )){
                enemyPlane.setBoom(true);
            }

        }

    }

    private void drawEnemy() {

        for(int i = 0; i < enemyPlanes.size(); i++){
            if(!enemyPlanes.get(i).isLive()){
                enemyPlanes.remove(i);
            }
        }

        for(int i = 0; i < enemyPlanes.size(); i++){
            enemyPlanes.get(i).draw(this.canvas, this.paint, this);
        }
    }

    private void produceEnemy() {
        EnemyPlane enemyPlane = new EnemyPlane(this.getResources(), R.drawable.small, this.screenW, this.screenH);

        enemyPlane.setX((float) (Math.random()*(this.screenW-enemyPlane.getW())));
        enemyPlane.setY(-enemyPlane.getH());
        enemyPlane.setSpeed(Game.ENEMYSPEED);
        enemyPlane.setShootSpeed(Game.ENEMYSHOOTSPEED);
        enemyPlanes.add(enemyPlane);
    }

    private void drawMyBullet() {

        for(int i = 0; i < myBullets.size(); i++){
            if(!myBullets.get(i).isLive()){
                myBullets.remove(i);
            }
        }

        for(int i = 0; i < myBullets.size(); i++){
            myBullets.get(i).draw(this.canvas, this.paint, this);
        }
    }

    private void drawBack() {
        back.draw(this.canvas, this.paint, this);
    }

    private void drawMyPlane() {
        myPlane.draw(this.canvas, this.paint, this);
    }


}
