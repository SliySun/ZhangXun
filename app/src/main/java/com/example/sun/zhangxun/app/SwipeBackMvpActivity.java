package com.example.sun.zhangxun.app;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.widget.SwipeBackLayout;

/**
 * Created by sun on 2017/5/29.
 */

public class SwipeBackMvpActivity extends BaseMvpActivity {

    protected SwipeBackLayout layout;

    private ArgbEvaluator argbEvaluator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.base, null);
        layout.attachToActivity(this);
        argbEvaluator = new ArgbEvaluator();


    }
}
