package com.example.mylocation.dao;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/7 16:06
 */

import android.content.Context;

import com.example.mylocation.sqlitehelper.OrmLiteHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 */

public abstract class BaseDao<T,N> {
    OrmLiteHelper mHelper = null;
    public BaseDao(Context context) {
        mHelper = OrmLiteHelper.getInstance(context);
    }

    public abstract Dao<T, N> getDao();

    public OrmLiteHelper getHelper(){
        return mHelper;
    }

    public int add(T t){
        try {
            return getDao().create(t);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<T> getByColumn(String columnName, Object value){        //select * from tableName where columnName = value;
        try {
            return getDao().queryBuilder().where().eq(columnName, value).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
