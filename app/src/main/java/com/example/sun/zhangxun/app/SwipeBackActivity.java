package com.example.sun.zhangxun.app;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.widget.SwipeBackLayout;

/**
 * Created by sun on 2017/5/29.
 */

public class SwipeBackActivity extends BaseActivity implements SwipeBackLayout.SwipeListener{


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




    @Override
    public void swipeValue(double value) {
        int statusColor = (int) argbEvaluator.evaluate((float) value, currentStatusColor,
                ContextCompat.getColor(this, R.color.transparent_00ffffff));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setStatusBarColor(statusColor);
        }
    }
}
