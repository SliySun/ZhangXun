package com.example.sun.zhangxun.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.sun.zhangxun.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sun on 2017/3/26.
 *
 */

public class HomeToolbar extends Toolbar {

    private CircleImageView mCi;
    private TextView mTv;

    public HomeToolbar(Context context) {
        this(context,null);
    }

    public HomeToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomeToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_home_toolbar,this);
        initView(view);
    }

    private void initView(View view) {
        mCi = (CircleImageView) view.findViewById(R.id.circleImageView_toolbar_icon);
        mTv = (TextView) view.findViewById(R.id.textView_toolbar_search);

    }


    public void setOnIconClickListener(OnClickListener listener){
        mCi.setOnClickListener(listener);
    }

    public void setOnSearchClickListener(OnClickListener listener){
        mTv.setOnClickListener(listener);
    }
}
