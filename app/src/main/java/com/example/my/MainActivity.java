package com.example.my;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

public class MainActivity extends Activity {

    private GameMainView gameMainView;
    private GameSoundPool gameSoundPool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameMainView = new GameMainView(this.getApplicationContext(), MainActivity.this);
        setContentView(gameMainView);

        gameSoundPool = new GameSoundPool(this);



    }
}
