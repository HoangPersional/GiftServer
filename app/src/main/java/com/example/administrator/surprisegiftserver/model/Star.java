package com.example.administrator.surprisegiftserver.model;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.support.MyMath;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 30/5/2017.
 */

public class Star {
    MyPoint p1, p2, p3, p4, p5;
    int color;
    Paint paint;
    static ArrayList<Integer> colors;
    public Star() {
        init();
    }
    void init()
    {
        colors=new ArrayList<>();
        colors.add(R.color.back_ground_block);
        colors.add(R.color.colorAccent);
        colors.add(R.color.colorPrimary);
        colors.add(R.color.paste);
        colors.add(R.color.step_one);
        colors.add(R.color.step_two);
        colors.add(R.color.step_three);
        paint=new Paint();
        int k=colors.size();
        Random random=new Random();
        int c=random.nextInt(k);
        paint.setColor(Color.parseColor("#ff0088"));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
    }
    public void drawStar(Canvas canvas)
    {
        Path path = new Path();
        path.moveTo(p1.getX(),p1.getY());
        path.lineTo(p2.getX(),p2.getY());
        path.lineTo(p3.getX(),p3.getY());
        path.lineTo(p4.getX(),p4.getY());
        path.lineTo(p5.getX(),p5.getY());
        path.close();
        canvas.drawPath(path, paint);
    }


    public MyPoint getP1() {
        return p1;
    }

    public void setP1(MyPoint p1) {
        this.p1 = p1;
    }

    public MyPoint getP2() {
        return p2;
    }

    public void setP2(MyPoint p2) {
        this.p2 = p2;
    }

    public MyPoint getP3() {
        return p3;
    }

    public void setP3(MyPoint p3) {
        this.p3 = p3;
    }

    public MyPoint getP4() {
        return p4;
    }

    public void setP4(MyPoint p4) {
        this.p4 = p4;
    }

    public MyPoint getP5() {
        return p5;
    }

    public void setP5(MyPoint p5) {
        this.p5 = p5;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    public String toString()
    {
        return  p1.getX()+" "+p2.getX()+" "+p3.getX()+" "+p4.getX()+" "+p5.getX()+" color : "+paint.getColor();
    }
}
