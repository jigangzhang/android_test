package com.example.mylocation.activities;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/4 9:13
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.mylocation.MyView;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: MyApplication
 * @package: com.example.mylocation.activities
 * @version: V1.0
 * @author: 任强强
 * @date: 2017/7/4 9:13
 * @description: <p>
 * <p>
 * </p>
 */

public class DrawActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = new MyView(this);
        setContentView(view);

        long memory = Runtime.getRuntime().maxMemory()/1024;
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
        Log.i("MyTest","drawActivity--> destroy");
    }

}
