package com.example.mylocation.dao;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/7 10:58
 */

import android.content.Context;

import com.example.mylocation.bean.SearchRecordInfo;
import com.example.mylocation.sqlitehelper.OrmLiteHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 */

public class RecordInfoDao {

    private OrmLiteHelper ormHelper = null;
    private Dao<SearchRecordInfo, Integer> mDao;

    public RecordInfoDao(Context context) {
        ormHelper = OrmLiteHelper.getInstance(context);
        try {
            mDao = ormHelper.getDao(SearchRecordInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int add(SearchRecordInfo info){
        try {
            return mDao.create(info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int remove(SearchRecordInfo info){
        try {
            return mDao.delete(info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int removeById(Integer id){
        try {
            return mDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int update(SearchRecordInfo info){
        try {
            return mDao.update(info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<SearchRecordInfo> queryForAll(){
        try {
            return mDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SearchRecordInfo> queryByTimeOrder(){
        try {
            return mDao.queryBuilder().orderBy("insertTime", false).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SearchRecordInfo> queryByRecord(String value){
        try {
            return mDao.queryBuilder().where().eq("record", value).query(); //查询
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
