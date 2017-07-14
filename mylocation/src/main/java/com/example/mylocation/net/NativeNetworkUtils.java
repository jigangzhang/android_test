package com.example.mylocation.net;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/13 11:41
 */

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 */

public class NativeNetworkUtils {
    Context mContext;

    public static int getNetworkType(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if(info != null && info.isAvailable()){
            return info.getType();
        }
        return -1;
    }

    public static boolean isNetConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null){
            return info.isAvailable(); // info.getState() == NetworkInfo.State.CONNECTED;
        }
        return false;
    }

    public static boolean isWifiConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null && info.getType() == ConnectivityManager.TYPE_WIFI){
            return info.isAvailable();  // info.isConnected();
        }
        return false;
    }

    public static boolean isMobileConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null && info.getType() == ConnectivityManager.TYPE_MOBILE){
            return info.isAvailable();
        }
        return false;
    }
    /**
     * 判断GPS是否开启
     * */
    public static boolean isGpsEnabled(Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
