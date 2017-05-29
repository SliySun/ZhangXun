package com.example.sun.zhangxun.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.app.BaseMvpActivity;
import com.example.sun.zhangxun.app.BasePresenter;
import com.example.sun.zhangxun.presenter.Constracts.ISavePhotoConstract;
import com.example.sun.zhangxun.presenter.SavePhotoPresenter;
import com.example.sun.zhangxun.ui.Adapter.PhotoBrowserPagerAdapter;
import com.example.sun.zhangxun.utils.permission.PermissionCall;
import com.example.sun.zhangxun.utils.permission.Permissions;

import java.util.ArrayList;

public class PhotoBrowserActivity extends BaseMvpActivity implements ISavePhotoConstract.ISavaPhotoView,ViewPager.OnPageChangeListener,View.OnClickListener{

    private ViewPager mVp;
    private TextView mTv_index,mTv_sava;
    private ArrayList<String> mList;
    private String mCurrentUrl;
    private int index;
    private SavePhotoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onInitPresenters() {
        presenter = new SavePhotoPresenter(this);
    }

    @Override
    protected BasePresenter[] getPresenters() {
        return new BasePresenter[]{presenter};
    }

    @Override
    protected void handleIntent(Intent intent) {
        mList = intent.getStringArrayListExtra("urls");
        mCurrentUrl = intent.getStringExtra("curImageUrl");
        System.out.println("photoBrower"+mList);
        System.out.println("photoBrower"+mCurrentUrl);

    }

    protected void initData() {
        System.out.println("initData");
        mVp.setAdapter(new PhotoBrowserPagerAdapter(this,mList));

        //获取被点击图片在所有图片中的位置
        index = mList.indexOf(mCurrentUrl);
        setIndex(index);

        mVp.setCurrentItem(index);
    }

    private void setIndex(int index) {
        mTv_index.setText((index+1)+"/"+mList.size());
    }




    @Override
    protected void initView() {
        mVp = (ViewPager) findViewById(R.id.viewPager_photo_browser);
        mTv_index = (TextView) findViewById(R.id.textView_photo_browser_index);
        mTv_sava = (TextView) findViewById(R.id.textView_photo_browser_save);
    }

    @Override
    protected void setListener() {
        mTv_sava.setOnClickListener(this);
        mVp.addOnPageChangeListener(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_photo_browser;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position != index){
//            showLoad();
        }
        setIndex(position);
        index = position;
        mCurrentUrl = mList.get(position);
    }



    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textView_photo_browser_save:
                requestRunTimePermissions(Permissions.Save,
                        new PermissionCall() {
                            @Override
                            public void requestSuccess() {
                                presenter.save(mCurrentUrl);
                                System.out.println("a");
                            }

                            @Override
                            public void refused() {
                                Toast.makeText(PhotoBrowserActivity.this,"无法保存图片，请允许相关权限",Toast.LENGTH_SHORT).show();
                            }
                        }, Permissions.SAVE_REQUEST);
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.photo_browser_exit);

    }

    @Override
    public void showSucceed(String path) {
        Toast.makeText(this,"已保存至SD卡"+path+"文件夹下",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailed(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

    }
}
