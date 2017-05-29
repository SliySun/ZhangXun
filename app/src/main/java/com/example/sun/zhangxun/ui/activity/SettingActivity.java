package com.example.sun.zhangxun.ui.activity;

import android.os.Bundle;

import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.app.SwipeBackActivity;


public class SettingActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.pull_left_out);


    }
}
