package com.example.administrator.surprisegiftserver.support;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.surprisegiftserver.R;
import com.example.administrator.surprisegiftserver.model.Circle;
import com.example.administrator.surprisegiftserver.model.MyPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 28/5/2017.
 */

public class CustomView extends View implements View.OnTouchListener{
    private int mColor;
    private Paint mPaint,paint,paint1;
    private Drawable mDrawable;
    private Bitmap mBitmap,bitmap;
    private int w, h;
    private int bitmapW, bitmapH;
    int vertical, hozital;
    int step = 1;
    float x, y;
    List<MyPoint> list;
    List<Point> list1;
    public CustomView(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    protected void init(AttributeSet attributeSet) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.MyImageView);
        mColor = typedArray.getInteger(R.styleable.MyImageView_myBackground, Color.RED);
        mDrawable = typedArray.getDrawable(R.styleable.MyImageView_mySrc);
        bitmap = ((BitmapDrawable) mDrawable).getBitmap();
        bitmapW = bitmap.getWidth();
        bitmapH = bitmap.getHeight();
        typedArray.recycle();
        setPaint();
        setOnTouchListener(this);
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#ff0099"));
        paint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        if (wMode == MeasureSpec.EXACTLY) {
            w = MeasureSpec.getSize(widthMeasureSpec);
//            Log.v("HH",""+w);
        } else if (wMode == MeasureSpec.AT_MOST || wMode == MeasureSpec.UNSPECIFIED) {
            w = bitmapW + bitmapW / 2;
            w = Math.min(w, MeasureSpec.getSize(widthMeasureSpec));
//            Log.v("HH1","width : "+w+"           mesua: "+MeasureSpec.getSize(widthMeasureSpec)+"      bitmpW:  "+bitmapW);
        }
        if (hMode == MeasureSpec.EXACTLY || wMode == MeasureSpec.AT_MOST)
            h = MeasureSpec.getSize(heightMeasureSpec);
        else if (wMode == MeasureSpec.UNSPECIFIED) {
            h = bitmapH + bitmapH / 2;
            h = Math.min(h, MeasureSpec.getSize(heightMeasureSpec));
        }
        vertical = w / 2;
        hozital = h / 2;
        reSizeBitmap();
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTriangle(canvas);
        canvas.drawBitmap(mBitmap, (w-mBitmap.getWidth())/2, (h-mBitmap.getHeight())/2, mPaint);
        drawList(canvas);
    }

    private void drawTriangle(Canvas canvas) {
        Path path=new Path();
        path.lineTo(0,0);
        path.lineTo(w,0);
        path.lineTo(0,h);
        path.close();
        canvas.drawPath(path,paint);
    }

    protected void setPaint() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(1);
        mPaint.setColor(getResources().getColor(R.color.paste));
        paint=new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(mColor);
        paint1=new Paint();
        paint1.setStrokeWidth(1);
        paint1.setColor(Color.parseColor("#8822ff"));
        paint1.setStyle(Paint.Style.FILL);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int sp=h/100;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            list.add(new MyPoint(x, y, sp,w,h));
            list.add(new MyPoint(x, y, sp,w,h));
            list.add(new MyPoint(x, y, sp,w,h));
            list.add(new MyPoint(x, y, sp,w,h));
            list.add(new MyPoint(x, y, sp,w,h));
            list.add(new MyPoint(x, y, sp,w,h));
            list.add(new MyPoint(x, y, sp,w,h));
            list.add(new MyPoint(x, y, sp,w,h));
            list.add(new MyPoint(x, y, sp,w,h));
            list.add(new MyPoint(x, y, sp,w,h));
        }
        Point p = new Point();
        p.set(x, y);
        list1.add(p);
        return true;
    }
    public void drawStar(final float x, float y, float r, final int color, final Canvas canvas) {
        Circle a = new Circle(vertical, hozital, step);
        a.setR(r);
        a.setX(x);
        a.setY(y);
        Circle b = new Circle(a, true);
        Circle c = new Circle(a, false);
        MyPoint[] p1 = MyMath.GiaoDiem2DuongTron(a, b);
        MyPoint[] p2 = MyMath.GiaoDiem2DuongTron(a, c);
        Path path = new Path();
        path.moveTo((float) b.getX(), (float) b.getY());
        path.lineTo(vertical + (float) p2[1].getX() * step, hozital + (float) p2[1].getY() * step);
        path.lineTo(vertical + (float) p1[0].getX() * step, hozital + (float) p1[0].getY() * step);
        path.lineTo(vertical + (float) p1[1].getX() * step, hozital + (float) p1[1].getY() * step);
        path.lineTo(vertical + (float) p2[0].getX() * step, hozital + (float) p2[0].getY() * step);
        path.close();
        canvas.drawPath(path, paint1);

    }
    private void drawList1(Canvas canvas) {

        for (int i = 0; i < list1.size(); ++i) {
            Point p = list1.get(i);
            canvas.drawCircle(p.x, p.y, 10, paint);
        }
        changeList1();
        invalidate();
    }
    private void changeList1() {
        for (int i = 0; i < list1.size(); ++i) {
            {
                Point m = list1.get(i);
                if (m.y + 30 >= h)
                    list1.remove(i);
                else

                    m.set(m.x, m.y + 30);
            }
        }
    }
    private void reSizeBitmap() {
        int newWidth = 2 * w / 3;
//        Log.v("HH", " w: " + w + "    bw: " + bitmapW);
//        Log.v("HH", "bitmap width > normal is " + (newWidth < bitmapW ? true : false));
        if (newWidth < bitmapW) {
            int newHeight = newWidth * bitmapH / bitmapW;
//            Log.v("HH",newHeight+"_____"+newHeight);
            Bitmap resize = Bitmap.createScaledBitmap(bitmap,newWidth,newHeight,false);
            mBitmap=resize;
        }
        else
            mBitmap=bitmap;
    }
    private void drawList(Canvas canvas) {
        for (int i = 0; i < list.size(); ++i) {
            drawStar((float) list.get(i).getX() - vertical, (float) list.get(i).getY() - hozital, w/10, Color.parseColor("#ff0099"), canvas);
        }
        changeList();
        invalidate();
    }
    private void changeList() {
        for (int i = 0; i < list.size(); ++i) {
            {
                MyPoint m = list.get(i);
                if (m.getY() + m.getSpeed() > h)
                    list.remove(i);
                else
                    m.setY(m.getY() + m.getSpeed());
            }
        }
    }
}
