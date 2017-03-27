package com.example.sun.zhangxun.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.base.BaseActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity implements View.OnClickListener,NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
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
        mNavigationView.setItemIconTintList(null);
        setSupportActionBar(mToolbar);

//        initNavigationView();
    }


    protected void setListener() {
        mNavigationView.setNavigationItemSelectedListener(this);
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


}
