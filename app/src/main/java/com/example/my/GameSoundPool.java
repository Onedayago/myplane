package com.example.my;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.example.my.Constant.GameSound;

import java.util.HashMap;

public class GameSoundPool {

    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    private static MainActivity mainActivity;

    public GameSoundPool(MainActivity mainActivity){
        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(2);
        AudioAttributes.Builder audioAttributes = new AudioAttributes.Builder();
        audioAttributes.setLegacyStreamType(AudioManager.STREAM_MUSIC);
        builder.setAudioAttributes(audioAttributes.build());
        soundPool = builder.build();
        this.mainActivity = mainActivity;

        this.initGameSound();
    }

    public void initGameSound(){
        try {
            map.put(GameSound.SHOOT, soundPool.load(mainActivity, R.raw.shoot, 1));
            map.put(GameSound.EXPLOSION, soundPool.load(mainActivity, R.raw.explosion, 1));
        }catch (Exception e){

        }

    }

    public static void play(int sound, int loop){
        AudioManager am = (AudioManager) mainActivity.getSystemService(Context.AUDIO_SERVICE);
        float stramVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float stramMaxVolumeCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volume = stramVolumeCurrent / stramMaxVolumeCurrent;
        soundPool.play(map.get(sound), volume, volume, 1, loop, 1.0f);
    }

}
