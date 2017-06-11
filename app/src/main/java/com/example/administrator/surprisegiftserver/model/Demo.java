package com.example.administrator.surprisegiftserver.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.support.MyMath;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 29/5/2017.
 */

public class Demo extends View implements View.OnTouchListener {

    int vertical, hozital;
    int step = 1;
    int w, h;
    float x, y;
    List<MyPoint> list;
    List<Point> list1;
    Paint paint;
    Handler handler;

    public Demo(Context context) {
        super(context);
        init();
    }

    public Demo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Demo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Demo(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        w = MeasureSpec.getSize(widthMeasureSpec);
        h = MeasureSpec.getSize(heightMeasureSpec);
        vertical = w / 2;
        hozital = h / 2;
        setMeasuredDimension(w, h);
        handler.removeCallbacks(runnable);
        handler.post(runnable);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            kt(vertical, hozital);
        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawList(canvas);

    }

    private void changeList() {
        for (int i = 0; i < list.size(); ++i) {
            {
                MyPoint m = list.get(i);
                if (m.getY() + m.getSpeed() > h) {
                    {
                        Random random = new Random();
                        int x = random.nextInt(w - 50) + 50;
                        int y = random.nextInt(100) + 10;
                        m.setX(x);
                        m.setY(y);
                    }
                } else
                    m.setY(m.getY() + m.getSpeed());
            }
        }
    }

    private void drawList(Canvas canvas) {
        for (int i = 0; i < list.size(); ++i) {
            drawStar((float) list.get(i).getX() - vertical, (float) list.get(i).getY() - hozital, w / 30,
                    getResources().getColor(R.color.colorAccent), canvas);
        }
        changeList();
        invalidate();
    }


    public void drawStar(final float x, final float y, final float r, final int color, final Canvas canvas) {

                Circle a = new Circle(vertical, hozital, step);
                a.setR(r);
                a.setX(x);
                a.setY(y);
                Circle b = new Circle(a, true);
                Circle c = new Circle(a, false);
                Paint p = new Paint();
                p.setStyle(Paint.Style.FILL);
                p.setColor(color);
                p.setStrokeWidth(1);
                MyPoint[] p1 = MyMath.GiaoDiem2DuongTron(a, b);
                MyPoint[] p2 = MyMath.GiaoDiem2DuongTron(a, c);
                Path path = new Path();
                p.setStyle(Paint.Style.FILL);
                path.moveTo((float) b.getX(), (float) b.getY());
                path.lineTo(vertical + (float) p2[1].getX() * step, hozital + (float) p2[1].getY() * step);
                path.lineTo(vertical + (float) p1[0].getX() * step, hozital + (float) p1[0].getY() * step);
                path.lineTo(vertical + (float) p1[1].getX() * step, hozital + (float) p1[1].getY() * step);
                path.lineTo(vertical + (float) p2[0].getX() * step, hozital + (float) p2[0].getY() * step);
                path.close();
                canvas.drawPath(path, p);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Point p = new Point();
        p.set(x, y);
        list1.add(p);
        return true;
    }

    public void kt(int x, int y) {
        if (list.isEmpty()) {
            int k = w / 100;
            list.add(new MyPoint(x, y, k, w, h));
            list.add(new MyPoint(x, y, k, w, h));
            list.add(new MyPoint(x, y, k, w, h));
            list.add(new MyPoint(x, y, k, w, h));
            invalidate();
        }
    }

    protected void init() {
        setOnTouchListener(this);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#ff0099"));
        paint.setStyle(Paint.Style.FILL);
        handler = new Handler();

    }

}
