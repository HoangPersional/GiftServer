package com.example.administrator.surprisegiftserver.model;

import java.util.Random;

/**
 * Created by Administrator on 29/5/2017.
 */

public class Circle {
    float a, b, step, x, y, r;

    public Circle(float x, float y, float step) {
        a = x;
        b = y;
        this.step = step;
    }

    public Circle(Circle circle, boolean d) {
        a = circle.getA();
        b = circle.getB();
        step = circle.getStep();
        setX(circle.getx());
        if (d == true) {
            setY(circle.gety() - circle.getr());
            setR((float) (circle.getr() * Math.sqrt(2 * (1 - Math.cos(Math.toRadians(72))))));
        } else {
            setY(circle.gety() + circle.getr());
            setR((float) (circle.getr() * Math.sqrt(2 * (1 - Math.cos(Math.toRadians(36))))));
        }

    }

    public double getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x * step + a;
    }

    public double getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y * step + b;
    }

    public double getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r *step;
    }

    public float getx() {
        return (float) (getX() - a) / step;
    }

    public float gety() {
        return (float) (getY() - b) / step;
    }

    public float getr() {
        return (float) r / step;
    }

    public String toString() {
        return getx() + " " + gety() + " " + getr();
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }
}

