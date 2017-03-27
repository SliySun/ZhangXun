package com.example.sun.zhangxun.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.example.sun.zhangxun.R;

/**
 * Created by sun on 2017/3/26.
 *
 */

public class HomeToolbar extends Toolbar {

    public HomeToolbar(Context context) {
        this(context,null);
    }

    public HomeToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomeToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_home_toolbar,this);
    }


}
