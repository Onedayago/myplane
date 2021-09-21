
package com.example.my;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

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
    private Bitmap btnBit;
    private boolean run = false;
    private boolean isFirst = true;
    private long produceStart = 0;
    private long produceEnd = 0;
    private MainActivity mainActivity;
    private AlertDialog alertDialog;
    private Context context;

    public GameMainView(Context context, MainActivity mainActivity) {
        super(context);
        this.surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        this.mainActivity = mainActivity;
        btnBit = BitmapFactory.decodeResource(this.getResources(), R.drawable.play);



        this.alertDialog = new AlertDialog.Builder(this.mainActivity, R.style.Theme_AppCompat_Light_Dialog)
                .setTitle("游戏结束")
                .setMessage("是否继续游戏")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        initMyPlane();
                        run = true;
                        thread = new Thread(GameMainView.this);
                        thread.start();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        if(this.isFirst){
            this.screenW = this.getWidth();
            this.screenH = this.getHeight();
            paint = new Paint();
            this.initMyPlane();
            this.initBack();
            this.isFirst = false;
        }
        this.thread = new Thread(this);
        this.thread.start();
    }

    private void initBack() {
        back = new Back(this.getResources(), R.drawable.bg_01, this.screenW, this.screenH);
    }

    private void initMyPlane() {
        myPlane = new MyPlane(this.getResources(), R.drawable.myplane, this.screenW, this.screenH);
        myPlane.setX(this.screenW/2-myPlane.getW()/2);
        myPlane.setY(this.screenH-myPlane.getH());
        myPlane.setShootSpeed(Game.MYPLANESHOOTSPEED);
        myPlane.setLifeNum(Game.MYPLANELIFE);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        this.destroyThread();
        this.run = false;
    }

    private void destroyThread() {
        try {
            if (null != thread && Thread.State.RUNNABLE == thread.getState()) {
                try {
                    Thread.sleep(10);
                    thread.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            thread = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        if(myPlane.onTouchEvent(event)){
            return true;
        }

        if(x>=0 && x <= btnBit.getWidth() && y>=0 && y<= btnBit.getHeight() && event.getAction() == MotionEvent.ACTION_DOWN){
            if(this.run){
                this.run = false;
            }else{
                this.run = true;
                this.thread = new Thread(this);
                this.thread.start();
            }

            return true;
        }


        return false;
    }

    @Override
    public void run() {
        while (null !=this.thread && !this.thread.isInterrupted()){
            try {
                canvas = surfaceHolder.lockHardwareCanvas();

                canvas.drawColor(Color.BLACK);
                this.drawBack();
                this.drawMyStatus();
                this.drawBtn();
                this.drawMyPlane();
                this.drawMyBullet();
                this.drawEnemy();
                this.boom();
                this.drawEnemyBullets();
                this.endtTime = System.currentTimeMillis();
                if(this.endtTime - this.startTime >= 100){
                    this.startTime = this.endtTime;
                    MyTimer.addTime(1);
                }

                this.produceEnd  = MyTimer.getTime();
                if(this.produceEnd - this.produceStart >= Game.PRODUCEENEMYSPEED){
                    this.produceStart = this.produceEnd;
                    this.produceEnemy();
                }


            }catch (Exception e){
                Log.d("", e.toString());
            }finally {
                if(canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    private void drawBtn() {
        canvas.save();

        if(this.run){
            canvas.clipRect(0,0,btnBit.getWidth(),btnBit.getHeight()/2);
            canvas.drawBitmap(btnBit,0,0,this.paint);
        }else {
            canvas.clipRect(0,0,btnBit.getWidth(),btnBit.getHeight()/2);
            canvas.drawBitmap(btnBit,0,-btnBit.getHeight()/2,this.paint);
            this.destroyThread();
        }

        canvas.restore();
    }

    private void drawMyStatus() {
        canvas.save();
        paint.setTextSize(60);
        paint.setColor(Color.RED);
        canvas.drawText("生命: "+myPlane.getLifeNum(), this.screenW-60*(2+String.valueOf(myPlane.getLifeNum()).length()), 60, this.paint);
        canvas.drawText("得分: "+myPlane.getScoreNum(), this.screenW-60*(2+String.valueOf(myPlane.getScoreNum()).length()), 140, this.paint);
        canvas.restore();
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
                    myPlane.setScoreNum(myPlane.getScoreNum()+1);
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
            ) && enemyPlane.isLive() && !enemyPlane.isBoom() && myPlane.isLive()){
                enemyPlane.setBoom(true);
                myPlane.setLifeNum(myPlane.getLifeNum()-1);
            }

        }

        for(int i = 0; i<enemyBullets.size();i++){
            EnemyBullet enemyBullet = enemyBullets.get(i);

            if(this.CheckRectCollsion(
                    enemyBullet.getX(),
                    enemyBullet.getY(),
                    enemyBullet.getW(),
                    enemyBullet.getH(),
                    myPlane.getX(),
                    myPlane.getY(),
                    myPlane.getW(),
                    myPlane.getH()
            ) && myPlane.isLive()){
                myPlane.setLifeNum(myPlane.getLifeNum()-1);
                enemyBullet.setLive(false);
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
        enemyPlane.setMoveDirection(Math.random()>0.5?1:-1);
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

        if(!myPlane.isLive()){
            this.run = false;

            this.mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!alertDialog.isShowing()){
                        alertDialog.show();
                    }

                }
            });

        }

        myPlane.draw(this.canvas, this.paint, this);
    }


}
