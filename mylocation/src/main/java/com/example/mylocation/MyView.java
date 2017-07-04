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
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: MyApplication
 * @package: com.example.mylocation
 * @version: V1.0
 * @author: 任强强
 * @date: 2017/7/4 9:15
 * @description: <p>
 * <p>
 * </p>
 */

public class MyView extends View {
    private int memory;
    public MyView(Context context) {
        super(context);
        memory = (int) (Runtime.getRuntime().totalMemory()/1024)/1024;
    }

    private Paint getPaint(int color, float width, Paint.Style style){
        Paint paint = new Paint();
        paint.setAntiAlias(true);   //使用抗锯齿功能
        paint.setColor(color);
        paint.setStrokeWidth(width);    //设置笔芯宽度
        paint.setStyle(style);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        String str = "total memory:"+memory;

        Paint paint = getPaint(Color.rgb(2,5,7), 2, Paint.Style.FILL_AND_STROKE);
        paint.setTextSize(50);
        canvas.drawText("total memory:\n"+memory+" MB", 0, 500, paint);

        canvas.drawCircle(100,100,25, getPaint(Color.rgb(6,8,4),5, Paint.Style.STROKE));

        RectF rectF = new RectF(200, 200, 800, 400);
        canvas.drawOval(rectF, getPaint(Color.RED, 5, Paint.Style.STROKE));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.katon);
    //    ScaleDrawable scale = new ScaleDrawable(drawable, Gravity.CENTER, 0.5f, 0.5f);
        bitmap = Bitmap.createScaledBitmap(bitmap, 100, 200, false);    //缩放bitmap

        canvas.drawBitmap(bitmap, 200, 100, getPaint(Color.BLACK, 1, Paint.Style.STROKE));

        Drawable drawable = getResources().getDrawable(R.drawable.katon);
        drawable.draw(canvas);
    }
}
