package com.example.mylocation;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/4 9:15
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 */

public class MyView extends View {

    private Path mPath;
    private int memory;
    private Bitmap bitmap;

    public MyView(Context context) {
        super(context);
        memory = (int) (Runtime.getRuntime().totalMemory() / 1024) / 1024;  //获取内存
        mPath = new Path();
    }

    private Paint getPaint(int color, float width, Paint.Style style) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);   //使用抗锯齿功能
        paint.setColor(color);
        paint.setStrokeWidth(width);    //设置笔芯宽度
        paint.setStyle(style);
        return paint;
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void layout(@Px int l, @Px int t, @Px int r, @Px int b) {
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String str = "total memory:" + memory;
        int i = canvas.getMaximumBitmapWidth();
        int n = canvas.getWidth();
        Log.i("MyTest", "i,n:" + i + "," + n);

        Paint paint = getPaint(Color.rgb(2, 5, 7), 2, Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(50);
        canvas.drawText("total memory:\n" + memory + " MB", 0, 500, paint);

        canvas.drawCircle(100, 100, 25, getPaint(Color.rgb(6, 8, 4), 5, Paint.Style.STROKE));

        RectF rectF = new RectF(200, 200, 800, 400);
        canvas.drawOval(rectF, getPaint(Color.RED, 5, Paint.Style.STROKE));

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.katon);
        //    ScaleDrawable scale = new ScaleDrawable(drawable, Gravity.CENTER, 0.5f, 0.5f);
        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 200, false);    //缩放bitmap


        canvas.drawBitmap(bitmap, 200, 100, getPaint(Color.BLACK, 1, Paint.Style.STROKE));

        Drawable drawable = getResources().getDrawable(R.drawable.katon);
        drawable.draw(canvas);

        Matrix matrix = new Matrix();   //矩阵 对图形三维变换
        matrix.setTranslate(500, 500);
        //   matrix.setRotate(45, 500, 500);
        //   matrix.setScale(2,2);
        //    matrix.setSkew(5,5);
        //  canvas.drawBitmap(bitmap,matrix,paint);

        matrix.preTranslate(100, 100);
        matrix.preRotate(20, 100, 100);
        canvas.drawBitmap(bitmap, matrix, paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(Color.YELLOW);
        mPath.moveTo(0, getHeight());
        mPath.quadTo(getWidth() / 2, getHeight() - 200, getWidth(), getHeight());
        mPath.close();
        canvas.drawPath(mPath, paint);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            Log.i("MyTest", "bitmap --> recycle");
        }
    }
}
