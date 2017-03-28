package com.example.sun.zhangxun.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.app.BaseActivity;
import com.example.sun.zhangxun.ui.activity.HomeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startMain();
            }
        },3000);
    }

    @Override
    protected void hideStatusBar() {
        //将SplashActivity页面设置全屏，没有状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    private void startMain(){
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }
}
