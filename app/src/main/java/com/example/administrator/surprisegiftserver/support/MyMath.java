package com.example.administrator.surprisegiftserver.support;

import android.util.Log;

import com.example.administrator.surprisegiftserver.model.Circle;
import com.example.administrator.surprisegiftserver.model.MyPoint;

/**
 * Created by Administrator on 29/5/2017.
 */

public class MyMath {
    public static float[] ptb2(float a, float b, float c) {
        float[] n = new float[2];
        float delta = b * b - 4 * a * c;
        if (delta > 0) {
            float x1 = (float) (-b-Math.sqrt(delta))/(2*a);
            float x2 = (float) (-b+Math.sqrt(delta))/(2*a);
            n[0] = x1;
            n[1] = x2;
        } else if (delta == 0) {
            float x1 = (float) (-b) / (2 * a);
        }
        return n;
    }

    public static MyPoint[] GiaoDiem2DuongTron(Circle a, Circle b)
    {
        MyPoint[] points =new MyPoint[2];
        float c1=(float) (b.getx()*b.getx()+b.gety()*b.gety()-b.getr()*b.getr());
        float c=(float)(a.getx()*a.getx()+a.gety()*a.gety()-a.getr()*a.getr());
        float y=(float) (c1-c)/(float) (2*(b.gety()-a.gety()));
        float[] x=ptb2(1,(float)(-2*a.getx()),(float)(y*y-2*y*a.gety()+c));
        points[0]=new MyPoint();
        points[0].setX(x[0]);
        points[0].setY(y);

        points[1]=new MyPoint();
        points[1].setX(x[1]);
        points[1].setY(y);
        return points;
    }
}
