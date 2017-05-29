package com.example.sun.zhangxun.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.sun.zhangxun.utils.permission.PermissionCall;
import com.example.sun.zhangxun.utils.permission.Permissions;

import java.util.ArrayList;
import java.util.List;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by sun on 2017/3/23.
 *
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;

    //静态变量，在 PicassoTarget 中修改 status 颜色时也动态修改
    public static int currentStatusColor;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (hide()){
            hideStatusBar();
        }
        setContentView(getContentViewId());

        if (transparent()){
            transparentStatusBar();
        }

        if (null != getIntent()){
            handleIntent(getIntent());
        }

        init();
    }

    protected  void handleIntent(Intent intent){}

    protected  boolean transparent(){
        return false;
    }

    private void init() {
        initView();
        setListener();
//        initData();
    }


    protected boolean hide(){
        return false;
    }


    /**
       隐藏状态栏
     */
    private void hideStatusBar(){
        //将SplashActivity页面设置全屏，没有状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected  void initView(){}

    protected  void setListener(){}

    protected  void initData(){}
    protected   int getContentViewId(){return 0;}

    /**
     * 透明式状态栏
     */
    private void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // LOLLIPOP解决方案
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//                                                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态拦
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }
    }





    private PermissionCall mPermissionCall;

    /**
     * 对子类提供的申请权限方法
     *
     * @param permissions 申请的权限
     */
    public void requestRunTimePermissions(String[] permissions, PermissionCall call,int requestCode) {
        if (permissions == null || permissions.length == 0) return;

        mPermissionCall = call;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            call.requestSuccess();
            return;
        }

        //获得批量请求但被禁止的权限列表
        List<String> deniedPermissionList = getDeniedPermissions(permissions);
        if (deniedPermissionList == null || deniedPermissionList.size() == 0) {
            call.requestSuccess();
            return;
        }


        //申请权限
        requestPermission(deniedPermissionList.toArray(new String[deniedPermissionList.size()]), requestCode);

    }

    private void requestPermission(final String[] permissions, final int requestCode) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            new AlertDialog.Builder(this)
                    .setTitle("温馨提示")
                    .setMessage("为了保证应用正常运行，请授予必要权限")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        boolean flag = false;
        for (String p : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, p)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     *
     */
    private  List<String> getDeniedPermissions( String[] permissions) {
        List<String> deniedPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                System.out.println(permission);
                deniedPermissionList.add(permission);
            }
        }
        return deniedPermissionList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(grantResults.length==0){
            return;
        }
//        switch (requestCode){
//            case Permissions.LOCATION_REQUEST:
//                if (verifyPermissions(grantResults)){
//                    mPermissionCall.requestSuccess();
//                }else {
//                    mPermissionCall.refused();
//                }
////                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
////                    mPermissionCall.requestSuccess();
////                }else {
////                    mPermissionCall.refused();
////                }
//                break;
//            case Permissions.STORAGE_REQUEST:
//                if (verifyPermissions(grantResults)){
//                    mPermissionCall.requestSuccess();
//                }else {
//                    mPermissionCall.refused();
//                }
//
////                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
////                    mPermissionCall.requestSuccess();
////                }else {
////                    mPermissionCall.refused();
////                }
//                break;
//            case 123:
//                if (verifyPermissions(grantResults)){
//                    mPermissionCall.requestSuccess();
//                }else {
//                    mPermissionCall.refused();
//                }
//
////                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
////                    mPermissionCall.requestSuccess();
////                }else {
////                    mPermissionCall.refused();
////                }


//        switch (requestCode){
//            case Permissions.ALL_REQUEST:
        System.out.println(verifyPermissions(grantResults));
                if (verifyPermissions(grantResults)) {
                    System.out.println("requestSuccess");
                    mPermissionCall.requestSuccess();
                } else {
                    System.out.println("refused");
                    mPermissionCall.refused();
                }
//                break;
//            case Permissions.SHARE_REQUEST:
//                break;
//        }


        isNeedCheck = false;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    /**
     * 检测是否说有的权限都已经授权
     * @param grantResults
     * @return
     * @since 2.5.0
     *
     */
    private boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


}
