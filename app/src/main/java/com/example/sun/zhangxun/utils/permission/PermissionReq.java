package com.example.sun.zhangxun.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2017/5/18.
 */

public class PermissionReq {

    private Object mObject;
    private PermissionCall call;
    private String[] mPermissions;

    public PermissionReq(Object obj){
        this.mObject = obj;
    }

    public PermissionReq with(Activity activity){
        return new PermissionReq(activity);
    }

    public PermissionReq with(Fragment fragment){
        return new PermissionReq(fragment);
    }

    public PermissionReq permissions(String... strings){
        this.mPermissions = strings;
        return this;
    }

    public PermissionReq result(PermissionCall permissionCall) {
        this.call = permissionCall ;
        return this;
    }

    public void request() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (call != null) {
                call.requestSuccess();
            }
            return;
        }

        Activity activity = getActivity(mObject);
        if (activity == null) {
            throw new IllegalArgumentException(mObject.getClass().getName() + " is not supported");
        }

        //获得批量请求但被禁止的权限列表
        List<String> deniedPermissionList = getDeniedPermissions(activity, mPermissions);
        if (deniedPermissionList.isEmpty()) {
            if (call != null) {
                call.requestSuccess();
            }
            return;
        }

        String[] deniedPermissions = deniedPermissionList.toArray(new String[deniedPermissionList.size()]);

        //申请权限
//        requestPermission(deniedPermissions, deniedPermissions);


    }

    private static List<String> getDeniedPermissions(Context context, String[] permissions) {
        List<String> deniedPermissionList = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissionList.add(permission);
            }
        }
        return deniedPermissionList;
    }

    private static Activity getActivity(Object object) {
        if (object != null) {
            if (object instanceof Activity) {
                return (Activity) object;
            } else if (object instanceof Fragment) {
                return ((Fragment) object).getActivity();
            }
        }
        return null;
    }
}
