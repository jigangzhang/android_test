package com.example.mylocation.activities;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mylocation.R;
import com.example.mylocation.fragment.MyTestFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.Glide.with;

public class MainActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.btn_test)
    Button mBtnTest;
    @BindView(R.id.edit_test)
    TextView mTextTest;
    @BindView(R.id.text_tab1)
    TextView mTextTab1;
    @BindView(R.id.text_tab2)
    TextView mTextTab2;
    @BindView(R.id.text_tab3)
    TextView mTextTab3;

    PopupWindow popWindow;
    @BindView(R.id.my_container)
    RelativeLayout mMyContainer;
    private ImageView fragment_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        InsetDrawable id = new InsetDrawable(getResources().getDrawable(R.drawable.katon), 9,0,9,0);
        mMyContainer.setBackground(id);
        mBtnTest.setBackground(id);
        queryAppInfo();
        Bundle bundle = new Bundle();
        bundle.putInt("img", R.drawable.katon);

        MyTestFragment fg = new MyTestFragment();
        fg.setArguments(bundle);
        fg.getData(new MyTestFragment.CallBack() {
            @Override
            public void getResult(String string) {
                System.out.println("receive:" + string);
            }
        });
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        // transaction.add(R.id.frag_test , fg);
        transaction.replace(R.id.container_fragment, fg);    //动态加载时，布局文件中不应该有fragment控件，只用framelayout作容器
        transaction.commit();

        Animator animator = new AnimatorInflater().loadAnimator(this, R.animator.animator_test);    //属性动画 xml文件加载
        animator.setTarget(mBtnTest);
        animator.start();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        fragment_img = (ImageView) getFragmentView();
        Glide.with(this).load("http://img.zcool.cn/community/014415578e4e920000018c1b9f183b.gif").thumbnail(1).into(fragment_img);    //glide框架
    }

    private View getFragmentView(){
        ImageView img = (ImageView) getFragmentManager() //在Activity中获取fragment中的控件
                .findFragmentById(R.id.container_fragment).getView().findViewById(R.id.img_fragment);
        return img;
    }

    private void initPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popup, null);
        popWindow = new PopupWindow(view);
        popWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        TextView text1 = (TextView) view.findViewById(R.id.text1);
        TextView text2 = (TextView) view.findViewById(R.id.text2);
        TextView text3 = (TextView) view.findViewById(R.id.text3);
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);

        popWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.katon));
        popWindow.setOutsideTouchable(true);    //popwindow以外，点击popwindow消失
        popWindow.setAnimationStyle(R.style.menuAnim);
        popWindow.showAsDropDown(mBtnTest);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataReceiverEvent(char i) {
        System.out.println("eventbus reveived -->" + i + " in " + Thread.currentThread());
        mTextTest.setText(i);
    }

    Runnable mThread = new Runnable() {
        @Override
        public void run() {

            //   Double i = 2.34;
            Integer i = 12;
            char a = 'a';
            boolean t = true;
            //  int i = 18;
            String str = "eventbus received";
            EventBus.getDefault().post(a);    //只传送包装数据类型，如：String,Boolean,Integer,自定义类等；不能传送基本数据类型，eg:int,boolean,char etc.
            System.out.println("eventbus posted -->" + Thread.currentThread());
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MyTest", "mainActivity --> destroy");
        EventBus.getDefault().unregister(this);

    }


    @OnClick(R.id.btn_test)
    public void onViewClicked() {
        new Thread(mThread).start();
        Animator animator = new AnimatorInflater().loadAnimator(this, R.animator.animator_test);
        animator.setTarget(mTextTab3);
        animator.start();
        with(this).load("http://imga.deyi.com/forum/201605/13/144155gnfekdk0grn0kb5s.gif").into(fragment_img);
    }

    public void queryAppInfo() {
        PackageManager pm = this.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(this.getPackageName(), 0);
            System.out.println(pi.versionName + '\n' + pi.versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.text_tab1, R.id.text_tab2, R.id.text_tab3})
    public void onTextViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_tab1:
                Toast.makeText(this, "click...", Toast.LENGTH_SHORT).show();
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mTextTab2, "alpha", 0, 1);    //属性动画代码实现
                AnimatorSet animator = new AnimatorSet();
                animator.play(objectAnimator);
                animator.setDuration(2000);
           //     animator.start();
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                startActivity(intent);
                break;
            case R.id.text_tab2:
                if (popWindow != null && popWindow.isShowing()) {
                    popWindow.dismiss();
                } else
                    initPopupWindow();
                break;
            case R.id.text_tab3:
                Animator animatorSet = new AnimatorInflater().loadAnimator(this, R.animator.animator_test);
                animatorSet.setTarget(fragment_img);
           //     animatorSet.start();
                Intent i = new Intent(MainActivity.this, WindowTestActivity.class);
                startActivity(i);
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text1:
                Toast.makeText(this, "click...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                startActivity(intent);
                break;
            case R.id.text2:
                Toast.makeText(this, "click...", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(MainActivity.this, RandomActivity.class);
                startActivity(intent1);
                break;
            case R.id.text3:
                Toast.makeText(this, "click...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (popWindow != null && popWindow.isShowing()) {
                popWindow.dismiss();
            }else
                finish();
            return false;   //true or false 是否拦截请求
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (popWindow != null && popWindow.isShowing()) {
            popWindow.dismiss();
        }
    }
}
