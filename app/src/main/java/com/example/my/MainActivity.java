package com.example.my;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class MainActivity extends Activity {

    private GameMainView gameMainView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameMainView = new GameMainView(this.getApplicationContext());
        setContentView(gameMainView);
    }
}