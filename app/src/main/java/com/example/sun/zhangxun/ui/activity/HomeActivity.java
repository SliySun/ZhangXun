package com.example.sun.zhangxun.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.weather.LocalWeatherLive;
import com.bumptech.glide.Glide;
import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.app.BaseMvpActivity;
import com.example.sun.zhangxun.app.BasePresenter;
import com.example.sun.zhangxun.config.Constants;
import com.example.sun.zhangxun.presenter.Constracts.ILocationConstract;
import com.example.sun.zhangxun.presenter.LocationPresenter;
import com.example.sun.zhangxun.ui.Adapter.TabFragmentAdapter;
import com.example.sun.zhangxun.ui.fragment.TabContentFragment;
import com.example.sun.zhangxun.utils.permission.PermissionCall;
import com.example.sun.zhangxun.utils.permission.Permissions;
import com.example.sun.zhangxun.widget.HomeToolbar;

import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseMvpActivity implements ILocationConstract.ILocationView, View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private HomeToolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private RelativeLayout mRl;
    private CircleImageView mCircleImageView;
    private TextView mTv_userName;
    private ImageView mIv_weather;
    private TextView mTv_city;
    private TextView mTv_degree;


    public static boolean isForeground = false;
    private LocationPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onInitPresenters() {
        mPresenter = new LocationPresenter(this);

    }

    @Override
    protected BasePresenter[] getPresenters() {
        return new BasePresenter[]{mPresenter};
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    protected void initView() {
        mDrawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout_home);
        mNavigationView= (NavigationView) findViewById(R.id.navigationView_Home);
        mToolbar= (HomeToolbar) findViewById(R.id.toolbar_home);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout_home_tab);
        mViewPager= (ViewPager) findViewById(R.id.viewPager_home_content);

        mNavigationView.setItemIconTintList(null);
        setSupportActionBar(mToolbar);

        initNavigation();
        initPager();

    }

    private void initNavigation() {
        View headerView = mNavigationView.getHeaderView(0);
        mRl = (RelativeLayout) headerView.findViewById(R.id.relativeLayout_navigationView_header);
        mCircleImageView= (CircleImageView) headerView.findViewById(R.id.circleImageView_navigationView_header);
        mTv_userName = (TextView) headerView.findViewById(R.id.textView_navigationView_header_username);
        mIv_weather = (ImageView) headerView.findViewById(R.id.imageView_navigationView_header_weather);
        mTv_city = (TextView) headerView.findViewById(R.id.textView_navigationView_header_city);
        mTv_degree = (TextView) headerView.findViewById(R.id.textView_navigationView_header_weather);
    }

    List<Fragment> mFragments;
    TabFragmentAdapter mAdapter;

    private void initPager() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < Constants.BASE_TITLES.length; i++) {
            Fragment fragment= TabContentFragment.newInstance(i);
            mFragments.add(fragment);
        }
        mAdapter = new TabFragmentAdapter(mFragments, Constants.BASE_TITLES, getSupportFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }


    protected void setListener() {
        mCircleImageView.setOnClickListener(this);
        mRl.setOnClickListener(this);
        mNavigationView.setNavigationItemSelectedListener(this);
        mToolbar.setOnIconClickListener(this);
        mToolbar.setOnSearchClickListener(this);
    }

    @Override
    protected void initData() {
        requestRunTimePermissions(Permissions.AMap, new PermissionCall() {
            @Override
            public void requestSuccess() {
                mPresenter.updateWeatherNew();
            }

            @Override
            public void refused() {
                Toast.makeText(HomeActivity.this,"无法更新天气",Toast.LENGTH_SHORT).show();
                showWeatherError();
            }
        }, Permissions.LOCATION_REQUEST);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.circleImageView_navigationView_header:
                Toast.makeText(getApplicationContext(),"点击头像",Toast.LENGTH_SHORT).show();
                break;
            case R.id.circleImageView_toolbar_icon:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.textView_toolbar_search:
                Toast.makeText(getApplicationContext(),"点击搜索框",Toast.LENGTH_SHORT).show();
                break;
            case R.id.relativeLayout_navigationView_header:
                Toast.makeText(getApplicationContext(),"暂未开通",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigationView_menu_item1:
                Intent intent = new Intent(this,FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.navigationView_menu_item2:
                Toast.makeText(getApplicationContext(),"点击视频",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigationView_menu_item3:
                Toast.makeText(getApplicationContext(),"点击商城",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigationView_menu_item4:
                Toast.makeText(getApplicationContext(),"点击关于",Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigationView_menu_item5:
                intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    private int getWeatherIcon(String weather) {
        if (weather.contains("-")) {
            weather = weather.substring(0, weather.indexOf("-"));
        }
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        System.out.println("hour:"+hour);
        int resId;
        if (weather.contains("晴")) {
            if (hour >= 7 && hour < 19) {
                resId = R.drawable.ic_weather_sunny;
            } else {
                resId = R.drawable.ic_weather_sunny_night;
            }
        } else if (weather.contains("多云")) {
            if (hour >= 7 && hour < 19) {
                resId = R.drawable.ic_weather_cloudy;
            } else {
                resId = R.drawable.ic_weather_cloudy_night;
            }
        } else if (weather.contains("阴")) {
            resId = R.drawable.ic_weather_overcast;
        } else if (weather.contains("雷阵雨")) {
            resId = R.drawable.ic_weather_thunderstorm;
        } else if (weather.contains("雨夹雪")) {
            resId = R.drawable.ic_weather_sleet;
        } else if (weather.contains("雨")) {
            resId = R.drawable.ic_weather_rain;
        } else if (weather.contains("雪")) {
            resId = R.drawable.ic_weather_snow;
        } else if (weather.contains("雾") || weather.contains("霾")) {
            resId = R.drawable.ic_weather_foggy;
        } else if (weather.contains("风") || weather.contains("飑")) {
            resId = R.drawable.ic_weather_typhoon;
        } else if (weather.contains("沙") || weather.contains("尘")) {
            resId = R.drawable.ic_weather_sandstorm;
        } else {
            resId = R.drawable.ic_weather_cloudy;
        }
        return resId;
    }



    @Override
    public void showWeather(LocalWeatherLive live) {
        System.out.println(live.getWeather());
//        mIv_weather.setImageResource(getWeatherIcon(live.getWeather()));
            mIv_weather.setVisibility(View.VISIBLE);
            Glide.with(HomeActivity.this).load(getWeatherIcon(live.getWeather())).into(mIv_weather);
            mTv_degree.setText(live.getTemperature() + "°");
            mTv_city.setText(live.getCity());
    }



    @Override
    public void showWeatherError() {
        mIv_weather.setVisibility(View.INVISIBLE);
        mTv_degree.setText("--°");
    }

    private Long firstTime = 0L;

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
            mDrawerLayout.closeDrawers();
            return;
        }

        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 1500) {
            Toast.makeText(this, getString(R.string.back_again_exit),Toast.LENGTH_LONG).show();
            firstTime = secondTime;
        } else {
            System.exit(0);
        }
    }
}
