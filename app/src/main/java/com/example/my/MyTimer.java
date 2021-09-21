package com.example.my;

public class MyTimer {

    private static long time = 0;

    public static long getTime(){
        return MyTimer.time;
    }

    public static void addTime(int num){
        MyTimer.time+=num;
    }

}
