package com.example.sun.zhangxun.utils.permission;

import android.Manifest;

public interface Permissions {
    String[] CALENDAR = {
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR};
    String[] CAMERA = {
            Manifest.permission.CAMERA};
    String[] CONTACTS = {
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS};
    String[] LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};
    String[] MICROPHONE = {
            Manifest.permission.RECORD_AUDIO};
    String[] PHONE = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.ADD_VOICEMAIL,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS};
    String[] SENSORS = {
            Manifest.permission.BODY_SENSORS};
    String[] SMS = {
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS};
    String[] STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    String CALENDAR_DESC = "日历";
    String CAMERA_DESC = "相机";
    String CONTACTS_DESC = "通讯录";
    String LOCATION_DESC = "位置信息";
    String MICROPHONE_DESC = "麦克风";
    String PHONE_DESC = "电话";
    String SENSORS_DESC = "身体传感器";
    String SMS_DESC = "短信";
    String STORAGE_DESC = "存储空间";

    int CALENDAR_REQUEST = 1;
    int CAMERA_REQUEST = 2;
    int CONTACTS_REQUEST = 3;
    int LOCATION_REQUEST = 0x4;
    int MICROPHONE_REQUEST = 5;
    int PHONE_REQUEST = 6;
    int SENSORS_REQUEST = 7;
    int SMS_REQUEST = 8;
    int STORAGE_REQUEST = 9;
    int ALL_REQUEST = 0x123;
    int SHARE_REQUEST = 0x2;
    int SAVE_REQUEST = 0x3;

    String[] AMap = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    String[] Share = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.CALL_PHONE,
//            Manifest.permission.GET_ACCOUNTS,

//            Manifest.permission.READ_LOGS,
            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.SET_DEBUG_APP,
//            Manifest.permission.SYSTEM_ALERT_WINDOW,
//            Manifest.permission.WRITE_APN_SETTINGS
    };

    String[] Save={
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
}
