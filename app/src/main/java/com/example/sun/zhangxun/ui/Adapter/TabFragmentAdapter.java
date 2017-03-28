package com.example.sun.zhangxun.ui.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sun.zhangxun.config.Constants;

import java.util.List;

/**
 * Created by sun on 2017/3/28.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;
    private String[] mTitles;


    public TabFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabFragmentAdapter(List<Fragment> list , String[] titles , FragmentManager fm , Context context) {
        super(fm);
        mList=list;
        mTitles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Constants.BASE_TITLES[position];
    }
}
