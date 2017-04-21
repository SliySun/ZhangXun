package com.example.sun.zhangxun.ui.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.app.BaseActivity;
import com.example.sun.zhangxun.app.BaseFragment;
import com.example.sun.zhangxun.config.Constants;
import com.example.sun.zhangxun.ui.Adapter.TabFragmentAdapter;
import com.example.sun.zhangxun.ui.fragment.TabContentFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

//    private CircleImageView mCircleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void hideStatusBar() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    protected void initView() {
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout_home);
        mNavigationView= (NavigationView) findViewById(R.id.navigationView_Home);
        mToolbar= (Toolbar) findViewById(R.id.toolbar_home);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout_home_tab);
        mViewPager= (ViewPager) findViewById(R.id.viewPager_home_content);

        mNavigationView.setItemIconTintList(null);
        setSupportActionBar(mToolbar);

        initPager();

    }

    List<Fragment> mFragments;

    private void initPager() {
        mFragments = new ArrayList<Fragment>();
        for (int i = 0; i < Constants.BASE_TITLES.length; i++) {
            Fragment fragment= TabContentFragment.newInstance(i+1);
            mFragments.add(fragment);
        }
        mViewPager.setAdapter(new TabFragmentAdapter(mFragments, Constants.BASE_TITLES, getSupportFragmentManager(), this));
        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabTextColors(Color.BLACK,Color.RED);


    }


    protected void setListener() {
        mNavigationView.setNavigationItemSelectedListener(this);
//        mViewPager.addOnPageChangeListener(this);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.circleImageView_navigationView_header:
                Toast.makeText(getApplicationContext(),"点击头像",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigationView_menu_item1:
                Toast.makeText(getApplicationContext(),"点击条目1",Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawers();
                break;
            case R.id.navigationView_menu_item2:
                Toast.makeText(getApplicationContext(),"点击条目2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigationView_menu_item3:
                Toast.makeText(getApplicationContext(),"点击条目3",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

/*
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabContentFragment frgment= (TabContentFragment) mFragments.get(position);
        frgment.initData();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    */
}
