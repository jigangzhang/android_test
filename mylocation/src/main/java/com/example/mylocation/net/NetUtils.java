package com.example.mylocation.net;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/13 10:05
 */

import android.util.Log;

import com.example.mylocation.presenter.HttpRequestPresenter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 */

public class NetUtils {
    public NetUtils() {
        try {
            throw new Exception("network is not avaliable");
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void httpGet(String URL, final HttpRequestPresenter presenter){
        String uri = URL;
        final RequestParams params = new RequestParams(uri);
        params.setConnectTimeout(3000);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                presenter.onRequestSuccess(result.getBytes());
                Log.i("MyTest","result:"+result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("MyTest","ex:"+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
