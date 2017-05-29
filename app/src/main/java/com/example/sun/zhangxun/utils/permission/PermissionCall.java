package com.example.sun.zhangxun.utils.permission;

/**
 * Created by sun on 2017/5/18.
 * 权限申请回调接口
 */

public interface PermissionCall {

    //申请成功
    void requestSuccess();

    //拒绝
    void refused();
}
