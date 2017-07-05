package com.example.mylocation.activities;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/5 10:37
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.mylocation.MyView;



public class WindowTestActivity extends AppCompatActivity {
    WindowManager wm;
    int width;
    int height;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);  //将当前窗口的一些信息放在DisplayMetrics类中
        float density = dm.density;

        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

      //  width = dm.widthPixels;   //两种方式取得的像素大小一样
      //  height = dm.heightPixels;
        Log.i("MyTest", "width height:"+width+","+height+",density:"+density);
        setContentView(new MyView(this));
        init();
    }
    private void init(){
        WindowManager.LayoutParams wl = new WindowManager.LayoutParams();
        wl.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        wl.gravity = Gravity.CENTER;
        wl.width = width/2;
        wl.height = height/2;
        wm.addView(new MyView(WindowTestActivity.this), wl);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
