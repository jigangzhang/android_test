package com.example.mylocation.activities;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/7/11 15:15
 */

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylocation.R;
import com.example.mylocation.net.NativeNetworkUtils;
import com.example.mylocation.net.NetUtils;
import com.example.mylocation.net.OkHttpUtils;
import com.example.mylocation.presenter.HttpRequestPresenter;

import org.xutils.x;

import java.io.IOException;
import java.nio.charset.Charset;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class NfcConfigActivity extends AppCompatActivity implements HttpRequestPresenter {
    @BindView(R.id.text_nfc_config)
    TextView mTextNfcConfig;
    @BindView(R.id.text_nfc_detail)
    TextView mTextNfcDetail;

    NfcAdapter mNfcAdapter;
    PendingIntent pendingIntent;
    @BindView(R.id.text_tech_list)
    TextView mTextTechList;
    @BindView(R.id.img_nfc_get)
    ImageView mImgNfcGet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        ButterKnife.bind(this);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        initNFC();
        initText();

        String url = "https://gss0.bdstatic.com/5bd1bjqh_Q23odCf/static/wiseindex/img/screen_icon.png";
        new OkHttpUtils().get(url);
        x.image().bind(mImgNfcGet, url);
        NetUtils.httpGet(url, this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }
    private void initText(){
        String str = "net type:"+ NativeNetworkUtils.getNetworkType(getApplicationContext())
                +"\nnet connect:"+NativeNetworkUtils.isNetConnected(getApplicationContext())
                +"\nwifi connect:"+NativeNetworkUtils.isWifiConnected(getApplicationContext())
                +"\nmobile connect:"+NativeNetworkUtils.isMobileConnected(getApplicationContext())
                +"\ngps open:"+NativeNetworkUtils.isGpsEnabled(getApplicationContext());
        mTextNfcDetail.setText(str);

    }

    private void initNFC() {
        if (mNfcAdapter == null) {
            mTextNfcConfig.setText("device is no support NFC!");
            return;
        }
        if (!mNfcAdapter.isEnabled()) {
            mTextNfcConfig.setText("please open NFC!");
        } else
            mTextNfcConfig.setText("NFC is enabled!");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);//当次Activity处于前台时，直接显示nfc标签信息
        }
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction()) || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            Intent intent = getIntent();
            Toast.makeText(this, "NFC received intent", Toast.LENGTH_SHORT).show();
            //   dealNfcIntent(intent);
            dealWithNfcA(intent);
        }
    }

    private void dealWithNfcA(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] id1 = tag.getId();
        String id = new String(id1);
        mTextNfcConfig.setText("Tag Id:" + id);
        for (String detail : tag.getTechList()) {
            mTextTechList.append("\n" + detail);
        }
        NfcA nfcA = NfcA.get(tag);
        try {
            nfcA.connect();
            byte[] bytes = nfcA.getAtqa();
            short b = nfcA.getSak();
            String detail = new String(bytes);
            mTextNfcDetail.append("\nNfcA:" + detail + "\nsak:" + b);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                nfcA.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dealNfcIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] bytes = tag.getId();
        String id = new String(bytes);
        mTextNfcConfig.setText("Tag Id:" + id);
        for (String detail : tag.getTechList()) {
            mTextTechList.append("\n" + detail);
        }
        readTag(tag);
    }

    private void readTag(Tag tag) {
        MifareClassic mf = MifareClassic.get(tag);
        try {
            mf.connect();
            if (mf.isConnected()) {
                int blockCount = mf.getBlockCount();
                int sectorCount = mf.getSectorCount();
                int type = mf.getType();
                int size = mf.getSize();
                String detail = "size=" + size + " B\nblockCount=" + blockCount + "\nsectorCount=" + sectorCount + "\ntype=" + type;
                mTextNfcDetail.setText(detail);
                boolean auth = false;
                for (int i = 0; i < sectorCount; ++i) {
                    Log.i("MyTest", "sector-->" + i);
                    auth = mf.authenticateSectorWithKeyA(i, MifareClassic.KEY_DEFAULT);
                    if (auth) {
                        mTextNfcDetail.append("\n   auth succeed in sector " + i);
                        int block = mf.getBlockCountInSector(i);

                        for (int j = 0; j < block; ++j) {
                            int bIndex = mf.sectorToBlock(i) + j;
                            readBlockInTag(mf, j, bIndex);
                        }
                    } else
                        mTextNfcDetail.append("\n   auth failed in sector " + i);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                mf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readBlockInTag(MifareClassic mf, int index, int bIndex) throws IOException {
        byte[] bytes = mf.readBlock(index);
        if (bytes != null && bytes.length != 0) {
            String message = new String(bytes, Charset.forName("US-ASCII"));
            mTextNfcDetail.append("\n   block " + bIndex + ":" + message);
        } else
            mTextNfcDetail.append("\n block " + bIndex + ": null");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mTextNfcConfig.setText("onNewIntent-->ON");
        dealNfcIntent(intent);
        //dealWithNfcA(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestSuccess(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        mImgNfcGet.setImageBitmap(bitmap);
        Log.i("MyTest","bit-->called");
    }
}
