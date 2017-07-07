package com.example.mylocation.bean;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/7 10:26
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 *
 */
@DatabaseTable(tableName = SearchRecordInfo.TABLE_NAME)
public class SearchRecordInfo implements Serializable {

    public static final String TABLE_NAME = "record_search_info";

    @DatabaseField(generatedId = true)  //主键，自动生成Id，必须为整型（int，long）
    private int id;
    @DatabaseField(columnName = "record")   //表字段名，默认为变量名称
    private String record;
    @DatabaseField(columnName = "insertTime")
    private long insertTime;

    public SearchRecordInfo() {
    }

    public SearchRecordInfo(String record, long insertTime) {

        this.record = record;
        this.insertTime = insertTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public long getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(long insertTime) {
        this.insertTime = insertTime;
    }

    @Override
    public String toString() {
        String str = "SearchRecordInfo{id="+id+",record='"+record+"',insertTime="+insertTime+"}";   //json?
        return str;
    }
}
