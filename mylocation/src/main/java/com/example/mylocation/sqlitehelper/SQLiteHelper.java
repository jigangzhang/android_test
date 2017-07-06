package com.example.mylocation.sqlitehelper;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/6 14:25
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String dbName = "test.db";
    private String tableName = "history";

   public SQLiteHelper(Context context){
       super(context, dbName, null, 1);
   }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+tableName+"(Id integer primary key, Record text, Time integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
