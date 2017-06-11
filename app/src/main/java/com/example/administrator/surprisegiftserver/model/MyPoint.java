package com.example.administrator.surprisegiftserver.model;

import java.util.Random;

/**
 * Created by Administrator on 29/5/2017.
 */

public class MyPoint {
    float x, y, speed;

    public MyPoint() {
    }

    public MyPoint(float x, float y, float speed,int w,int h) {
        int a;

        Random random = new Random();
        a = random.nextInt(w) + 1;
        if (a >= w/3)
            this.x = x + a;
        else
            this.x = x - a;
        a = random.nextInt(200) + 1;
        if (a >= 100)
            this.y = y + a;
        else
            this.y = y - a;
        a=random.nextInt((int) speed)+5;
        this.speed = a;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String toString() {
        return x + "---" + y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}