package com.example.sun.zhangxun.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.Window;
import android.view.WindowManager;

import com.example.sun.zhangxun.R;
import com.example.sun.zhangxun.app.BaseActivity;
import com.example.sun.zhangxun.app.CustomApplication;
import com.example.sun.zhangxun.ui.activity.HomeActivity;
import com.example.sun.zhangxun.utils.permission.PermissionCall;
import com.example.sun.zhangxun.utils.permission.Permissions;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    callRequest();

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//
//        Timer timer=new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
////                startMain();
//        callRequest();
//            }
//        },3000);
//
        handler.sendEmptyMessageDelayed(2,3000);

    }

    private void callRequest() {
        requestRunTimePermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
//                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_PHONE_STATE,

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
//                        Manifest.permission.GET_ACCOUNTS,


//                        Manifest.permission.READ_LOGS,
//                        Manifest.permission.SET_DEBUG_APP,
//                        Manifest.permission.SYSTEM_ALERT_WINDOW,
//                        Manifest.permission.WRITE_APN_SETTINGS
        },
                new PermissionCall() {
                    @Override
                    public void requestSuccess() {
                        startMain();
                    }

                    @Override
                    public void refused() {
                        System.out.println("refused");

                        new AlertDialog.Builder(SplashActivity.this)
                                .setTitle("温馨提示")
                                .setMessage("为了保证应用正常运行，请授予必要权限")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
//                                        ActivityCompat.requestPermissions(BaseActivity.this, permissions, requestCode);
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", CustomApplication.getContext().getPackageName(), null);
                                        intent.setData(Uri.parse("package:"+ CustomApplication.getContext().getPackageName()));
                                        SplashActivity.this.startActivityForResult(intent,0);
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        startMain();
                                    }
                                })
                                .show();
                    }
                }, Permissions.ALL_REQUEST);
    }


    @Override
    protected boolean hide() {
        return true;
    }



    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    private void startMain(){
        System.out.println("startMain");
        Intent intent=new Intent(SplashActivity.this,HomeActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    public void finish() {
        overridePendingTransition(R.anim.photo_browser_enter,R.anim.photo_browser_exit);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        startMain();
    }
}
