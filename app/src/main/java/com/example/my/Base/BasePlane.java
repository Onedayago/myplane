package com.example.my.Base;

import android.content.res.Resources;

import com.example.my.GameMainView;

public class BasePlane extends BaseObj {

    private float shootSpeed;

    public float getShootSpeed() {
        return shootSpeed;
    }

    public void setShootSpeed(float shootSpeed) {
        this.shootSpeed = shootSpeed;
    }

    public BasePlane(Resources resources, int pic, float screenW, float screenH) {
        super(resources, pic, screenW, screenH);
    }


    public void shoot(GameMainView gameMainView){

    }

}
