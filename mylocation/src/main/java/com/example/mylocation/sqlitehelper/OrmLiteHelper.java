package com.example.mylocation.sqlitehelper;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/7 11:00
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mylocation.bean.SearchRecordInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 */

public class OrmLiteHelper extends OrmLiteSqliteOpenHelper {

    public static final String dbName = "db_record";
    public static int version = 1;

    private static OrmLiteHelper instance;

    private OrmLiteHelper(Context context) {        //构造方法私有化
        super(context, dbName, null, version);
    }

    public static OrmLiteHelper getInstance(Context context){       //单例模式
        if(instance == null){
            synchronized (OrmLiteHelper.class) {    //对象锁，同一时刻只能有一个线程访问同步代码块
                if (instance == null) {
                    instance = new OrmLiteHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, SearchRecordInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, SearchRecordInfo.class, true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
