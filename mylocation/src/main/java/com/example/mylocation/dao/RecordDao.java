package com.example.mylocation.dao;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/7 16:25
 */

import android.content.Context;

import com.example.mylocation.bean.SearchRecordInfo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 */

public class RecordDao extends BaseDao<SearchRecordInfo, Integer> {

    public RecordDao(Context context) {
        super(context);
    }

    @Override
    public Dao getDao() {
        try {
            return getHelper().getDao(SearchRecordInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
