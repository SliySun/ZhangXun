package com.example.sun.zhangxun.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sun on 2017/3/28.
 */

public abstract class BaseFragment extends Fragment{

    private BaseActivity mActivity;

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity= (BaseActivity)context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("onCreateView");
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(getLayoutId(),container,false);
        initView(view,savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        System.out.println("onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            isUIVisible = true;
            lazyLoad();

        }else {
            isUIVisible = false;

            //不可见，设置为false 第二次可见的时候不执行加载数据的方法
            isViewCreated=false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible){
            System.out.println("loadData");
            loadData();
            //数据加载完毕,恢复标记,防止重复加载
            isUIVisible = false;
            isViewCreated = false;
        }
    }

    protected abstract void loadData();

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract int getLayoutId();


}
