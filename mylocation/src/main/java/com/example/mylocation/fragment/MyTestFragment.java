package com.example.mylocation.fragment;
/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/6/19 11:35
 */

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mylocation.R;

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: MyApplication
 * @package: com.example.mylocation.fragment
 * @version: V1.0
 * @author: 任强强
 * @date: 2017/6/19 11:35
 * @description: <p>
 * <p>
 * </p>
 */

public class MyTestFragment extends Fragment {

    private String str;
    private int imgSrc;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        imgSrc = bundle.getInt("img", R.drawable.katon);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_views, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.img_fragment);
        img.setImageResource(imgSrc);
        str = view.toString();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    //回调接口，用于fragment传数据到Activity
    public interface CallBack{
        public abstract void getResult(String string);
    }
    //接口回调，传数据
    public void getData(CallBack callBack){
        callBack.getResult(str);
    }
}
