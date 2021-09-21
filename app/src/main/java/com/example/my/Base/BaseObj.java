package com.example.my.Base;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.my.GameMainView;
import com.example.my.MainActivity;

public class BaseObj {

    private float X = 0;
    private float Y = 0;
    private float W;
    private float H;
    private boolean isLive = true;
    private long startTime = 0;
    private long endTime = 0;

    public int getLifeNum() {
        return lifeNum;
    }

    public void setLifeNum(int lifeNum) {
        this.lifeNum = lifeNum;
    }

    private int lifeNum = 1;

    public boolean isBoom() {
        return isBoom;
    }

    public void setBoom(boolean boom) {
        isBoom = boom;
    }

    private boolean isBoom = false;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public float getScreenW() {
        return screenW;
    }

    public void setScreenW(float screenW) {
        this.screenW = screenW;
    }

    public float getScreenH() {
        return screenH;
    }

    public void setScreenH(float screenH) {
        this.screenH = screenH;
    }

    private float screenW;
    private float screenH;

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public float getW() {
        return W;
    }

    public void setW(float w) {
        W = w;
    }

    public float getH() {
        return H;
    }

    public void setH(float h) {
        H = h;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    private float speed = 1;
    private Bitmap bitmap;
    private Resources resources;
    private int pic;

    public BaseObj(Resources resources, int pic, float screenW, float screenH){
        this.resources = resources;
        this.pic = pic;
        this.screenW = screenW;
        this.screenH = screenH;
        init();
    }

    public void init() {
        this.bitmap = BitmapFactory.decodeResource(this.resources, this.pic);
        this.W = this.bitmap.getWidth();
        this.H = this.bitmap.getHeight();
    }

    public void draw(Canvas canvas, Paint paint, GameMainView gameMainView){

    }

}
