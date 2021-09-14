package com.example.my.Base;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import com.example.my.GameMainView;

public class BaseObj {

    private float x;
    private float y;
    private float w;
    private float h;
    private long startTime = 0;

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

    private long endTime = 0;
    public float getW() {
        return w;
    }
    public void setW(float w) {
        this.w = w;
    }
    public float getH() {
        return h;
    }
    public void setH(float h) {
        this.h = h;
    }
    public float getSpeed() {
        return speed;
    }

    private float speed;
    
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    protected Bitmap bitmap;

    public Resources getResources() {
        return resources;
    }

    protected Resources resources;

    public int getPic() {
        return pic;
    }

    private int pic;

    private float screen_width;

    public float getScreen_width() {
        return screen_width;
    }

    public float getScreen_height() {
        return screen_height;
    }

    private float screen_height;

    public void setScreen_width(float screen_width) {
        this.screen_width = screen_width;
    }

    public void setScreen_height(float screen_height) {
        this.screen_height = screen_height;
    }

    public BaseObj(Resources resources, int pic){
        this.resources = resources;
        this.pic = pic;
        initBipMap();
    }

    public void initBipMap() {
        this.bitmap = BitmapFactory.decodeResource(resources, pic);
        this.w = bitmap.getWidth();
        this.h = bitmap.getHeight();

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void release() {
        if (!this.bitmap.isRecycled()) {
            this.bitmap.recycle();
        }
    }
}
