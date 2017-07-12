package com.example.mylocation.activities;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/6 9:39
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylocation.R;
import com.example.mylocation.bean.SearchRecordInfo;
import com.example.mylocation.dao.RecordDao;
import com.example.mylocation.dao.RecordInfoDao;

import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 */

public class RandomActivity extends AppCompatActivity {
    @BindView(R.id.btn_commit)
    Button mBtnCommit;
    @BindView(R.id.btn_getdata)
    Button mBtnGetdata;
    @BindView(R.id.text_show_data)
    TextView mTextShowData;
    @BindView(R.id.edit_radom)
    EditText mEditRadom;
    ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radom);
        ButterKnife.bind(this);
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            mTextShowData.append("\nrandom=" + (random.nextInt(100) + 1));      //生产随机数  [0~100)
        }
        getDate();
        dialog = new ProgressDialog(this, R.style.dialog_notitle);
        dialog.setCancelable(true);
        dialog.setIndeterminate(true);
        dialog.setMessage("loading...");
        dialog.show();

        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
    }

    private boolean saveDataIntoSharedPreferences(Context context, String key, Object value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("history", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String type = value.getClass().getSimpleName();
        mTextShowData.append(type);
        if (type.equals("Integer")) {
            editor.putInt(key, (int) value);
        }
        return editor.commit();
    }

    private int getDataFromSharedPreferences(Context context, String key, Object type1) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("history", MODE_PRIVATE);
        String type = type1.getClass().getSimpleName();
        if (type.equals("Integer")) {
            int value = sharedPreferences.getInt(key, -1);
            return value;
        }
        return -1;
    }

    private int produceRadom() {
        Random random = new Random();
        int i = random.nextInt(100);     //生产随机数  [0~100)
        Math.random();
        return i;
    }

    private void sqliteHelper(Context context, final String tableName) {
        String dbName = "test.db";
        SQLiteOpenHelper helper = new SQLiteOpenHelper(context, dbName, null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("create table if not exists " + tableName + "(Id integer primary key, Record text, OrderPrice integer, Country text)");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("drop table if exists " + tableName);
                onCreate(db);
            }
        };
        SQLiteDatabase database = helper.getWritableDatabase();
        database.beginTransaction();
        //数据库操作
        database.endTransaction();
        database.close();

    }

    private void getDate(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Calendar c = Calendar.getInstance();
            c.get(Calendar.DAY_OF_YEAR);
        }
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int i = calendar.get(java.util.Calendar.DAY_OF_YEAR);
        mTextShowData.setText("day="+i);

        Date date = new Date(System.currentTimeMillis());
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

    @OnClick({R.id.btn_commit, R.id.btn_getdata})
    public void onViewClicked(View view) {
        RecordInfoDao infoDao = new RecordInfoDao(this);
        String text;
        switch (view.getId()) {
            case R.id.btn_commit:
                int i = produceRadom();
                saveDataIntoSharedPreferences(this, "radom", i);
                String str = "\nradom=" + i;
                text = mEditRadom.getText().toString();
                SearchRecordInfo info = new SearchRecordInfo( text, System.currentTimeMillis());
            //    infoDao.add(info);
                RecordDao dao = new RecordDao(this);
                dao.add(info);
                mTextShowData.setText(info.toString());
                break;
            case R.id.btn_getdata:
                int m = getDataFromSharedPreferences(this, "radom", 1);
              //  mTextShowData.append("\nget: radom=" + m);
                text = mEditRadom.getText().toString();
                List<SearchRecordInfo> list = infoDao.queryByRecord(text);
                if(list.size() > 0) {
                    for (SearchRecordInfo info1 : list) {
                        mTextShowData.append(info1.toString());
                    }
                }else
                    mTextShowData.setText("list is null");
                break;
        }
    }

    @OnClick(R.id.edit_radom)
    public void onViewClicked() {
        Toast.makeText(this,"edit click..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
